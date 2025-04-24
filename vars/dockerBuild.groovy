def call(Map args) {
    // Use parameter names that match the Jenkinsfile exactly
    def project = args.ImageName ?: error("Missing parameter: ImageName")  // Corresponds to 'ImageName' in Jenkinsfile
    def imageTag = args.ImageTag ?: "latest"  // Default to 'latest' if not provided, corresponds to 'ImageTag' in Jenkinsfile
    def hubUser = args.DockerHubUser ?: error("Missing parameter: DockerHubUser")  // Corresponds to 'DockerHubUser' in Jenkinsfile

    // Docker build and tagging commands
    sh """
        docker image build -t ${hubUser}/${project}:${imageTag} .
        docker image tag ${hubUser}/${project}:${imageTag} ${hubUser}/${project}:latest
    """
}
