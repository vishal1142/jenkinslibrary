def call(credentialsId){
    withSonarQubeEnv(credentialsId: credentialsId) {
        sh 'echo "SonarQube host: $SONAR_HOST_URL"'
        sh 'mvn clean package sonar:sonar'
    }
}
def call(credentialsId, sonarProjectKey, sonarProjectName, sonarProjectVersion){
    withSonarQubeEnv(credentialsId: credentialsId) {
        sh 'echo "SonarQube host: $SONAR_HOST_URL"'
        sh "mvn clean package sonar:sonar -Dsonar.projectKey=${sonarProjectKey} -Dsonar.projectName=${sonarProjectName} -Dsonar.projectVersion=${sonarProjectVersion}"
    }