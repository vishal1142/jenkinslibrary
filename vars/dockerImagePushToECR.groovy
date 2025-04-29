def call(String imageName, String tag, String accountId, String region) {
    def repoUrl = "${accountId}.dkr.ecr.${region}.amazonaws.com"
    def fullImageName = "${repoUrl}/${imageName}:${tag}"
    def latestImageName = "${repoUrl}/${imageName}:latest"

    withCredentials([usernamePassword(
        credentialsId: 'aws-ecr-creds',
        usernameVariable: 'AWS_ACCESS_KEY_ID',
        passwordVariable: 'AWS_SECRET_ACCESS_KEY'
    )]) {
        sh """
            export AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
            export AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY

            aws ecr get-login-password --region ${region} | \
            docker login --username AWS --password-stdin ${repoUrl}

            docker tag ${imageName}:${tag} ${fullImageName}
            docker tag ${imageName}:${tag} ${latestImageName}

            docker push ${fullImageName}
            docker push ${latestImageName}
        """
    }
}
