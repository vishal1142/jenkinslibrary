// dockerPushEcr.groovy

def call(Map config) {
    def ecrImageName = "${config.AWS_ACCOUNT_ID}.dkr.ecr.${config.REGION}.amazonaws.com/${config.ECR_REPO_NAME}:${config.ImageTag}"
    def sourceImage = "${config.DockerHubUser}/${config.ImageName}:${config.ImageTag}"
    echo "Pushing Docker image to AWS ECR: ${ecrImageName}"

    // Tagging the image with the ECR repository name
    sh "docker tag ${sourceImage} ${ecrImageName}"

    // Pushing the image to AWS ECR
    sh "docker push ${ecrImageName}"
}
