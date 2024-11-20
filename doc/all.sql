DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` BIGINT NOT NULL COMMENT 'id',
  `firstname` VARCHAR(50) COMMENT '名字',
    `lastname` VARCHAR(50) COMMENT '姓氏',
  `email` VARCHAR(50) COMMENT '邮箱',
    `password` VARCHAR(50) COMMENT '密码',
  `appUserRole` VARCHAR(50) COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';