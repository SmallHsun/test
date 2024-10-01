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
                sshagent(['jenkins-ssh-key-id']) {
                    sh """
                    scp -o StrictHostKeyChecking=no target/demo1-0.0.1-SNAPSHOT.jar ${SSH_USER}@${SSH_HOST}:${REMOTE_PATH}
                    """
                }
            }
        }

        stage('Deploy and Start Spring Boot') {
            steps {
                sshagent(['jenkins-ssh-key-id']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${SSH_USER}@${SSH_HOST} '
                    set -x;  # 開啟調試模式

                    # 停止正在運行的Spring Boot應用
                    if ps -ef | grep -v grep | grep -q demo1-0.0.1-SNAPSHOT.jar; then
                        echo "Stopping the running Spring Boot application...";
                        pkill -f demo1-0.0.1-SNAPSHOT.jar;
                    else
                        echo "No running Spring Boot application found.";
                    fi

                    # 等待一段時間以確保進程已經停止
                    sleep 10;

                    # 啟動新的Spring Boot應用
                    echo "Starting the new Spring Boot application...";
                    nohup java -jar ${REMOTE_PATH}/demo1-0.0.1-SNAPSHOT.jar > ${REMOTE_PATH}/app.log 2>&1 &
                    
                    # 等待一段時間以確保進程已經啟動
                    sleep 5;

                    # 檢查新應用是否成功啟動
                    if ps -ef | grep -v grep | grep -q demo1-0.0.1-SNAPSHOT.jar; then
                        echo "Spring Boot application started successfully.";
                    else
                        echo "Failed to start Spring Boot application.";
                        cat ${REMOTE_PATH}/app.log;  # 打印日誌以便檢查
                    fi
                    '
                    """
                }
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
