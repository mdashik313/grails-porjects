package pos

import java.awt.Graphics2D
import java.awt.image.BufferedImage

import javax.imageio.*
import javax.imageio.stream.*
import java.awt.*
import java.awt.image.*
import javax.imageio.plugins.jpeg.*
import java.util.UUID
import java.io.ByteArrayInputStream
import java.io.InputStream




class ImageProcessingService {

    def compress(File uploadedFile) {  //argument: uploadedFile.inputStream
        try {
            // Load image
            BufferedImage originalImage = ImageIO.read(uploadedFile.inputStream)
            BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight)
            
            // Compress the image iteratively until it's under 100KB
            ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream()
            byte[] compressedImageBytes = compressToTargetSize(resizedImage, compressedImageStream, quality, 100 * 1024) // 100KB target
            
            println "Original Size: ${uploadedFile.size / 1024} KB, Compressed Size: ${compressedImageBytes.length / 1024} KB"
            
            //  Minio configuration

            String endpoint = "https://minio.waltonbd.com"
            String accessKey = "GL3fVbqbvNz58U4oOtFE"
            String secretKey = "ikyBaKyjV8bnOYuUkbN5NOzY0zLiFMgS3zMCy2hq"
            String bucketName = "wc-pos"
                        
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
