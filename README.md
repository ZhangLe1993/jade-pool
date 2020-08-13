# 代理管理平台
## 运行一个mysql
sudo docker run -itd -p 3306:3306 --name mysql -v /home/yinyue/mysql/conf:/etc/mysql/conf.d -v /home/yinyue/mysql/logs:/logs -v /home/yinyue/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:latest

