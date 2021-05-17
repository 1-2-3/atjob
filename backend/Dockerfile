FROM maven:3.6.3-jdk-11

ENV TZ=Asia/Shanghai

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

COPY ./.mvn/settings.xml /usr/share/maven/conf

WORKDIR /app