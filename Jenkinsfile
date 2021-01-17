pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn clean package -Dmaven.test.skip=true"
            }
        }
        stage('Run test') {
            steps {
                sh "mvn test"
            }
        }
        stage('Compile') {
            steps {
                sh "mvn clean compile"
            }
        }
    }
}
