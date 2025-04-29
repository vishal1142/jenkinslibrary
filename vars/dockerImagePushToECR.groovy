def DockerImagePushToECR(String project, String ImageTag, String ecrUrl, String region) {
    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-creds']]) {
        sh """
            echo "Authenticating Docker with AWS ECR..."
            aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${ecrUrl}
        """
    }

    def fullImageName = "${ecrUrl}/${project}:${ImageTag}"
    def latestImageName = "${ecrUrl}/${project}:latest"

    echo "Tagging image: ${fullImageName}"
    sh "docker tag ${project}:${ImageTag} ${fullImageName}"

    echo "Pushing Docker image: ${fullImageName}"
    sh "docker push ${fullImageName}"

    echo "Tagging image as latest: ${latestImageName}"
    sh "docker tag ${project}:${ImageTag} ${latestImageName}"

    echo "Pushing Docker image: ${latestImageName}"
    sh "docker push ${latestImageName}"
}
