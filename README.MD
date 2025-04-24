git init
git remote add origin https://github.com/vishal1142/jenkinslibrary.git
git add .
git commit -m "Initial commit"
git push -u origin master



def call(String project, String imageTag, String hubUser){

    sh """
        docker image build -t ${hubUser}:${project} .
        docker image tag ${hubUser}:${project} ${hubUser}:${project}:${ImageTag}
        docker image tag ${hubUser}:${project} ${hubUser}:${project}:latest
    """
}