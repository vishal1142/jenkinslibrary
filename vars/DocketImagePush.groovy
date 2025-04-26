def call(String project, String ImageTag, String hubUser) {
    withCredentials([usernamePassword(
        credentialsId: 'vishal',
        usernameVariable: 'USER',
        passwordVariable: 'PASS'
    )]) {
        sh """
            echo "Logging into DockerHub with user: \$USER"
            docker login -u "\$USER" -p "\$PASS"
        """
    }

    def fullImageName = "${hubUser}/${project}:${ImageTag}"
    def latestImageName = "${hubUser}/${project}:latest"

    echo "Pushing Docker image: ${fullImageName}"
    sh "docker push ${fullImageName}"

    echo "Tagging image as latest: ${latestImageName}"
    sh "docker tag ${fullImageName} ${latestImageName}"   // <--- THIS was missing

    echo "Pushing Docker image: ${latestImageName}"
    sh "docker push ${latestImageName}"
}
