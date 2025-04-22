def call(Map params = [:]) {
    def credentialsId        = params.get('credentialsId')
    def sonarHostUrl         = params.get('sonarHostUrl', 'http://localhost:9000')
    def sonarProjectKey      = params.get('sonarProjectKey')
    def sonarProjectName     = params.get('sonarProjectName')
    def sonarProjectVersion  = params.get('sonarProjectVersion')

    if (!credentialsId) {
        error "Missing required parameter: credentialsId"
    }

    withSonarQubeEnv(credentialsId: credentialsId) {
        echo "Using SonarQube host: ${sonarHostUrl}"
        env.SONAR_HOST_URL = sonarHostUrl

        def sonarCmd = 'mvn clean package sonar:sonar'

        if (sonarProjectKey && sonarProjectName && sonarProjectVersion) {
            sonarCmd += " -Dsonar.projectKey='${sonarProjectKey}'"
            sonarCmd += " -Dsonar.projectName='${sonarProjectName}'"
            sonarCmd += " -Dsonar.projectVersion='${sonarProjectVersion}'"
        }

        sh sonarCmd
    }
}
