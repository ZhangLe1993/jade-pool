cd /root/code
git pull

cd /root/code/jade-pool-web
cnpm install
cnpm run build

cd /root/code/jade-pool-app
mvn clean package -Dmaven.test.skip=true --settings /root/settings.xml

cp -f target/*.jar /root/ws/app.jar
cd /root/ws
java -Dfile.encoding=utf-8 -jar app.jar --spring.profiles.active=$active
