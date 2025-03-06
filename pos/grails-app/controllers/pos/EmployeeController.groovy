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

//package for minio
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection
import java.net.URL
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream

//package for method signature
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.security.MessageDigest

// packages for jets3t
import org.jets3t.service.S3Service
import org.jets3t.service.impl.rest.httpclient.RestS3Service
import org.jets3t.service.model.S3Bucket
import org.jets3t.service.model.S3Object
import org.jets3t.service.security.AWSCredentials

class EmployeeController {

    def index() {
        def employees = Employee.list()
        render(view: "index", model:[employees:employees])
    }

    def add_employee() {
        //create a service for the file handling part
    }

    

    // def add_image_reduce_resolution() {
    //     def uploadedFile = request.getFile('uploadedImage') // Get uploaded image file
    //     if (!uploadedFile || uploadedFile.empty) {
    //         flash.message = "Please upload a valid image file."
    //         redirect(action: 'index')
    //         return
    //     }
        
    //     String originalFilename = uploadedFile.originalFilename
    //     int targetWidth = 300  
    //     int targetHeight = 300
    //     // float quality = 0.6f   // Set compression quality (0.6 = 60%)

    //     try {
    //         BufferedImage originalImage = ImageIO.read(uploadedFile.inputStream)
    //         BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight)
    //         ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream()

    //         // Compress and write image
    //         ImageIO.write(resizedImage, "jpg", compressedImageStream)

    //         byte[] compressedImageBytes = compressedImageStream.toByteArray()
    //         println "Original Size: ${uploadedFile.size / 1024} KB, Compressed Size: ${compressedImageBytes.length / 1024} KB"

    //         // Save the compressed image (Modify as per your requirements)
    //         File outputFile = new File("grails-app/assets/image/${originalFilename}")
    //         outputFile.bytes = compressedImageBytes

    //         flash.message = "Image uploaded and compressed successfully!"
    //     } catch (Exception e) {
    //         flash.message = "Error processing image: ${e.message}"
    //     }

    //     redirect(controller: 'sales', action: 'home')
    // }

    

