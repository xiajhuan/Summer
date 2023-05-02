SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for security_role
-- ----------------------------
DROP TABLE IF EXISTS `security_role`;
CREATE TABLE `security_role`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '角色名称',
    `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for security_role_user
-- ----------------------------
DROP TABLE IF EXISTS `security_role_user`;
CREATE TABLE `security_role_user`
(
    `id`          bigint(20)                                                   NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `role_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '角色ID',
    `user_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '用户ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_role_id` (`role_id`) USING BTREE,
    INDEX `idx_user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色用户关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for security_user
-- ----------------------------
DROP TABLE IF EXISTS `security_user`;
CREATE TABLE `security_user`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `dept_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '本部门ID',
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '用户名',
    `password`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `real_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '真实姓名',
    `gender`      tinyint(1)                                                    NULL DEFAULT NULL COMMENT '性别 0：男 1：女 2：保密',
    `head_url`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像URL',
    `email`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `mobile`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '手机号',
    `status`      tinyint(1)                                                    NULL DEFAULT NULL COMMENT '状态 0：停用 1：正常',
    `user_type`   tinyint(1)                                                    NULL DEFAULT NULL COMMENT '用户类型 0：超级管理员 1：普通用户',
    `data_scope`  tinyint(1)                                                    NULL DEFAULT NULL COMMENT '数据权限 0：全部 1：基于角色 2：本部门 3：本部门及以下 4：仅本人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_username` (`username`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for security_user_post
-- ----------------------------
DROP TABLE IF EXISTS `security_user_post`;
CREATE TABLE `security_user_post`
(
    `id`          bigint(20)                                                   NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `user_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '用户ID',
    `post_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '岗位ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_user_id` (`user_id`) USING BTREE,
    INDEX `idx_post_id` (`post_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户岗位关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for security_post
-- ----------------------------
DROP TABLE IF EXISTS `security_post`;
CREATE TABLE `security_post`
(
    `id`          bigint(20)                                                   NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '更新时间',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位编码',
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
    `status`      tinyint(1)                                                   NULL DEFAULT NULL COMMENT '状态 0：停用 1：正常',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '岗位'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for security_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `security_role_menu`;
CREATE TABLE `security_role_menu`
(
    `id`          bigint(20)                                                   NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `role_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '角色ID',
    `menu_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_role_id` (`role_id`) USING BTREE,
    INDEX `idx_menu_id` (`menu_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for security_menu
-- ----------------------------
DROP TABLE IF EXISTS `security_menu`;
CREATE TABLE `security_menu`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `parent_id`   bigint(20)                                                    NULL DEFAULT NULL COMMENT '上级菜单ID',
    `url`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
    `permissions` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权',
    `type`        tinyint(1)                                                    NULL DEFAULT NULL COMMENT '类型 0：菜单 1：按钮',
    `open_mode`   tinyint(1)                                                    NULL DEFAULT NULL COMMENT '打开方式 0：内部 1：外部',
    `icon`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '菜单图标',
    `weight`      int(11)                                                       NULL DEFAULT NULL COMMENT '顺序，越小优先级越高',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_parent_id` (`parent_id`) USING BTREE,
    INDEX `idx_weight` (`weight`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '菜单'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for security_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `security_role_dept`;
CREATE TABLE `security_role_dept`
(
    `id`          bigint(20)                                                   NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `role_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '角色ID',
    `dept_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '部门ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_role_id` (`role_id`) USING BTREE,
    INDEX `idx_dept_id` (`dept_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色部门关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for security_dept
-- ----------------------------
DROP TABLE IF EXISTS `security_dept`;
CREATE TABLE `security_dept`
(
    `id`            bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新者',
    `update_time`   datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `parent_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '上级部门ID',
    `parent_id_all` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所有上级部门ID，用“,”分隔',
    `name`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '部门名称',
    `weight`        int(11)                                                       NULL DEFAULT NULL COMMENT '顺序，越小优先级越高',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_parent_id` (`parent_id`) USING BTREE,
    INDEX `idx_weight` (`weight`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '部门'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for locale_name
-- ----------------------------
DROP TABLE IF EXISTS `locale_name`;
CREATE TABLE `locale_name`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `table_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '表名',
    `line_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '行ID',
    `field_name`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '字段名',
    `field_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段值',
    `locale`      varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '地区语言',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_line_id` (`line_id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '国际化名称'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_operation
-- ----------------------------
DROP TABLE IF EXISTS `log_operation`;
CREATE TABLE `log_operation`
(
    `id`              bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'systemUser' COMMENT '创建者',
    `create_time`     datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `operation`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '用户操作',
    `request_uri`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
    `request_method`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '请求方式',
    `request_params`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '请求参数',
    `request_time`    int(11)                                                       NULL DEFAULT NULL COMMENT '请求时长（ms）',
    `user_agent`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
    `ip`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '操作IP',
    `status`          tinyint(1)                                                    NULL DEFAULT NULL COMMENT '状态  0：失败 1：成功',
    `operate_by`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '操作人',
    `operation_group` tinyint(1)                                                    NULL DEFAULT NULL COMMENT '操作分组 0：Common Crud 1：Excel Opeation 2：Other Operation',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '操作日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_error
-- ----------------------------
DROP TABLE IF EXISTS `log_error`;
CREATE TABLE `log_error`
(
    `id`             bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time`    datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `request_uri`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
    `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '请求方式',
    `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '请求参数',
    `user_agent`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
    `ip`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '操作IP',
    `error_info`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '异常堆栈信息',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '错误日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT 'systemUser' COMMENT '创建者',
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `login_user`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '登录用户名',
    `operation`   tinyint(1)                                                    NULL DEFAULT NULL COMMENT '用户操作 0：用户登录 1：用户退出',
    `status`      tinyint(1)                                                    NULL DEFAULT NULL COMMENT '登录状态 0：失败1：成功 2：账号已停用',
    `user_agent`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
    `ip`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '操作IP',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '登录日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_task
-- ----------------------------
DROP TABLE IF EXISTS `log_task`;
CREATE TABLE `log_task`
(
    `id`          bigint(20)                                                     NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT 'quartzTask' COMMENT '创建者',
    `create_time` datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `task_id`     bigint(20)                                                     NULL DEFAULT NULL COMMENT '任务ID',
    `bean_name`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '0' COMMENT 'Bean名称',
    `json`        varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '参数（Json格式）',
    `task_time`   int(11)                                                        NULL DEFAULT NULL COMMENT '任务时长（ms）',
    `type`        tinyint(1)                                                     NULL DEFAULT NULL COMMENT '类型 0：System 1：Api 2：Business',
    `status`      tinyint(1)                                                     NULL DEFAULT NULL COMMENT '状态  0：失败 1：成功',
    `error_info`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '异常堆栈信息',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_task_id` (`task_id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '定时任务日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for monitor_online
-- ----------------------------
DROP TABLE IF EXISTS `monitor_online`;
CREATE TABLE `monitor_online`
(
    `id`          bigint(20)                                                   NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者（用户名）',
    `create_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `user_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '用户ID',
    `real_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
    `dept_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '本部门名称',
    `login_time`  datetime(0)                                                  NULL DEFAULT NULL COMMENT '登录时间',
    `expire_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT 'Token过期时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE,
    INDEX `uk_user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '在线用户'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule_task
-- ----------------------------
DROP TABLE IF EXISTS `schedule_task`;
CREATE TABLE `schedule_task`
(
    `id`              bigint(20)                                                     NOT NULL COMMENT '主键ID',
    `create_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '创建者',
    `create_time`     datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '更新者',
    `update_time`     datetime(0)                                                    NULL DEFAULT NULL COMMENT '更新时间',
    `bean_name`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT 'Bean名称',
    `json`            varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数（Json格式）',
    `cron_expression` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT 'Cron表达式',
    `type`            tinyint(1)                                                     NULL DEFAULT NULL COMMENT '类型 0：System 1：Api 2：Business',
    `status`          tinyint(1)                                                     NULL DEFAULT NULL COMMENT '状态 0：停用 1：正常',
    `description`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '定时任务'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dictionary_category
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_category`;
CREATE TABLE `dictionary_category`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `dept_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '部门ID',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '0' COMMENT '类别编码',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT '0' COMMENT '类别名称',
    `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '字典类别'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dictionary_item
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_item`;
CREATE TABLE `dictionary_item`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '更新时间',
    `dept_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '部门ID',
    `label`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '项标签',
    `value`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '项值',
    `weight`      int(11)                                                       NULL DEFAULT NULL COMMENT '顺序，越小优先级越高',
    `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `category_id` bigint(20)                                                    NULL DEFAULT NULL COMMENT '类别ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE,
    INDEX `idx_category_id` (`category_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '字典项'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for extend_region
-- ----------------------------
DROP TABLE IF EXISTS `extend_region`;
CREATE TABLE `extend_region`
(
    `id`          bigint(20)                                                   NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '更新时间',
    `dept_id`     bigint(20)                                                   NULL DEFAULT NULL COMMENT '部门ID',
    `parent_id`   bigint(20)                                                   NULL DEFAULT 0 COMMENT '上级区域ID',
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '区域名称',
    `weight`      int(11)                                                      NULL DEFAULT NULL COMMENT '顺序，越小优先级越高',
    `level`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域级别 0：省直辖市 1：地级市 2：区县',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_parent_id` (`parent_id`) USING BTREE,
    INDEX `idx_weight` (`weight`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '行政区域'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for extend_param
-- ----------------------------
DROP TABLE IF EXISTS `extend_param`;
CREATE TABLE `extend_param`
(
    `id`          bigint(20)                                                     NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                    NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime(0)                                                    NULL DEFAULT NULL COMMENT '更新时间',
    `key`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT '0' COMMENT '参数键',
    `value`       varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值（Json格式）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '配置参数'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for extend_oss
-- ----------------------------
DROP TABLE IF EXISTS `extend_oss`;
CREATE TABLE `extend_oss`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT '主键ID',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)                                                   NULL DEFAULT NULL COMMENT '创建时间',
    `dept_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '部门ID',
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '文件名称',
    `url`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT 'URL',
    `path`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径（相对路径）',
    `type`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '类型 0：本地 1：MinIo 2：七牛云',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '对象存储'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
