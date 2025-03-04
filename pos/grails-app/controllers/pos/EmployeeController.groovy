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

// package for minio cloud
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest

class EmployeeController {

    def index() {
        def employees = Employee.list()
        render(view: "index", model:[employees:employees])
    }

    def add_employee() {
        //create a service for the file handling part
    }

    // def add_pdf() {
  
    //     def f = request.getFile('myFile')
    //     if (f.empty) {
    //         flash.message = 'file cannot be empty'
    //         render(view: 'index')
    //         return
    //     }
    //     String uploadDir = "grails-app/assets/nid/"
    //     String filePath = uploadDir +  f.originalFilename
    //     //String filePath = "${uploadDir}${f.originalFilename}"

    //         //compressing and saving:
    //     String zipFileName = "grails-app/assets/nid/${f.originalFilename}.zip"
    //     File zipFile = new File(zipFileName)

    //     zipFile.withOutputStream { outputStream ->
    //         ZipOutputStream zipOut = new ZipOutputStream(outputStream)
    //         zipOut.putNextEntry(new ZipEntry(f.originalFilename))
    //         f.inputStream.withStream { inputStream ->
    //             zipOut << inputStream
    //         }
    //         zipOut.closeEntry()
    //         zipOut.close()
    //     }
        

    //     //saving the original file
    //     // f.transferTo(new File(filePath))
    //     redirect(controller: 'sales', action: 'home')
    // }

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

        String uniqueFilename = UUID.randomUUID().toString()
        String originalFilename = uniqueFilename + uploadedFile.originalFilename
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
            
            // Save the compressed image
            Random n = new Random()
            File outputFile = new File("web-app/images/employee/${originalFilename}")
            outputFile.bytes = compressedImageBytes
            
            flash.message = "Image uploaded and compressed successfully!"

        } catch (Exception e) {
            flash.message = "Error processing image: ${e.message}"
        }

        //saving the data to database

        def employee = new Employee()
        employee.name = params.name
        employee.phone = params.phone
        employee.document = originalFilename

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


    // Upload file to Minio
    private String uploadToMinio(
        byte[] fileBytes, 
        String filename, 
        String bucketName, 
        String endpoint, 
        String accessKey, 
        String secretKey ) {

        // Create S3 client with Minio endpoint
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey)
        
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, "us-east-1")) // Region doesn't matter for Minio
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true) // Important for Minio compatibility
                .build()
        
        // Create metadata for the file
        ObjectMetadata metadata = new ObjectMetadata()
        metadata.setContentLength(fileBytes.length)
        
        // Set content type based on file extension
        if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
            metadata.setContentType("image/jpeg")
        } else if (filename.toLowerCase().endsWith(".png")) {
            metadata.setContentType("image/png")
        }
        
        // Upload the file
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes)
        PutObjectRequest putRequest = new PutObjectRequest(bucketName, filename, inputStream, metadata)
        s3Client.putObject(putRequest)
        
        // Return the URL to the uploaded file
        return "${endpoint}/${bucketName}/${filename}"
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
