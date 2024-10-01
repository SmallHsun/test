pipeline {
    agent any

    environment {
	MAVEN_HOME = "/usr/share/maven"
        JAVA_HOME = "/opt/java/openjdk"
        SSH_USER = 'user'     
        SSH_HOST = '192.168.1.106'         
        REMOTE_PATH = '/home/user/SpringBoot'        
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/SmallHsun/test.git'
            }
        }

        stage('Build') {
            steps {
                // 使用Maven打包Spring Boot項目
                sh """
                ${MAVEN_HOME}/bin/mvn clean package
                """
            }
        }

        stage('Transfer JAR to Ubuntu') {
            steps {
                // 使用SCP將JAR文件從Jenkins傳輸到Ubuntu虛擬機
                sh """
                scp -o StrictHostKeyChecking=no target/demo1-0.0.1-SNAPSHOT.jar ${SSH_USER}@${SSH_HOST}:${REMOTE_PATH}
                """
            }
        }

        stage('Deploy and Start Spring Boot') {
            steps {
                // 使用SSH登錄到Ubuntu虛擬機並啟動Spring Boot應用
                sh """
                ssh -o StrictHostKeyChecking=no ${SSH_USER}@${SSH_HOST} '
                nohup java -jar ${REMOTE_PATH}/demo1-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
                '
                """
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
