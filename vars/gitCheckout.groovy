// This is gitCheckout.groovy
def call(Map stageParams) {
    checkout([
        $class: 'GitSCM',
        branches: [[name:  stageParams.branch]],
        userRemoteConfigs: [[ url: stageParams.url ]]
    ])
}
