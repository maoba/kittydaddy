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


--内容metadata(视频内容基本信息)
DROP TABLE IF EXISTS `k_video_content`;
CREATE TABLE `k_video_content` (
  `id` VARCHAR(64) NOT NULL  COMMENT '主键',
  `sub_origin_id` VARCHAR(32) DEFAULT NULL COMMENT '第三标识id',
  `origin_id` VARCHAR(32) COLLATE utf8_bin DEFAULT NULL COMMENT '原始Id标识',
  `source` VARCHAR(32) COLLATE utf8_bin DEFAULT NULL COMMENT '来源',
  `title` VARCHAR(64) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `subtitle` VARCHAR(64) COLLATE utf8_bin DEFAULT NULL COMMENT '二级标题',
  `rate` VARCHAR(16) COLLATE utf8_bin DEFAULT NULL COMMENT '评分',
  `tags` VARCHAR(256) COLLATE utf8_bin DEFAULT NULL COMMENT '标签',
  `channel` VARCHAR(64) COLLATE utf8_bin DEFAULT NULL COMMENT '视频类别',
  `short_flag` INT(2) DEFAULT 0 COMMENT '0:表示长视频 1:表示短视频',
  `episode_count` INT(16) DEFAULT 0 COMMENT '总剧集数',
  `directors` VARCHAR(256) DEFAULT NULL COMMENT '导演',
  `actors` VARCHAR(256) DEFAULT NULL COMMENT '演员',
  `year` VARCHAR(32) DEFAULT NULL COMMENT '年份',
  `last_sn` INT(8) DEFAULT 0 COMMENT '最近更新集数',
  `duration` INT(32) DEFAULT 0 COMMENT '时长',
  `area` VARCHAR(64) DEFAULT NULL COMMENT '地区',
  `origin_pub_time` VARCHAR(32) DEFAULT NULL COMMENT '影视发布时间',
  `language` VARCHAR(32) DEFAULT NULL COMMENT '影片语言',
  `genres` VARCHAR(32) DEFAULT NULL COMMENT '风格,体裁',
  `yester_play_count` INT(16) DEFAULT 0 COMMENT '年播放次数',
  `weekly_play_count` INT(16) DEFAULT 0 COMMENT '周播放次数',
  `total_play_count`  INT(16) DEFAULT 0 COMMENT '总播放次数',  
  `img_small_url` VARCHAR(256) DEFAULT NULL COMMENT '视频封面小图片地址',
  `img_medium_url` VARCHAR(256) DEFAULT NULL COMMENT '视频封面中等图片地址',
  `img_large_url` VARCHAR(256) DEFAULT NULL COMMENT '视频封面大图片地址',
  `status` INT(4) DEFAULT '1' COMMENT '-1：删除 0：失效 1：生效',
  `is_publish` INT(4) DEFAULT 0 COMMENT '0:未发布 1:已发布',
  `summary` VARCHAR(2048) DEFAULT NULL COMMENT '影片描述信息',
  `is_free` INT(4)  DEFAULT 0 COMMENT '1:表示免费  2:表示收费',  
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `publish_time` DATETIME DEFAULT NULL COMMENT '发布时间',
  `operate_id` VARCHAR(64) DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`id`),
  KEY `origin_id` (`origin_id`),
  KEY `title`(`title`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='视频内容信息表';

--内容剧集表
DROP TABLE IF EXISTS `k_video_content_item`;
CREATE TABLE `k_video_content_item` (
  `id` varchar(64) NOT NULL  COMMENT '主键',
  `item_channel` varchar(64) default null comment '剧集类别，例如：电视剧、综艺、动漫',
  `item_title` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '剧集名称',
  `item_sn` int(16) COLLATE utf8_bin DEFAULT 0 COMMENT '当前集数',
  `item_period` varchar(64) default null comment '阶段',
  `item_summary` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '剧集描述',
  `status` int(4) DEFAULT '1' COMMENT '-1：删除 0：失效 1：生效',
  `content_id` varchar(64) default null comment '关联内容id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='视频剧集内容信息表';


--地址表
DROP TABLE IF EXISTS `k_video_content_source`;
CREATE TABLE `k_video_content_source` (
  `id` varchar(64) NOT NULL  COMMENT '主键',
  `play_url` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '剧集名称',
  `source_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '来源id',
  `source` varchar(32) COLLATE utf8_bin DEFAULT null COMMENT '来源名称',
  `is_free` INT(4)  DEFAULT 0 COMMENT '1:表示免费  2:表示收费',  
  `relative_id` varchar(128)  default null comment '关联id',  
  `relative_type` varchar(32) default null comment '关联类型',
  `image_url` varchar(256) default null comment '剧集照片',
  `status` int(4) DEFAULT '1' COMMENT '-1:删除 0：失效 1：生效',
  `duration` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '剧集时长',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `relative_id` (`relative_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='视频内容源表';


















