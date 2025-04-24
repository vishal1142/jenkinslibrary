def call(Map args) {
    def project = args.project ?: error("Missing parameter: project")
    def imageTag = args.imageTag ?: "latest"
    def hubUser = args.hubUser ?: error("Missing parameter: hubUser")

    sh """
        docker image build -t ${hubUser}/${project}:${imageTag} .
        docker image tag ${hubUser}/${project}:${imageTag} ${hubUser}/${project}:latest
    """
}