pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                dir('backoffice') {
                    sh 'mvn clean package'
                }
            }
        }
        stage('Test') {
            steps {
                dir('backoffice') {
                    sh 'mvn test'
                }
            }
        }
        // Ajoutez d'autres étapes ici si nécessaire
    }
}
