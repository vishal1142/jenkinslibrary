// dockerpush.groovy
def call(Map params) {
    script {
        // Pull Docker image from DockerHub
        echo "Tagging and pushing Docker image to AWS ECR..."
        sh """
            docker tag ${params.DockerHubUser}/${params.ImageName}:${params.ImageTag} ${params.AWS_ACCOUNT_ID}.dkr.ecr.${params.AWS_REGION}.amazonaws.com/${params.ECR_REPO_NAME}:${params.ImageTag}
            docker push ${params.AWS_ACCOUNT_ID}.dkr.ecr.${params.AWS_REGION}.amazonaws.com/${params.ECR_REPO_NAME}:${params.ImageTag}
        """
    }
}
