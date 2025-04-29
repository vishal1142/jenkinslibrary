def call(Map config) {
    // Extract required parameters from the config map
    def awsAccountId = config.awsAccountId
    def region       = config.region
    def ecrRepoName  = config.ecrRepoName
    def imageName    = config.imageName
    def imageTag     = config.imageTag

    // Construct the full ECR image URL
    def fullEcrImageUrl = "${awsAccountId}.dkr.ecr.${region}.amazonaws.com/${ecrRepoName}:${imageTag}"

    // Tag and push the Docker image to ECR
    sh """
        echo "Tagging Docker image: ${imageName}:${imageTag} -> ${fullEcrImageUrl}"
        docker tag ${imageName}:${imageTag} ${fullEcrImageUrl}

        echo "Authenticating with AWS ECR..."
        aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${awsAccountId}.dkr.ecr.${region}.amazonaws.com

        echo "Pushing Docker image to ECR repository..."
        docker push ${fullEcrImageUrl}

        echo "Docker image pushed successfully: ${fullEcrImageUrl}"
    """
}
