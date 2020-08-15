/*
select * from source;


insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('大数据中心', '大数组中心的机器集群', '', 'FOLDER', -1, '105281', '105281');
insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('生产环境', '生产集群', '', 'FOLDER', 1, '105281', '105281');
insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('测试环境', '测试集群', '', 'FOLDER', 1, '105281', '105281');

insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('唐僧', '唐僧跳板机', '{\"host\": \"172.172.0.11\", \"port\":\"20001\", \"password\": \"root\"}', 'NODE', 2, '105281', '105281');
insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('悟空', '悟空跳板机', '{\"host\": \"172.172.0.12\", \"port\":\"20002\", \"password\": \"root\"}', 'NODE', 2, '105281', '105281');
insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('八戒', '八戒跳板机', '{\"host\": \"172.172.0.13\", \"port\":\"20003\", \"password\": \"root\"}', 'NODE', 2, '105281', '105281');

insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('华东测试', '华东测试', '{\"host\": \"172.172.0.14\", \"port\":\"20004\", \"password\": \"root\"}', 'NODE', 3, '105281', '105281');
insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('公有云测试', '公有云测试', '{\"host\": \"172.172.0.15\", \"port\":\"20005\", \"password\": \"root\"}', 'NODE', 3, '105281', '105281');

insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('拍机堂', '拍机堂集群', '', 'FOLDER', -1, '105281', '105281');
insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('爱机汇', '爱机汇跳板机', '{\"host\": \"172.172.0.16\", \"port\":\"20006\", \"password\": \"root\"}', 'NODE', 9, '105281', '105281');

insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('爱回收', '爱回收集群', '', 'FOLDER', -1, '105281', '105281');
insert into ssh(`name`, `description`, `config`, `type`, `parent_id`, `create_uk`, `update_uk`) values('以旧换新', '以旧换新跳板机', '{\"host\": \"172.172.0.17\", \"port\":\"20007\", \"password\": \"root\"}', 'NODE', 11, '105281', '105281');

*/


select * from ssh ;

insert into proxy.source(name,description,config,type,create_time,create_uk,update_time,update_uk) values('本机','本机','{\"ext\":false,\"password\":\"root\",\"url\":\"jdbc:mysql://127.0.0.1:3306\",\"username\":\"root\"}','jdbc', now(), '105281',now(), '105281');

select * from source ;
