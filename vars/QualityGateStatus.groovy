def call(Map params = [:]) {
    def credentialsId = params.get('credentialsId')

    if (!credentialsId) {
        error "Missing required parameter: credentialsId"
    }

    echo "Waiting for SonarQube Quality Gate result..."
    waitForQualityGate abortPipeline: true, credentialsId: credentialsId
}
