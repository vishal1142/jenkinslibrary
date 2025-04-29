def call(Map config) {
    def imageName = config.ImageName
    def imageTag = config.ImageTag
    def accountId = config.AWS_ACCOUNT_ID
    def region = config.REGION
    def repoName = config.ECR_REPO_NAME

    def fullImageName = "${accountId}.dkr.ecr.${region}.amazonaws.com/${repoName}:${imageTag}"

    withCredentials([[
        $class: 'AmazonWebServicesCredentialsBinding',
        credentialsId: 'ecr-credentials'  // ID of the AWS credentials
    ]]) {
        sh """
            aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${accountId}.dkr.ecr.${region}.amazonaws.com
            docker tag ${imageName}:${imageTag} ${fullImageName}
            docker push ${fullImageName}
        """
    }
}
