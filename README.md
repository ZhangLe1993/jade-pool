# 瑶池开发者平台
## 运行一个mysql
    sudo docker run -itd -p 3306:3306 --name mysql -v /home/yinyue/mysql/conf:/etc/mysql/conf.d -v /home/yinyue/mysql/logs:/logs -v /home/yinyue/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:latest

# 运行一个容器
    docker run -itd  --privileged --name abc -p 2200:22 centos /usr/sbin/init
    docker exec -it abc  /bin/bash

# 安装password
    yum search passwd
    yum install passwd.x86_64
    passwd root

# 安装openssh
yum -y install openssh*

# 安装systemctl
    yum list | grep initscripts
    yum install initscripts.x86_64 -y
    vi /etc/ssh/sshd_config

    RSAAuthentication yes #启用 RSA 认证
    PubkeyAuthentication yes #启用公钥私钥配对认证方式
    AuthorizedKeysFile .ssh/authorized_keys #公钥文件路径（和上面生成的文件同）
    PermitRootLogin yes #root能使用ssh登录
# 重启ssh
    systemctl restart sshd
# 开机自启动
    chkconfig sshd on
# 安装network命令 ifconfig
    yum install net-tools -y
# 提交
    docker commit -m 'openssh' -a 'yule.zhang' abc centos-ssh:latest

# 配置跨主机容器访问
    docker network create --subnet=172.172.0.0/24 docker-br0
    备注：这里选取了172.172.0.0网段，也可以指定其他任意空闲的网段，docker-br0为自定义网桥的名字，可自己任意取名。
    注意：这里子网掩码要使用255.255.255.0也就是IP后面的那个24,因为后面要使用iptables配置路由表,我之前使用255.255.0.0 无法配置.所以这里配置成24.

    创建网桥之后,使用ifconfig查看 会多出一个网桥,该网桥在docker启动或者重启之后,会自动显示出来。永久的,可以使用docker network rm docker-br0 移除网桥。

# 在要访问容器的宿主机上添加路由表
    # ip route add 172.172.1.0/24 via ECS(IP) dev eno16777736(本机网卡)

# 运行7个容器作为测试
    docker run -itd --net docker-br0 --ip 172.172.0.11 --name abc -p 20001:22 centos-ssh /usr/sbin/sshd -D
    docker run -itd --net docker-br0 --ip 172.172.0.12 --name abd -p 20002:22 centos-ssh /usr/sbin/sshd -D
    docker run -itd --net docker-br0 --ip 172.172.0.13 --name abe -p 20003:22 centos-ssh /usr/sbin/sshd -D
    docker run -itd --net docker-br0 --ip 172.172.0.14 --name abf -p 20004:22 centos-ssh /usr/sbin/sshd -D
    docker run -itd --net docker-br0 --ip 172.172.0.15 --name abg -p 20005:22 centos-ssh /usr/sbin/sshd -D
    docker run -itd --net docker-br0 --ip 172.172.0.16 --name abh -p 20006:22 centos-ssh /usr/sbin/sshd -D
    docker run -itd --net docker-br0 --ip 172.172.0.17 --name abi -p 20007:22 centos-ssh /usr/sbin/sshd -D


# 部署容器
    cd /root
    mkdir code
    git clone https://github.com/ZhangLe1993/jade-pool.git




