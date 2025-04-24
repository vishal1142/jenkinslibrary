def call(Map args) {
    def project  = args.ImageName ?: error("Missing parameter: ImageName")
    def imageTag = args.ImageTag ?: "latest"
    def hubUser  = args.DockerHubUser ?: error("Missing parameter: DockerHubUser")

    sh """
        echo "Running Trivy scan on ${hubUser}/${project}:${imageTag}..."
        trivy image ${hubUser}/${project}:${imageTag} > scan.txt
        cat scan.txt
    """
}