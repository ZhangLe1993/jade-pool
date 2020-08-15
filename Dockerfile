FROM docker.io/zhangyule1993/java-base:v1.0.0

RUN npm install -g cnpm --registry=https://registry.npm.taobao.org

ENV active $ACTIVE

COPY deploy.sh /root/deploy.sh

COPY settings.xml /root/settings.xml

WORKDIR /root

CMD ["sh", "deploy.sh"]