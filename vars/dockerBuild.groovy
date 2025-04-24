def call(String project, String imageTag, String hubUser, String credId) {
    // Ensure Docker login before building the image
    withCredentials([usernamePassword(credentialsId: credId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
        try {
            // Print the current working directory for debugging
            sh 'pwd'
            sh 'ls -la'

            // Login to DockerHub
            sh """
                echo "Logging in to DockerHub..."
                echo "\$DOCKER_PASSWORD" | docker login -u \$DOCKER_USER --password-stdin
            """

            // Build Docker image
            echo "Building Docker image ${hubUser}/${project}:${imageTag}..."
            sh """
                docker image build -t ${hubUser}/${project}:${imageTag} .
                docker image tag ${hubUser}/${project}:${imageTag} ${hubUser}/${project}:latest
                docker push ${hubUser}/${project}:${imageTag}
                docker push ${hubUser}/${project}:latest
            """
        } catch (Exception e) {
            // Handle any error and provide better error message
            echo "Docker build failed: ${e.message}"
            throw e
        }
    }
}
