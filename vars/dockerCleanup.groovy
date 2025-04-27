def call() {
    echo "Cleaning up dangling Docker images and unused resources..."

    sh '''
        echo "Removing dangling Docker images..."
        docker image prune -f

        echo "Removing unused Docker volumes..."
        docker volume prune -f

        echo "Removing unused Docker networks..."
        docker network prune -f

        echo "Cleanup completed."
    '''
}
