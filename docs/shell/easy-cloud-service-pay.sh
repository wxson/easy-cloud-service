#!/bin/sh
#------------------------------------------------------------------------
# author : GR
# date : 2019-04-13
# email : wxseon@163.com
# function : 该脚本实现java应用服务的启动start、停止stop、重启restart
#------------------------------------------------------------------------

#进入当前用户的主目录
LOGS_DIR=~/easy-cloud-service-game/
cd $LOGS_DIR
SERVICE_DIR=$(pwd)
SERVICE_NAME=easy-cloud-service-pay-biz
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid

cd $SERVICE_DIR

case "$1" in

    start)
        #判断日志目录是否存在
        if [ ! -d "logs" ];then
	        echo "logs目录不存在、开始创建日志目录"
             mkdir -p  $LOGS_DIR/logs/
        else
             echo "logs目录已经存在、不需创建日志目录"
        fi
        #使用后台命令启动
        nohup java -Dspring.profiles.active=test  -Xms1024m -Xmx1024m -jar $JAR_NAME >/dev/null 2>&1 &
        echo $! > $SERVICE_DIR/$PID
        echo "=== start $SERVICE_NAME"
        ;;

    stop)
        kill `cat $SERVICE_DIR/$PID`
        rm -rf $SERVICE_DIR/$PID
        echo "=== stop $SERVICE_NAME"

        sleep 5
		##
        P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "=== $SERVICE_NAME process not exists or stop success"
        else
            echo "=== $SERVICE_NAME process pid is:$P_ID"
            echo "=== begin kill $SERVICE_NAME process, pid is:$P_ID"
            kill -9 $P_ID
        fi
        ;;

    restart)
        $0 stop
        sleep 2
        $0 start
        echo "=== restart $SERVICE_NAME"
        ;;

    *)
        ## restart
        $0 stop
        sleep 2
        $0 start
        ;;

esac
exit 0
