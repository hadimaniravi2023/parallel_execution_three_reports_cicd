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
                // bat 'mvn clean test' // Use 'sh' instead of 'bat' if on Mac/Linux
            }
        }
        stage('Generate Reports') {
            steps {
                echo 'Publishing test reports...'
                // Add your report archiving steps here
            }
        }
    }
}