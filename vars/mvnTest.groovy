// jenkinslibrary.groovy

def call() {
    try {
        sh 'mvn test'
    } catch (Exception e) {
        echo "Maven Test Failed: ${e.getMessage()}"
        throw e // Re-throw the exception to cause pipeline failure
    }
}
