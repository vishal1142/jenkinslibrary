// vars/gitCheckout.groovy
def call(Map stageParams = [:]) {
    if (!stageParams.branch || !stageParams.url) {
        error("Missing required parameters: 'branch' and 'url'")
    }

    checkout([
        $class: 'GitSCM',
        branches: [[name: stageParams.branch]],
        userRemoteConfigs: [[url: stageParams.url]]
    ])
}
