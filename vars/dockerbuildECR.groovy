// EcrBuildPush.groovy
def call(String awsAccountId, String region, String ecrRepoName, String imageTag) {
    // Authenticate Docker to Amazon ECR
    echo "Authenticating Docker to Amazon ECR..."
    sh """
        aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${awsAccountId}.dkr.ecr.${region}.amazonaws.com
    """

    // Build the Docker image
    echo "Building Docker image..."
    sh "docker build -t ${ecrRepoName}:${imageTag} ."

    // Tag the Docker image
    echo "Tagging Docker image..."
    sh """
        docker tag ${ecrRepoName}:${imageTag} ${awsAccountId}.dkr.ecr.${region}.amazonaws.com/${ecrRepoName}:${imageTag}
    """

    // Push the Docker image to ECR
    echo "Pushing Docker image to ECR..."
    sh """
        docker push ${awsAccountId}.dkr.ecr.${region}.amazonaws.com/${ecrRepoName}:${imageTag}
    """
}
