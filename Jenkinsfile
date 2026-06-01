pipeline {
    agent any
    environment {
        MAVEN_OPTS = '-Xms256m -Xmx512m'
    }

    tools {
        // This must match the name you gave to Maven in Jenkins -> Manage Jenkins -> Tools
        maven 'Maven 3.9.16'
    }

    stages {
        stage('1. Pull Code') {
            steps {
                echo 'Pulling latest code from GitHub...'
            }
        }

        stage('2. Build JAR') {
            steps {
                echo 'Building Spring Boot application with Maven...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('3. Deploy App') {
            steps {
                echo 'Deploying application to Amazon Linux EC2...'

                sh '''
                    echo "Stopping current running Spring Boot application..."
                    # Safely kills any old running instances using pkill.
                    sudo pkill -f spring-boot-aws || true

                    echo "Copying the newly built JAR file..."
                    # Copies the artifact to our dedicated deployment directory
                    cp target/*.jar /opt/app/app.jar

                    echo "Starting the new Spring Boot application in the background on port 8181..."
                    # Navigates to the deployment directory and starts the app
                    cd /opt/app
                    BUILD_ID=dontKillMe nohup java -jar app.jar --server.port=8181 > app.log 2>&1 &

                    echo "Deployment completed successfully!"
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully! Your app is live on port 8181.'
        }
        failure {
            echo 'Pipeline failed. Check the Jenkins console log to see what went wrong.'
        }
    }
}