    /// action: compress by resualation and size:
    def add_image_reduce_resolution_and_bytes() {

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
            // String endpoint = "https://minio-console.waltonbd.com"
            // String accessKey = "T0eNu377V0ZpgjwemFml"
            // String secretKey = "OkSoTcayuOrYYegJS5nAcqpBJBq9lfHN33GV5UQ1"

            String endpoint = "https://minio.waltonbd.com"
            String accessKey = "GL3fVbqbvNz58U4oOtFE"
            String secretKey = "ikyBaKyjV8bnOYuUkbN5NOzY0zLiFMgS3zMCy2hq"
            String bucketName = "wc-pos"
                        
            // Upload to Minio
            //String minioUrl = uploadToMinio(compressedImageBytes, uniqueFilename, bucketName, endpoint, accessKey, secretKey)
            // String minioUrl = uploadToMinio(
            //     compressedImageBytes,   // compressed image bytes
            //     uniqueFilename,         // unique filename
            //     bucketName,             // bucket name
            //     endpoint,               // endpoint
            //     accessKey,              // access key
            //     secretKey               // secret key
            // )
            println minioUrl
            
            // Save the compressed image to local server
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
    private String uploadToMinio(byte[] imageBytes, String filename, String bucketName, String endpoint, String accessKey, String secretKey) {
        try {
            String urlString = "${endpoint}/${bucketName}/${filename}"
            URL url = new URL(urlString)
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection()
            
            // Set HTTP method to PUT (upload)
            connection.setRequestMethod("PUT")
            connection.setDoOutput(true)
            connection.setRequestProperty("Content-Type", "image/jpeg")
            connection.setRequestProperty("Authorization", "AWS ${accessKey}:${getSignature(accessKey, secretKey)}")

            // Write the image bytes to the request body
            connection.getOutputStream().write(imageBytes)
            println connection.getResponseMessage()

            // Get the response code to check if the upload was successful
            int responseCode = connection.getResponseCode()
            if (responseCode == 200) {
                return urlString  // Return the URL of the uploaded file
            } else {
                throw new RuntimeException("Error uploading to Minio. Response code: ${responseCode}")
            }
        } catch (Exception e) {
            throw new RuntimeException("Error uploading to Minio: " + e.message, e)
        }
    }

    //using aws:
    //  private String uploadToMinio(
    //     byte[] fileBytes, 
    //     String filename, 
    //     String bucketName, 
    //     String endpoint, 
    //     String accessKey, 
    //     String secretKey
    // ) {
    //     try {
    //         // Disable strict SSL validation (use with caution)
    //         disableSSLValidation()

    //         // Construct full URL
    //         String fullUrl = "${endpoint}/${bucketName}/${filename}"
            
    //         // Create URL connection
    //         URL url = new URL(fullUrl)
    //         HttpsURLConnection connection = (HttpsURLConnection) url.openConnection()
            
    //         // Set up authentication
    //         String authString = "${accessKey}:${secretKey}"
    //         String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes())
            
    //         // Configure connection
    //         connection.setRequestMethod("PUT")
    //         connection.setDoOutput(true)
    //         connection.setRequestProperty("Content-Type", "image/jpeg")
    //         connection.setRequestProperty("Authorization", "Basic ${encodedAuthString}")
            
    //         // Additional connection settings for robustness
    //         connection.setConnectTimeout(10000)  // 10 seconds
    //         connection.setReadTimeout(10000)     // 10 seconds
    //         connection.setUseCaches(false)
            
    //         // Write file bytes
    //         OutputStream outputStream = null
    //         try {
    //             outputStream = connection.getOutputStream()
    //             outputStream.write(fileBytes)
    //         } finally {
    //             if (outputStream != null) {
    //                 // outputStream.close()
    //             }
    //         }
            
    //         // Check response
    //         int responseCode = connection.getResponseCode()
    //         if (responseCode == 200 || responseCode == 204) {
    //             return fullUrl
    //         } else {
    //             // Read error stream for more details
    //             def errorStream = connection.getErrorStream()
    //             String errorResponse = errorStream ? errorStream.text : "Unknown error"
                
    //             log.error("Minio upload failed. Response code: ${responseCode}, Error: ${errorResponse}")
    //             throw new RuntimeException("Minio upload failed. Response code: ${responseCode}")
    //         }
    //     } catch (Exception e) {
    //         log.error("Minio upload error: ${e.message}", e)
    //         throw new RuntimeException("Failed to upload to Minio: ${e.message}", e)
    //     }
    // }

    // /**
    //  * Disable SSL certificate validation
    //  * WARNING: This should only be used for testing and is not recommended for production
    //  */
    // private void disableSSLValidation() {
    //     try {
    //         // Create a trust manager that does not validate certificate chains
    //         TrustManager[] trustAllCerts = [
    //             new X509TrustManager() {
    //                 public X509Certificate[] getAcceptedIssuers() { return null }
    //                 public void checkClientTrusted(X509Certificate[] certs, String authType) {}
    //                 public void checkServerTrusted(X509Certificate[] certs, String authType) {}
    //             }
    //         ] as TrustManager[]

    //         // Install the all-trusting trust manager
    //         SSLContext sc = SSLContext.getInstance("SSL")
    //         sc.init(null, trustAllCerts, new java.security.SecureRandom())
    //         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())

    //         // Create all-trusting host name verifier
    //         HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }
    //     } catch (Exception e) {
    //         log.error("Error disabling SSL validation: ${e.message}", e)
    //     }
    // }

    // Helper method to generate AWS signature for Minio authentication
    private String getSignature(String accessKey, String secretKey) {
        try {
            // Step 1: Prepare the request
            String region = "bangladesh"  // Adjust region as needed
            String service = "s3"
            String method = "PUT"
            String host = "minio-console.waltonbd.com"  // Minio server
            String bucketName = "wc-pos"  // Your bucket name
            String objectKey = UUID.randomUUID().toString() + ".jpg"  // Random filename for example

            String requestDate = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(new Date())
            String amzDate = new SimpleDateFormat("yyyyMMdd").format(new Date()) // Date for signing

            String canonicalUri = "/${bucketName}/${objectKey}"
            String canonicalQueryString = ""
            String canonicalHeaders = "host:${host}\nx-amz-date:${requestDate}\n"
            String signedHeaders = "host;x-amz-date"

            // Create the canonical request
            String canonicalRequest = "${method}\n${canonicalUri}\n${canonicalQueryString}\n${canonicalHeaders}\n${signedHeaders}\nUNSIGNED-PAYLOAD"

            // Step 2: Generate the string to sign
            String credentialScope = "${amzDate}/${region}/${service}/aws4_request"
            String stringToSign = "AWS4-HMAC-SHA256\n${requestDate}\n${credentialScope}\n${hash(canonicalRequest)}"

            // Step 3: Compute the signature
            byte[] signingKey = getSigningKey(secretKey, amzDate, region, service)
            byte[] signatureBytes = hmacSHA256(stringToSign, signingKey)

            // Step 4: Return the final signature
            return "AWS4-HMAC-SHA256 Credential=${accessKey}/${credentialScope}, SignedHeaders=${signedHeaders}, Signature=${bytesToHex(signatureBytes)}"
        } catch (Exception e) {
            throw new RuntimeException("Error generating signature: " + e.message, e)
        }
    }

    private byte[] hmacSHA256(String data, byte[] key) {
        Mac mac = Mac.getInstance("HmacSHA256")
        mac.init(new SecretKeySpec(key, "HmacSHA256"))
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8))
    }

    private byte[] getSigningKey(String secretKey, String amzDate, String region, String service) {
        byte[] kSecret = ("AWS4" + secretKey).getBytes(StandardCharsets.UTF_8)
        byte[] kDate = hmacSHA256(amzDate, kSecret)
        byte[] kRegion = hmacSHA256(region, kDate)
        byte[] kService = hmacSHA256(service, kRegion)
        return hmacSHA256("aws4_request", kService)
    }

    private String hash(String data) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256")
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8))
        return bytesToHex(hashBytes)
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder()
        for (byte b : bytes) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
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
