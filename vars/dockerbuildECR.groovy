// vars/ecr.groovy
def authenticateToECR(credentialsId, awsRegion, ecrRegistry) {
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
        // Perform the AWS ECR login
        sh """
            aws ecr get-login-password --region ${awsRegion} | docker login --username ${DOCKER_USERNAME} --password-stdin ${ecrRegistry}
        """
    }
}

return this
