

FROM java:8-jre-alpine

MAINTAINER Ryun <gaojiangjian@ryuntech.com>

ARG workdir=/app

VOLUME ${workdir}

WORKDIR ${workdir}

ADD ryuntech-su-cloud-gateway-1.0-SNAPSHOT.jar ryuntech-su-cloud-gateway.jar

EXPOSE 9999

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ryuntech-su-cloud-gateway.jar"]



