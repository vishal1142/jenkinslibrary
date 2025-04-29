// jenkinslibrary/vars/dockerImagePushToECR.groovy

def call(Map config) {
    def imageName = config.ImageName
    def imageTag = config.ImageTag
    def accountId = config.AWS_ACCOUNT_ID
    def region = config.REGION
    def repoName = config.ECR_REPO_NAME

    def fullImageName = "${accountId}.dkr.ecr.${region}.amazonaws.com/${repoName}:${imageTag}"

    sh """
        aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${accountId}.dkr.ecr.${region}.amazonaws.com
        docker tag ${imageName}:${imageTag} ${fullImageName}
        docker push ${fullImageName}
    """
}
