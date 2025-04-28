def call(String imageName, String dockerfilePath, String tag = 'latest') {
    try {
        // Build Docker image
        def image = docker.build("${imageName}:${tag}", "-f ${dockerfilePath} .")
        
        // Login to AWS ECR
        withAWS(credentials: 'aws-credentials-id') {
            sh 'aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin <your_account_id>.dkr.ecr.us-west-2.amazonaws.com'
        }
        
        // Push Docker image to ECR
        image.push(tag)
        echo "Docker image pushed to ECR: ${imageName}:${tag}"
        
    } catch (Exception e) {
        error "Error pushing Docker image to ECR: ${e.message}"
    }
}
