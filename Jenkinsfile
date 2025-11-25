pipeline {
    agent any
    
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-cred')
        DOCKER_IMAGE = 'ghassenhchaichi/student-management'
    }
    
    stages {
        stage('Build Maven') {
            steps {
                sh 'mvn -version'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Construction de l image Docker...'
                    sh 'docker --version'
                    sh '''
                        docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} .
                        docker tag ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest
                    '''
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                    echo 'Push vers DockerHub...'
                    sh '''
                        echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin
                        docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}
                        docker push ${DOCKER_IMAGE}:latest
                    '''
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline CI/CD terminé avec succès!'
            sh 'docker logout'
        }
    }
}