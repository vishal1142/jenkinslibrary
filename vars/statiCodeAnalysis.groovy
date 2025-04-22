def call(Map params) {
    def credentialsId = params.credentialsId
    def sonarProjectKey = params.get('sonarProjectKey', null)
    def sonarProjectName = params.get('sonarProjectName', null)
    def sonarProjectVersion = params.get('sonarProjectVersion', null)

    withSonarQubeEnv(credentialsId: credentialsId) {
        sh 'echo "SonarQube host: $SONAR_HOST_URL"'
        
        def sonarCmd = 'mvn clean package sonar:sonar'
        if (sonarProjectKey && sonarProjectName && sonarProjectVersion) {
            sonarCmd += " -Dsonar.projectKey=${sonarProjectKey} -Dsonar.projectName=${sonarProjectName} -Dsonar.projectVersion=${sonarProjectVersion}"
        }
        
        sh sonarCmd
    }
}
