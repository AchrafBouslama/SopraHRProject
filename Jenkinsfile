pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
             
                    sh 'mvn clean package'
             
            }
        }
        stage('Test') {
            steps {
             
                    sh 'mvn test'
               
            }
        }
        // Ajoutez d'autres étapes ici si nécessaire
    }
}
