pipeline{
    agent any

    stages{
        stage('Build'){
            steps{
                echo 'Hello Worlds'
                sh 'pwd'
            }
        }
    }
    options{
        withCredentials('test')
    }
}