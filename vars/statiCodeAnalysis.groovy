def call(Map params) {
    def sonarInstanceName = params.get('sonarInstanceName', 'SonarQube') // Use the name from Jenkins config
    def sonarHostUrl = params.get('sonarHostUrl', 'http://localhost:9000')
    def sonarProjectKey = params.get('sonarProjectKey', null)
    def sonarProjectName = params.get('sonarProjectName', null)
    def sonarProjectVersion = params.get('sonarProjectVersion', null)

    withSonarQubeEnv(sonarInstanceName) {
        env.SONAR_HOST_URL = sonarHostUrl

        sh 'echo "SonarQube host: $SONAR_HOST_URL"'
        
        def sonarCmd = 'mvn clean package sonar:sonar'
        if (sonarProjectKey && sonarProjectName && sonarProjectVersion) {
            sonarCmd += " -Dsonar.projectKey=${sonarProjectKey} -Dsonar.projectName=${sonarProjectName} -Dsonar.projectVersion=${sonarProjectVersion}"
        }

        sh sonarCmd
    }
}
