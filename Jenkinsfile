pipeline {
    agent any
    environment {
        MAVEN_OPTS = '-Xms256m -Xmx512m'
    }

    tools {
        // This must match the name you gave to Maven in Jenkins -> Manage Jenkins -> Global Tool Configuration
        maven 'Maven 3.9.16'
    }

    stages {
        stage('1. Pull Code') {
            steps {
                echo 'Pulling latest code from GitHub...'
                // Jenkins automatically pulls the code based on the job configuration we did earlier
            }
        }

        stage('2. Build JAR') {
            steps {
                echo 'Building Spring Boot application with Maven...'
                // Runs maven to clean old builds and package a new .jar file.
                // We skip tests (-DskipTests) to save precious RAM on AWS Free Tier.
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('3. Deploy App') {
            steps {
                echo 'Deploying application to EC2...'

                // This script handles stopping the old version of your app and starting the new one
                sh '''
                    echo "Stopping current running Spring Boot application..."
                    # This finds the process ID of your old running JAR and stops it gently. If none is running, it won't crash.
                    sudo kill $(pgrep -f '.*\\.jar') || true

                    echo "Copying the newly built JAR file..."
                    # Finds the built jar in the target folder and copies it to a safe deployment directory
                    cp target/*.jar /home/ubuntu/app.jar

                    echo "Starting the new Spring Boot application in the background..."
                    # 'nohup' keeps the app running even after Jenkins finishes the job.
                    # '> app.log 2>&1 &' redirects all console logs to a file and runs it in the background.
                    cd /home/ubuntu
                    BUILD_ID=dontKillMe nohup java -jar app.jar > app.log 2>&1 &

                    echo "Deployment completed successfully!"
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully! Your app is live.'
        }
        failure {
            echo 'Pipeline failed. Check the Jenkins console log to see what went wrong.'
        }
    }
}