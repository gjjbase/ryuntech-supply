

FROM java:8-jre-alpine

MAINTAINER Ryun <gaojiangjian@ryuntech.com>

ARG workdir=/app

VOLUME ${workdir}

WORKDIR ${workdir}

ADD ryuntech-su-cloud-admin-biz-1.0-SNAPSHOT.jar ryuntech-su-cloud-admin-biz.jar

EXPOSE 4100

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ryuntech-su-cloud-admin-biz.jar"]



