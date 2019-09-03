# Name:     xbin-store-cloud-web-portal
# Time:     2017-05-07

FROM java:8-jre-alpine

MAINTAINER Ryun <gaojiangjian@ryuntech.com>

RUN mkdir /app

WORKDIR /app

COPY ryun-supply-zipkin-1.0-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/ryun-supply-zipkin-1.0-SNAPSHOT.jar.jar"]

EXPOSE 8101
