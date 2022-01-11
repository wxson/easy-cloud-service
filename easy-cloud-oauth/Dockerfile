FROM java:8-jre

MAINTAINER wxseon@163.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /jiayou-spd-auth

WORKDIR /jiayou-spd-auth

EXPOSE 9529

ADD ./target/jiayou-spd-auth.jar ./

CMD java $JAVA_OPTS -jar jiayou-spd-auth.jar
