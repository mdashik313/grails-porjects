
<!DOCTYPE html>
<html>
<head>
    

  <g:render template="/layouts/heading_pos"/>
  <meta charset="UTF-8">
  <title>Upload</title>
  
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      background-color: #f2f2f2; /* Light gray background */
    }

    .page-container {
      width: 80%;
      margin: 20px auto;
      background-color: #fff;
      border: 1px solid #ddd;
      border-radius: 4px;
      padding: 20px;
    }

    /* Header styling */
    .header-title {
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 10px;
      border-bottom: 1px solid #ddd;
      padding: 10px;
      color: #333;
      background-color: #f2f2f2;
      border: 1px solid #ddd;
      text-align:left;
    }

    

    

  </style>
</head>
<body>

  <!-- Main Container -->
  <div class="page-container">
    
    <!-- Header Section -->
    <div class="header-title">Upload</div>

        <g:uploadForm controller="employee" action="add_image_reduce_resolution_and_bytes">
            <label> Name: </label> 
            <input type="text" name="name" /> <br>

            <label> Phone: </label> 
            <input type="text" name="phone" /> <br>
            
             <input type="file" name="uploadedImage" /> <br> 
             <input type="submit" /> 

            <label> ${flash.message} </label>
        </g:uploadForm>

        <table>
          <tr>
            <td> Name </td>
            <td> Phone </td>
            <td> Document </td>
          </tr>

          <g:each var="employee" in="${employees}" >
            <tr>
              <td> ${employee.name} </td>
              <td> ${employee.phone} </td>
              <td> <img src="${createLinkTo(dir: 'images/employee/', file: employee.document)}" /> </td>
              <%-- <td> <img src="${createLinkTo(dir: minioUrl)}" /> </td> --%>
              
              
          </tr>
          </g:each>

        </table>
    </div>
    
  </div>


  <script>
        const minioEndpoint = "https://minio.waltonbd.com"; // Your MinIO server URL
        const bucketName = "wc-pos"; // Your MinIO bucket name
        const accessKey = "GL3fVbqbvNz58U4oOtFE"; // Your MinIO access key
        const secretKey = "ikyBaKyjV8bnOYuUkbN5NOzY0zLiFMgS3zMCy2hq"; // Your MinIO secret key
        const region = "us-east-1"; // MinIO region (often 'us-east-1' or custom)
        const fileInput = document.getElementById("fileInput");

        // This function will be called when the user clicks the upload button
        async function uploadFile() {
            const file = fileInput.files[0];
            if (!file) {
                alert("Please select a file.");
                return;
            }

            const objectName = file.name;
            const url = `${minioEndpoint}/${bucketName}/${objectName}`;

            // Get the current date and time in UTC format
            const now = new Date();
            const amzDate = now.toISOString().replace(/[-:]/g, '').split('.')[0] + 'Z';
            const dateStamp = amzDate.substr(0, 8); // YYYYMMDD (date part)

            // Step 1: Create the canonical request
            const method = "PUT";
            const headers = {
                "x-amz-date": amzDate,
                "Content-Type": file.type,
            };

            const canonicalHeaders = `x-amz-date:${amzDate}\n`;
            const signedHeaders = "x-amz-date";
            const payloadHash = await sha256(file); // Use file hash as payload

            const canonicalRequest = `${method}\n/${bucketName}/${objectName}\n\n${canonicalHeaders}\n${signedHeaders}\n${payloadHash}`;

            // Step 2: Create the string to sign
            const credentialScope = `${dateStamp}/${region}/s3/aws4_request`;
            const stringToSign = `AWS4-HMAC-SHA256\n${amzDate}\n${credentialScope}\n${sha256(canonicalRequest)}`;

            // Step 3: Calculate the signature
            const signingKey = getSignatureKey(secretKey, dateStamp, region, "s3");
            const signature = await hmacSHA256(stringToSign, signingKey);

            // Step 4: Create the authorization header
            const authorizationHeader = `AWS4-HMAC-SHA256 Credential=${accessKey}/${credentialScope}, SignedHeaders=${signedHeaders}, Signature=${signature}`;

            // Step 5: Upload the file using fetch API
            try {
                const response = await fetch(url, {
                    method: 'PUT',
                    headers: {
                        'Authorization': authorizationHeader,
                        'x-amz-date': amzDate,
                        'Content-Type': file.type,
                    },
                    body: file,
                });

                if (response.ok) {
                    alert("File uploaded successfully!");
                } else {
                    alert("Failed to upload file. Status code: " + response.status);
                }
            } catch (error) {
                console.error('Error uploading file:', error);
                alert('Error uploading file.');
            }
        }

        // SHA-256 Hashing function
        async function sha256(file) {
            const hashBuffer = await crypto.subtle.digest('SHA-256', file);
            const hashArray = Array.from(new Uint8Array(hashBuffer));
            return hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
        }

        // HMAC-SHA256 function
        async function hmacSHA256(stringToSign, key) {
            const keyBuffer = new TextEncoder().encode(key);
            const stringBuffer = new TextEncoder().encode(stringToSign);
            const hmac = await crypto.subtle.importKey(
                'raw',
                keyBuffer,
                { name: 'HMAC', hash: { name: 'SHA-256' } },
                false,
                ['sign']
            );
            const signatureBuffer = await crypto.subtle.sign('HMAC', hmac, stringBuffer);
            const signatureArray = Array.from(new Uint8Array(signatureBuffer));
            return signatureArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
        }

        // Generate signing key
        function getSignatureKey(secretKey, dateStamp, regionName, serviceName) {
            const kDate = hmacSHA256(dateStamp, 'AWS4' + secretKey);
            const kRegion = hmacSHA256(regionName, kDate);
            const kService = hmacSHA256(serviceName, kRegion);
            return hmacSHA256('aws4_request', kService);
        }
    </script>

</body>
</html>
