def call(Map args) {
    def project   = args.ImageName ?: error("❌ Missing parameter: ImageName")
    def imageTag  = args.ImageTag ?: "latest"
    def hubUser   = args.DockerHubUser ?: error("❌ Missing parameter: DockerHubUser")

    // This variable is only available inside this method
    def fullImageName = "${hubUser}/${project}:${imageTag}"

    // Run Trivy from container to avoid host-level Trivy installation drift.
    sh """
        echo "🔍 Running Trivy scan on ${fullImageName}..."
        docker run --rm aquasec/trivy:latest image \
          --timeout 5m \
          --severity HIGH,CRITICAL \
          --exit-code 1 \
          --no-progress \
          ${fullImageName}
    """
}
