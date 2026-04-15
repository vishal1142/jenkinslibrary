def call() {
    sh '''
        if command -v mvn >/dev/null 2>&1; then
            echo "[mvnBuild] Using local Maven"
            mvn clean install
        else
            echo "[mvnBuild] Local Maven not found, using Docker Maven image"
            mkdir -p "$HOME/.m2"
            docker run --rm \
                -v "$PWD":/workspace \
                -v "$HOME/.m2":/root/.m2 \
                -w /workspace \
                maven:3.9.9-eclipse-temurin-17 \
                mvn clean install
        fi
    '''
}