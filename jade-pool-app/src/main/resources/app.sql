create database if not exists proxy;
use proxy;
/**
   实例表，数据仓库管理员主动创建添加。
 */
DROP TABLE IF EXISTS cpt_meta_rds;

CREATE TABLE IF NOT EXISTS cpt_meta_rds(
    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(200) NOT NULL COMMENT '别名',
    `host` VARCHAR(200) NOT NULL COMMENT '连接RDS实例的IP或者域名',
    `port` int(4) NOT NULL COMMENT '端口号',
    `username` VARCHAR(30) NOT NULL COMMENT '数据库实例的用户名',
    `password` VARCHAR(200) NOT NULL COMMENT '数据库实例的密码',
    `status` VARCHAR(10) NOT NULL COMMENT '状态： 创建: CREATED, 可用: ENABLED, 禁用: DISABLED',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_uk` VARCHAR(30) NOT NULL COMMENT '创建者工号',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_uk` VARCHAR(30) NOT NULL COMMENT '修改者工号',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '实例表';



select * from cpt_meta_rds where status = 'ENABLED';

/**
   数据库，从远程实例同步过来
 */
DROP TABLE IF EXISTS cpt_meta_schema;

CREATE TABLE IF NOT EXISTS cpt_meta_schema(
    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(200) NOT NULL COMMENT '库名',
    `parent_id` INT(11) NOT NULL COMMENT '父节点ID，所属实例的ID',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '数据库表';


/**
   表，从远程实例同步过来
 */
DROP TABLE IF EXISTS cpt_meta_table;

CREATE TABLE IF NOT EXISTS cpt_meta_table(
    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id` INT(11) NOT NULL COMMENT '父节点ID，所属schema的ID',
    `name` VARCHAR(200) NOT NULL COMMENT '表名',
    `comment` VARCHAR(2000) COMMENT '注释',
    `engine` VARCHAR(2000) COMMENT '引擎',
    `character` VARCHAR(20) COMMENT '字符集',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '表表';


/**
   字段，从远程实例同步过来
 */
DROP TABLE IF EXISTS cpt_meta_field;

CREATE TABLE IF NOT EXISTS cpt_meta_field(
    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id` INT(11) NOT NULL COMMENT '父节点ID，所属table的ID',
    `name` VARCHAR(200) NOT NULL COMMENT '字段名',
    `comment` VARCHAR(2000) COMMENT '注释',
    `type` VARCHAR(2000) COMMENT '字段类型',
    `pri` VARCHAR(20) COMMENT '主键',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '字段表';


DROP TABLE IF EXISTS `source`;
CREATE TABLE IF NOT EXISTS `source`
(
    `id`             bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`           varchar(255) NOT NULL COMMENT '别名名称',
    `description`    varchar(255) DEFAULT NULL COMMENT '描述',
    `config`         text         NOT NULL COMMENT '配置',
    `type`           varchar(10)  NOT NULL COMMENT '类型',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_uk` VARCHAR(30) NOT NULL COMMENT '创建者工号',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_uk` VARCHAR(30) NOT NULL COMMENT '修改者工号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '数据源表';

DROP TABLE IF EXISTS `ssh`;
CREATE TABLE IF NOT EXISTS `ssh`
(
    `id`             bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`           varchar(255) NOT NULL COMMENT '别名名称',
    `description`    varchar(255) DEFAULT NULL COMMENT '描述',
    `config`         text         COMMENT '配置',
    `type`           varchar(10)  NOT NULL COMMENT '类型 FOLDER:文件夹, NODE:叶子节点',
    `parent_id`      bigint(20)   NOT NULL COMMENT '父文件夹ID',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_uk` VARCHAR(30) NOT NULL COMMENT '创建者工号',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_uk` VARCHAR(30) NOT NULL COMMENT '修改者工号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '远程链接配置';

DROP TABLE IF EXISTS `agents`;
CREATE TABLE IF NOT EXISTS `agents` (
                          `id` int unsigned NOT NULL AUTO_INCREMENT,
                          `local_url` varchar(500) NOT NULL DEFAULT '' COMMENT '路径',
                          `target_host` varchar(200) NOT NULL DEFAULT '' COMMENT '目标主机',
                          `target_url` varchar(500) NOT NULL DEFAULT '' COMMENT '目标路径',
                          `header_append` text COMMENT 'header',
                          `cookie_append` text COMMENT 'cookie',
                          `url_append` text COMMENT 'url',
                          `body_append` text COMMENT 'body',
                          `status` varchar(20) NOT NULL DEFAULT 'CREATE' COMMENT '状态: CREATE, TEST, PASS, DELETE',
                          `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建人',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_url` (`local_url`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代理表';