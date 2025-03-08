package pos

import io.minio.MinioClient
import io.minio.errors.MinioException
import java.io.ByteArrayInputStream

class UploadToMinio {

     String MINIO_URL = ""
     String ACCESS_KEY = ""
     String SECRET_KEY = ""
     String BUCKET_NAME = ""

    def uploadCompressedImageToMinio(byte[] compressedImageBytes, String fileName) {
        def minioClient = new MinioClient(MINIO_URL, ACCESS_KEY, SECRET_KEY)
        
        try {
            // Ensure the bucket exists
            // if (!minioClient.bucketExists(bucketName)) {
            //     minioClient.makeBucket(bucketName)
            // }
            
            // Upload the byte array as an input stream
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedImageBytes)
            
            // Upload the file to MinIO
            minioClient.putObject(BUCKET_NAME, fileName, byteArrayInputStream, compressedImageBytes.length, "image/jpeg")
            println "Compressed image uploaded successfully to MinIO!"
        } catch (MinioException e) {
            e.printStackTrace()
        }
    }
}