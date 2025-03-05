## configuring minIO
download aws-java-sdk: https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-s3/1.11.722
add the dependency in buildConfig.groovy:
    dependencies {
        // AWS SDK for S3/Minio operations
        compile 'com.amazonaws:aws-java-sdk-s3:1.11.722'
    }
> grails clean
> grails refresh-dependencies



