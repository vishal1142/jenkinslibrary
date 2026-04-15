def call(Map params = [:]) {
    // Extract parameters
    def credentialsId        = params.get('credentialsId')
    // Must match Jenkins → Manage Jenkins → SonarQube installations (CasC: local-sonarqube).
    def installationName     = params.get('installationName', 'local-sonarqube')
    def sonarHostUrl         = params.get('sonarHostUrl', 'http://code-analyzer:9000')
    def sonarProjectKey      = params.get('sonarProjectKey')
    def sonarProjectName     = params.get('sonarProjectName')
    def sonarProjectVersion  = params.get('sonarProjectVersion')

    // Ensure credentialsId is provided
    if (!credentialsId) {
        error "Missing required parameter: credentialsId"
    }

    // Named installation is required (see Jenkins SonarQube Scanner plugin docs).
    withSonarQubeEnv(installationName: installationName, credentialsId: credentialsId) {
        echo "Using SonarQube host: ${sonarHostUrl}"
        env.SONAR_HOST_URL = sonarHostUrl

        // Construct the SonarQube arguments to run analysis
        def sonarArgs = 'clean package sonar:sonar'

        // Append project details if provided
        if (sonarProjectKey && sonarProjectName && sonarProjectVersion) {
            sonarArgs += " -Dsonar.projectKey='${sonarProjectKey}'"
            sonarArgs += " -Dsonar.projectName='${sonarProjectName}'"
            sonarArgs += " -Dsonar.projectVersion='${sonarProjectVersion}'"
        }

        def sonarCmd = "mvn ${sonarArgs}"
        echo "Executing SonarQube analysis with command: ${sonarCmd}"
        sh """
            if command -v mvn >/dev/null 2>&1; then
                echo "[staticCodeAnalysis] Using local Maven"
                ${sonarCmd}
            else
                echo "[staticCodeAnalysis] Local Maven not found, using Docker Maven image"
                mkdir -p "\$HOME/.m2"
                docker run --rm \\
                    -v "\$PWD":/workspace \\
                    -v "\$HOME/.m2":/root/.m2 \\
                    -w /workspace \\
                    maven:3.9.9-eclipse-temurin-17 \\
                    ${sonarCmd}
            fi
        """
    }
}
