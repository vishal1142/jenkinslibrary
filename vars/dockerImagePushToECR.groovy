def call(Map config) {
    def awsAccountId = config.awsAccountId
    def region = config.region
    def ecrRepoName = config.ecrRepoName
    def imageName = config.imageName
    def imageTag = config.imageTag

    def fullEcrUrl = "${awsAccountId}.dkr.ecr.${region}.amazonaws.com/${ecrRepoName}:${imageTag}"

    sh """
        echo "Tagging Docker image..."
        docker tag ${imageName}:${imageTag} ${fullEcrUrl}

        echo "Pushing Docker image to ECR..."
        docker push ${fullEcrUrl}
    """
}
