def call(Map args) {
    def project  = args.ImageName ?: error("Missing parameter: ImageName")
    def imageTag = args.ImageTag ?: "latest"
    def hubUser  = args.DockerHubUser ?: error("Missing parameter: DockerHubUser")

    sh """
        echo "📥 Preloading Trivy vulnerability database (if not already cached)..."
        trivy image --download-db-only || true

        echo "🔍 Running Trivy scan on image: ${fullImageName}"
        trivy image ${hubUser}/${project}:${imageTag} > scan.txt
        
        echo "📄 Vulnerability scan results:"
        cat scan.txt
    """
}