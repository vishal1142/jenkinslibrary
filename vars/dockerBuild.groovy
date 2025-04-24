def call(String project, String ImageTag, String hubUser) {
    // Ensure Docker login before building the image
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
        try {
            // Print the current working directory for debugging
            sh 'pwd'
            sh 'ls -la'

            // Login to DockerHub
            sh """
                echo "Logging in to DockerHub..."
                docker login -u ${DOCKER_USER} -p ${DOCKER_PASSWORD}
            """

            // Build Docker image
            echo "Building Docker image ${hubUser}:${project}..."
            sh """
                docker image build -t ${hubUser}:${project} .
                docker image tag ${hubUser}:${project} ${hubUser}:${project}:${ImageTag}
                docker image tag ${hubUser}:${project} ${hubUser}:${project}:latest
            """
        } catch (Exception e) {
            // Handle any error and provide better error message
            echo "Docker build failed: ${e.message}"
            throw e
        }
    }
}
