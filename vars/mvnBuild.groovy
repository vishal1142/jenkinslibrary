def call() {
    echo "[Build] Starting Maven build..."

    withEnv(["JAVA_HOME=${tool 'JDK11'}", "PATH+MAVEN=${tool 'Maven3'}/bin"]) {
        sh 'mvn clean install -DskipTests=false'
    }

    echo "[Build] Maven build completed successfully."
}
