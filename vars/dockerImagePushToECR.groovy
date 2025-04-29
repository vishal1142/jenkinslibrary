def DockerImagePushToECR(String imageName, String imageTag, String registryUrl, String region) {
    def fullImageName = "${registryUrl}/${imageName}:${imageTag}"
    def latestImageName = "${registryUrl}/${imageName}:latest"

    withCredentials([usernamePassword(
        credentialsId: 'aws-ecr-creds',
        usernameVariable: 'AWS_ACCESS_KEY_ID',
        passwordVariable: 'AWS_SECRET_ACCESS_KEY'
    )]) {
        sh """
            echo "Authenticating with AWS ECR..."
            export AWS_ACCESS_KEY_ID=\$AWS_ACCESS_KEY_ID
            export AWS_SECRET_ACCESS_KEY=\$AWS_SECRET_ACCESS_KEY
            aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${registryUrl}
        """
    }

    echo "Tagging Docker image..."
    sh "docker tag ${imageName}:${imageTag} ${fullImageName}"
    sh "docker tag ${imageName}:${imageTag} ${latestImageName}"

    echo "Pushing Docker images to ECR..."
    sh "docker push ${fullImageName}"
    sh "docker push ${latestImageName}"
}
