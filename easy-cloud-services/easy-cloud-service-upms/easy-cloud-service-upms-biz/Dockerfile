FROM java:8-jre

MAINTAINER wxseon@163.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /easy-cloud-service-upms-biz

WORKDIR /easy-cloud-service-upms-biz

EXPOSE 9528

ADD ./target/easy-cloud-service-upms-biz.jar ./

CMD java $JAVA_OPTS -jar easy-cloud-service-upms-biz.jar
