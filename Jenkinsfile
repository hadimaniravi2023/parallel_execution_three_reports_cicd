pipeline {
    agent any

    // THIS BLOCK FORCES JENKINS TO BIND TO THE WEBHOOK
    properties([
        pipelineTriggers([
            githubPush()
        ])
    ])

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                echo 'Running automation tests...'
                bat 'mvn clean test'
            }
        }
    }
}