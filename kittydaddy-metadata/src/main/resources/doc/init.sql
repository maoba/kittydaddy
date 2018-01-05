--用户表
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名称',
  `user_pwd` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户密码',
  `tenant_id` varchar(64) DEFAULT NULL COMMENT '租户id',
  `tenant_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '租户名称',
  `cell_phone_num` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `email` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `salt` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '盐',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `avatar` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '头像url地址',
  `position` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '职位信息',
  `status` int(11) DEFAULT '0' COMMENT '-1:删除 0：失效 1：生效',
  `sex` tinyint(4) DEFAULT '0' COMMENT '0:男 1：女',
  `terminal_type` int(2) DEFAULT '0' COMMENT '登入端类型 0:pc端  1:手机端',
  `login_type` int(2) DEFAULT '0' COMMENT '0:手机号码方式登入 1:邮件方式登入',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`),
  KEY `cell_phone_num` (`cell_phone_num`),
  KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统用户登录表';

ALTER TABLE SYSTEM_USER ADD COLUMN province VARCHAR(32) DEFAULT NULL COMMENT '省';
ALTER TABLE SYSTEM_USER ADD COLUMN city VARCHAR(32) DEFAULT NULL COMMENT '市';
ALTER TABLE SYSTEM_USER ADD COLUMN area_ VARCHAR(32) DEFAULT NULL COMMENT '区';
ALTER TABLE SYSTEM_USER ADD COLUMN hobby VARCHAR(512) DEFAULT NULL COMMENT '兴趣爱好';
ALTER TABLE SYSTEM_USER ADD COLUMN birthday DATETIME DEFAULT NULL COMMENT '出生日期';
ALTER TABLE SYSTEM_USER ADD COLUMN real_name VARCHAR(32) DEFAULT NULL COMMENT '真实姓名';


--角色表
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` VARCHAR(64) NOT NULL COMMENT '主键',
  `role_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `tenant_id` VARCHAR(64) DEFAULT NULL COMMENT '租户id',
  `role_code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
  `description` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';


--用户角色关系表
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `id` VARCHAR(64) NOT NULL COMMENT '主键',
  `role_id` VARCHAR(64) DEFAULT NULL COMMENT '角色Id',
  `role_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
  `user_id` VARCHAR(64) DEFAULT NULL COMMENT '用户id',
  `tenant_id` VARCHAR(64) DEFAULT NULL COMMENT '租户id',
  `user_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名称',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色关系表';

--权限表
DROP TABLE IF EXISTS `system_permission`;
CREATE TABLE `system_permission` (
  `id` varchar(64) NOT NULL  COMMENT '主键',
  `module_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '模块名称',
  `module_code` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '模块编码',
  `permission_code` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '权限编码',
  `permission_url` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '权限地址',
  `permission_ico` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '权限图标',
  `permission_type` tinyint(4) DEFAULT NULL COMMENT '0:目录 1:资源',
  `terminal_type` tinyint(4) DEFAULT NULL COMMENT '0:pc权限 1:手机权限',
  `tenant_id` varchar(64) DEFAULT NULL COMMENT '租户id',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父节点',
  `status` tinyint(4) DEFAULT NULL COMMENT '-1:删除 0:失效 1:生效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `parent_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父目录名称',
  PRIMARY KEY (`id`),
  KEY `module_code` (`module_code`),
  KEY `tenant_id` (`tenant_id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限表';

--角色权限表
DROP TABLE IF EXISTS `system_role_permission`;
CREATE TABLE `system_role_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色Id',
  `role_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `permission_id` varchar(64) DEFAULT NULL COMMENT '权限id',
  `permission_code` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '权限编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限表';


--租户信息表
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `id` varchar(64) NOT NULL  COMMENT '主键',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '租户的名称',
  `description` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '租户的简单的介绍',
  `location` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '租户的具体位置',
  `detail_id` varchar(64) DEFAULT NULL COMMENT '相关租户的详细的信息',
  `status` int(11) DEFAULT '1' COMMENT '-1：删除 0：失效 1：生效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `detail_id` (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户表';





