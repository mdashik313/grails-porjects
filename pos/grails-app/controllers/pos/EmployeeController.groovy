package pos
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

import java.awt.Graphics2D
import java.awt.image.BufferedImage

import javax.imageio.*
import javax.imageio.stream.*
import java.awt.*
import java.awt.image.*
import javax.imageio.plugins.jpeg.*
import java.util.UUID

//
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.NoSuchAlgorithmException
import java.security.InvalidKeyException

// MinIO dependencies (add to BuildConfig.groovy)
import io.minio.MinioClient
import io.minio.errors.MinioException
import org.xmlpull.v1.XmlPullParserException

import java.net.HttpURLConnection
import java.net.URL
import java.io.OutputStream
import java.io.DataOutputStream
import java.io.ByteArrayInputStream
import org.apache.commons.codec.binary.Base64

class EmployeeController {

    def index() {
        def employees = Employee.list()
        render(view: "index", model:[employees:employees])
    }

    def add_employee() {
        //create a service for the file handling part
    }


    /// action: compress by resualation and size:
    def add_image_reduce_resolution_and_bytes() {

        def uploadToMinio

        def uploadedFile = request.getFile('uploadedImage') // Get uploaded image file

        if (!uploadedFile || uploadedFile.empty) {
            flash.message = "Please upload a valid image file."
            redirect(action: 'index')
            return
        }

        // Allowed image MIME types
        def allowedTypes = ['image/jpeg', 'image/png', 'image/jpg']
        
        if (!allowedTypes.contains(uploadedFile.contentType)) {
            flash.message = "Only JPG, JPEG, and PNG files are allowed."
            redirect(action: 'index')
            return
        }
        
        String f_extension = uploadedFile.originalFilename.toLowerCase()
        f_extension = f_extension.endsWith(".jpg") ? ".jpg" :
                     f_extension.endsWith(".jpeg") ? ".jpeg" :
                     f_extension.endsWith(".png") ? ".png" : ""  // Default case (empty string if no match)

        String uniqueFilename = UUID.randomUUID().toString() + f_extension
        int targetWidth = 300  
        int targetHeight = 300
        float quality = 0.9f  // Default to 90% quality

        try {
            // Load image
            BufferedImage originalImage = ImageIO.read(uploadedFile.inputStream)
            BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight)
            
            // Compress the image iteratively until it's under 100KB
            ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream()
            byte[] compressedImageBytes = compressToTargetSize(resizedImage, compressedImageStream, quality, 100 * 1024) // 100KB target
            
            println "Original Size: ${uploadedFile.size / 1024} KB, Compressed Size: ${compressedImageBytes.length / 1024} KB"
            
            //  Minio configuration

            String endpoint = ""
            String accessKey = ""
            String secretKey = ""
            String bucketName = ""
                        
            // Upload to Minio
            // String minioUrl = uploadToMinioDirect(endpoint, accessKey, secretKey, bucketName, uniqueFilename, compressedImageBytes)
            //uploadToMinio.uploadCompressedImageToMinio(compressedImageBytes, uniqueFilename)
            
            // Save compressed image to local server
            File outputFile = new File("web-app/images/employee/${uniqueFilename}")
            outputFile.bytes = compressedImageBytes
            
            flash.message = "Image uploaded and compressed successfully!"

        } catch (Exception e) {
            flash.message = "Error processing image: ${e.message}"
        }

        //saving the data to database

        def employee = new Employee()
        employee.name = params.name
        employee.phone = params.phone
        employee.document = uniqueFilename

        if(employee.save(flush:true)){
            redirect(action: "index")
            return
        }
        else{
            redirect(action: "index")
            flash.message = "Failed saving"
            return
        }
        
        redirect(controller: 'sales', action: 'home')
    }


    // Upload to Minio
    private String uploadToMinioDirect(String endpoint, String accessKey, String secretKey, String bucketName, String objectName, byte[] data) {
        try {
            String urlString = "${endpoint}/${bucketName}/${objectName}"
            URL url = new URL(urlString)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setRequestMethod("PUT")
            connection.setRequestProperty("Authorization", getMinioAuthorization(accessKey, secretKey, "PUT", bucketName, objectName))
            connection.setRequestProperty("Content-Type", "image/jpeg") // Adjust if needed
            connection.setDoOutput(true)

            OutputStream outputStream = connection.getOutputStream()
            outputStream.write(data)
            outputStream.flush()
            outputStream.close()

            int responseCode = connection.getResponseCode()
            if (responseCode == 200) {
                return urlString; // Or construct a proper URL if needed
            } else {
                println "MinIO upload failed with response code: ${responseCode}"
                return null;
            }
        } catch (Exception e) {
            println "Error uploading to MinIO: ${e.message}"
            return null;
        }
    }

    private String getMinioAuthorization(String accessKey, String secretKey, String method, String bucketName, String objectName) {
        String date = new Date().format("EEE, dd MMM yyyy HH:mm:ss z", TimeZone.getTimeZone("GMT"))
        String contentType = "image/jpeg" // Adjust as needed
        String contentMd5 = new String(Base64.encodeBase64(java.security.MessageDigest.getInstance("MD5").digest(new ByteArrayInputStream(new byte[0]).bytes)));
        String stringToSign = "${method}\n${contentMd5}\n${contentType}\n${date}\n/${bucketName}/${objectName}"

        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1")
        javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(secretKey.getBytes(), "HmacSHA1")
        mac.init(secretKeySpec)
        String signature = new String(Base64.encodeBase64(mac.doFinal(stringToSign.getBytes())))

        return "AWS ${accessKey}:${signature}"
    }



    // Resize image based on width & height
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH)
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        Graphics2D g2d = resizedImage.createGraphics()
        g2d.drawImage(scaledImage, 0, 0, width, height, null)
        g2d.dispose()
        return resizedImage
    }

    // Compress image with adjustable quality
    private void compressImage(BufferedImage image, OutputStream outputStream, float quality) {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next()
        ImageOutputStream imageOutput = ImageIO.createImageOutputStream(outputStream)
        writer.setOutput(imageOutput)

        ImageWriteParam param = writer.defaultWriteParam
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT)
            param.setCompressionQuality(quality) // 🔥 Apply compression
        }

        writer.write(null, new IIOImage(image, null, null), param)
        imageOutput.close()
        writer.dispose()
    }

    // compress an image to a target size
    private byte[] compressToTargetSize(BufferedImage image, ByteArrayOutputStream outputStream, float initialQuality, int targetSize) {
        float quality = initialQuality
        byte[] imageBytes
        
        //  decreasing quality until get under target size
        while (quality > 0.1f) { // Set a minimum quality 
            outputStream.reset() // Clear previous data
            compressImage(image, outputStream, quality)
            imageBytes = outputStream.toByteArray()
            
            if (imageBytes.length <= targetSize) {
                break
            }
            
            // Reduce quality and try
            quality -= 0.1f
        }
        
        return outputStream.toByteArray()
    }
}
