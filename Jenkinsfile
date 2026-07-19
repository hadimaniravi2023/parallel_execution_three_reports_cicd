pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Jenkins automatically pulls your code, but you can explicitly confirm it here
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                echo 'Running automation tests...'
                // If using Maven, uncomment the line below:
                 bat 'mvn clean test' // Use 'sh' instead of 'bat' if on Mac/Linux
            }
        }
        stage('Generate Reports') {
            steps {
                echo 'Publishing test reports...'
                // Add your report archiving steps here


                // 1. THIS IS THE CRITICAL CHANGE: This saves your reports so you can view them in Jenkins UI.
                                // Replace 'target/cucumber-reports/**/*' with the exact folder path where your framework generates its 3 reports.
                                archiveArtifacts artifacts: 'target/cucumber-reports/**/*', allowEmptyArchive: true

                                // 2. OPTIONAL: If you use the standard Cucumber HTML plugin in Jenkins, uncomment the line below:
                                // cucumber buildResult: 'SUCCESS', reportDir: 'target/cucumber-reports'
            }
        }
    }

    // 3. ADDED BONUS: This ensures your reports are still captured even if some tests fail!
        post {
            always {
                echo 'Cleaning up workspace and ensuring reports are saved...'
                // This runs even if 'mvn clean test' fails, ensuring you can see WHICH test failed.
                junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
            }
        }
}