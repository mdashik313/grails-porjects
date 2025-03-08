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