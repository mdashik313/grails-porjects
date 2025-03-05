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