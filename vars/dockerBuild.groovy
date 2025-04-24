def call(Map args) {
    def project = args.project
    def imageTag = args.imageTag
    def hubUser = args.hubUser

    sh """
        docker image build -t ${hubUser}/${project} .
        docker image tag ${hubUser}/${project} ${hubUser}/${project}:${imageTag}
        docker image tag ${hubUser}/${project} ${hubUser}/${project}:latest
    """
}