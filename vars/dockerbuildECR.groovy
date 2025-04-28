def call(String AWS_ACCOUNT_ID, String REGION, String ECR_REPO_NAME) {
    sh """
        docker build -t ${ECR_REPO_NAME} .
        docker tag ${ECR_REPO_NAME}:latest ${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/${ECR_REPO_NAME}:latest
    """
}
