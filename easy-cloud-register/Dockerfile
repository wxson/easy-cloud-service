FROM openjdk:12

#定义编写dockerfile作者邮箱
MAINTAINER 1084424111@qq.com

#定义容器用户和工作空间目录
USER root
WORKDIR /root

#制作时间同步链接
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime


#应用服务端口
EXPOSE 8848

#解决容器中出现乱码
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

#将mvn package编译后的jar包发送到docker中
ADD easy-cloud-register.jar easy-cloud-register.jar

#source环境变量
CMD  source /etc/profile

#服务启动命令
CMD java -Dspring.profiles.active=dev -Xms512m -Xmx512m -jar easy-cloud-register.jar
