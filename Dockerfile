FROM docker.io/zhangyule1993/container-base:v-0.0.1
COPY jade-pool-web /home/jade-pool-web

RUN npm install -g cnpm --registry=https://registry.npm.taobao.org

ENV active $ACTIVE

COPY jade-pool-web/deploy.sh /home/deploy.sh

WORKDIR /home

CMD ["sh", "deploy.sh"]