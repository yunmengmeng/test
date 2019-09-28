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
        githubConnection('test')
    }
}