def call(Map args) {
    // Use parameter names that match the Jenkinsfile exactly
    def project = args.ImageName ?: error("Missing parameter: ImageName")  // Corresponds to 'ImageName' in Jenkinsfile
    def imageTag = args.ImageTag ?: "latest"  // Default to 'latest' if not provided, corresponds to 'ImageTag' in Jenkinsfile
    def hubUser = args.DockerHubUser ?: error("Missing parameter: DockerHubUser")  // Corresponds to 'DockerHubUser' in Jenkinsfile

    // Docker build and tagging commands
    sh """
        trivy image ${hubUser}/${project}:${imageTag} ${hubUser}/${project}:latest > scan.txt
        trivy image ${hubUser}/${project}:${imageTag} ${hubUser}/${project}:latest --severity HIGH,CRITICAL --exit-code 1 --ignore-unfixed --no-progress > scan.txt
        cat scan.txt
        if grep -q "HIGH\|CRITICAL" scan.txt; then
            echo "Vulnerabilities found in the image. Please check the scan report."
            exit 1
        else
            echo "No vulnerabilities found in the image."
        fi
    """
}
