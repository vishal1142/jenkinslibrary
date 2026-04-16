def call(Map args) {
    def project   = args.ImageName ?: error("❌ Missing parameter: ImageName")
    def imageTag  = args.ImageTag ?: "latest"
    def hubUser   = args.DockerHubUser ?: error("❌ Missing parameter: DockerHubUser")

    // This variable is only available inside this method
    def fullImageName = "${hubUser}/${project}:${imageTag}"

    // Run Trivy from container to avoid host-level Trivy installation drift.
    // Prefer GHCR image; fallback to Docker Hub with pinned tag.
    sh """
        echo "Running Trivy scan on ${fullImageName}..."
        TRIVY_IMAGE="ghcr.io/aquasecurity/trivy:latest"
        if ! docker pull "\$TRIVY_IMAGE" >/dev/null 2>&1; then
          TRIVY_IMAGE="aquasec/trivy:0.57.1"
          docker pull "\$TRIVY_IMAGE" >/dev/null 2>&1
        fi
        docker run --rm "\$TRIVY_IMAGE" image \
          --timeout 5m \
          --severity HIGH,CRITICAL \
          --exit-code 1 \
          --no-progress \
          ${fullImageName}
    """
}
