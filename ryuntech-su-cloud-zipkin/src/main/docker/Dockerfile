

FROM java:8-jre-alpine

MAINTAINER Ryun <gaojiangjian@ryuntech.com>

ARG workdir=/app

VOLUME ${workdir}

WORKDIR ${workdir}

ADD ryun-supply-zipkin-1.0-SNAPSHOT.jar ryun-supply-zipkin.jar

EXPOSE 3001

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ryun-supply-zipkin.jar"]



