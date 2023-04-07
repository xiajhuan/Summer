SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Records of security_dept
-- ----------------------------
INSERT INTO `security_dept` VALUES (1636548298722242561, 'systemUser', '2023-03-17 10:01:35', 'systemUser', '2023-03-17 10:01:35', 0, '0', '罗邦洁具股份有限公司', 0);
INSERT INTO `security_dept` VALUES (1636548627622785026, 'systemUser', '2023-03-17 10:02:53', 'systemUser', '2023-03-17 10:02:53', 1636548298722242561, '1636548298722242561', '泰国工厂', 0);
INSERT INTO `security_dept` VALUES (1636548808003022850, 'systemUser', '2023-03-17 10:03:36', 'systemUser', '2023-03-17 10:03:36', 1636548298722242561, '1636548298722242561', '椒江工厂', 1);
INSERT INTO `security_dept` VALUES (1636548954166128641, 'systemUser', '2023-03-17 10:04:11', 'systemUser', '2023-03-17 10:04:11', 1636548627622785026, '1636548627622785026,1636548298722242561', '市场部', 0);
INSERT INTO `security_dept` VALUES (1636549079382880258, 'systemUser', '2023-03-17 10:04:41', 'systemUser', '2023-03-17 10:04:41', 1636548627622785026, '1636548627622785026,1636548298722242561', '销售部', 1);
INSERT INTO `security_dept` VALUES (1636549217312567298, 'systemUser', '2023-03-17 10:05:14', 'systemUser', '2023-03-17 10:05:14', 1636548808003022850, '1636548808003022850,1636548298722242561', '产品部', 0);
INSERT INTO `security_dept` VALUES (1636549260430012417, 'systemUser', '2023-03-17 10:05:24', 'systemUser', '2023-03-17 10:05:24', 1636548808003022850, '1636548808003022850,1636548298722242561', '技术部', 1);
INSERT INTO `security_dept` VALUES (1636549294890414082, 'systemUser', '2023-03-17 10:05:32', 'systemUser', '2023-03-17 10:05:32', 1636548808003022850, '1636548808003022850,1636548298722242561', '销售部', 2);
INSERT INTO `security_dept` VALUES (1636549534884294658, 'systemUser', '2023-03-17 10:06:29', 'systemUser', '2023-03-17 10:08:10', 1636549260430012417, '1636548808003022850,1636548298722242561,1636549260430012417', '模具研发部', 0);

-- ----------------------------
-- Records of security_menu
-- ----------------------------
INSERT INTO `security_menu` VALUES (1644147910819700738, 'admin', '2023-04-07 09:19:43', 'admin', '2023-04-07 09:19:43', 0, '', '', 0, 0, 'icon-safetycertificate', 0);
INSERT INTO `security_menu` VALUES (1644152845405667330, 'admin', '2023-04-07 09:39:20', 'admin', '2023-04-07 09:39:20', 1644147910819700738, 'security/user', '', 0, 0, 'icon-user', 0);
INSERT INTO `security_menu` VALUES (1644153599092736001, 'admin', '2023-04-07 09:42:20', 'admin', '2023-04-07 09:42:20', 1644147910819700738, 'security/post', '', 0, 0, 'icon-pic-left', 1);
INSERT INTO `security_menu` VALUES (1644154672847142914, 'admin', '2023-04-07 09:46:36', 'admin', '2023-04-07 09:46:36', 1644147910819700738, 'security/dept', '', 0, 0, 'icon-apartment', 2);
INSERT INTO `security_menu` VALUES (1644155270992642049, 'admin', '2023-04-07 09:48:58', 'admin', '2023-04-07 09:48:58', 1644147910819700738, 'security/role', '', 0, 0, 'icon-team', 3);
INSERT INTO `security_menu` VALUES (1644157656800935937, 'admin', '2023-04-07 09:58:27', 'admin', '2023-04-07 09:58:27', 1644154672847142914, '', 'security:dept:list,security:dept:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644158403013754882, 'admin', '2023-04-07 10:01:25', 'admin', '2023-04-07 10:01:25', 1644154672847142914, '', 'security:dept:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644158848205570050, 'admin', '2023-04-07 10:03:11', 'admin', '2023-04-07 10:03:11', 1644154672847142914, '', 'security:dept:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644159233980874754, 'admin', '2023-04-07 10:04:43', 'admin', '2023-04-07 10:04:43', 1644154672847142914, '', 'security:dept:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1644159962489536514, 'admin', '2023-04-07 10:07:37', 'admin', '2023-04-07 10:07:37', 0, '', '', 0, 0, 'icon-setting', 0);
INSERT INTO `security_menu` VALUES (1644160692621393922, 'admin', '2023-04-07 10:10:31', 'admin', '2023-04-07 10:10:31', 1644159962489536514, 'security/menu', '', 0, 0, 'icon-unorderedlist', 0);
INSERT INTO `security_menu` VALUES (1644161676202459138, 'admin', '2023-04-07 10:14:25', 'admin', '2023-04-07 10:14:25', 1644160692621393922, '', 'security:menu:list,security:menu:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644162022752632834, 'admin', '2023-04-07 10:15:48', 'admin', '2023-04-07 10:15:48', 1644160692621393922, '', 'security:menu:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644162242701934594, 'admin', '2023-04-07 10:16:40', 'admin', '2023-04-07 10:16:40', 1644160692621393922, '', 'security:menu:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644162484654555138, 'admin', '2023-04-07 10:17:38', 'admin', '2023-04-07 10:17:38', 1644160692621393922, '', 'security:menu:delete', 1, NULL, '', 3);

-- ----------------------------
-- Records of locale_international_name
-- ----------------------------
INSERT INTO `locale_international_name` VALUES (1644147911247519745, 'admin', '2023-04-07 09:19:44', 'admin', '2023-04-07 09:19:44', 1636548298722242561, 'security_menu', 1644147910819700738, 'name', '权限管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644150551851188226, 'admin', '2023-04-07 09:30:13', 'admin', '2023-04-07 09:30:13', 1636548298722242561, 'security_menu', 1644147910819700738, 'name', 'Authority management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644152845405667331, 'admin', '2023-04-07 09:39:20', 'admin', '2023-04-07 09:39:20', 1636548298722242561, 'security_menu', 1644152845405667330, 'name', '用户管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644153014499033090, 'admin', '2023-04-07 09:40:00', 'admin', '2023-04-07 09:40:00', 1636548298722242561, 'security_menu', 1644152845405667330, 'name', 'User management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644153599092736002, 'admin', '2023-04-07 09:42:20', 'admin', '2023-04-07 09:42:20', 1636548298722242561, 'security_menu', 1644153599092736001, 'name', '岗位管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644153831411040258, 'admin', '2023-04-07 09:43:15', 'admin', '2023-04-07 09:43:15', 1636548298722242561, 'security_menu', 1644153599092736001, 'name', 'Post management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644154672847142915, 'admin', '2023-04-07 09:46:36', 'admin', '2023-04-07 09:46:36', 1636548298722242561, 'security_menu', 1644154672847142914, 'name', '部门管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644154920399159299, 'admin', '2023-04-07 09:47:35', 'admin', '2023-04-07 09:47:35', 1636548298722242561, 'security_menu', 1644154672847142914, 'name', 'Department management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644155270992642050, 'admin', '2023-04-07 09:48:58', 'admin', '2023-04-07 09:48:58', 1636548298722242561, 'security_menu', 1644155270992642049, 'name', '角色管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644155423136825345, 'admin', '2023-04-07 09:49:34', 'admin', '2023-04-07 09:49:34', 1636548298722242561, 'security_menu', 1644155270992642049, 'name', 'Role management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644157657094537217, 'admin', '2023-04-07 09:58:27', 'admin', '2023-04-07 09:58:27', 1636548298722242561, 'security_menu', 1644157656800935937, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644157964012732418, 'admin', '2023-04-07 09:59:40', 'admin', '2023-04-07 09:59:40', 1636548298722242561, 'security_menu', 1644157656800935937, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644158403013754883, 'admin', '2023-04-07 10:01:25', 'admin', '2023-04-07 10:01:25', 1636548298722242561, 'security_menu', 1644158403013754882, 'name', '新增', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644158554126139394, 'admin', '2023-04-07 10:02:01', 'admin', '2023-04-07 10:02:01', 1636548298722242561, 'security_menu', 1644158403013754882, 'name', 'Add', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644158848205570051, 'admin', '2023-04-07 10:03:11', 'admin', '2023-04-07 10:03:11', 1636548298722242561, 'security_menu', 1644158848205570050, 'name', '修改', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644159040074006531, 'admin', '2023-04-07 10:03:57', 'admin', '2023-04-07 10:03:57', 1636548298722242561, 'security_menu', 1644158848205570050, 'name', 'Update', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644159234047983618, 'admin', '2023-04-07 10:04:43', 'admin', '2023-04-07 10:04:43', 1636548298722242561, 'security_menu', 1644159233980874754, 'name', '删除', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644159270886555651, 'admin', '2023-04-07 10:04:52', 'admin', '2023-04-07 10:04:52', 1636548298722242561, 'security_menu', 1644159233980874754, 'name', 'Delete', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644159962556645378, 'admin', '2023-04-07 10:07:37', 'admin', '2023-04-07 10:07:37', 1636548298722242561, 'security_menu', 1644159962489536514, 'name', '系统设置', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644160077732233219, 'admin', '2023-04-07 10:08:04', 'admin', '2023-04-07 10:08:04', 1636548298722242561, 'security_menu', 1644159962489536514, 'name', 'System setting', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644160692621393923, 'admin', '2023-04-07 10:10:31', 'admin', '2023-04-07 10:10:31', 1636548298722242561, 'security_menu', 1644160692621393922, 'name', '菜单管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644160890819035139, 'admin', '2023-04-07 10:11:18', 'admin', '2023-04-07 10:11:18', 1636548298722242561, 'security_menu', 1644160692621393922, 'name', 'Menu management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644161676202459139, 'admin', '2023-04-07 10:14:25', 'admin', '2023-04-07 10:14:25', 1636548298722242561, 'security_menu', 1644161676202459138, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644161815008755714, 'admin', '2023-04-07 10:14:58', 'admin', '2023-04-07 10:14:58', 1636548298722242561, 'security_menu', 1644161676202459138, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644162022752632835, 'admin', '2023-04-07 10:15:48', 'admin', '2023-04-07 10:15:48', 1636548298722242561, 'security_menu', 1644162022752632834, 'name', '新增', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644162042633633795, 'admin', '2023-04-07 10:15:53', 'admin', '2023-04-07 10:15:53', 1636548298722242561, 'security_menu', 1644162022752632834, 'name', 'Add', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644162242701934595, 'admin', '2023-04-07 10:16:40', 'admin', '2023-04-07 10:16:40', 1636548298722242561, 'security_menu', 1644162242701934594, 'name', '修改', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644162299870298114, 'admin', '2023-04-07 10:16:54', 'admin', '2023-04-07 10:16:54', 1636548298722242561, 'security_menu', 1644162242701934594, 'name', 'Update', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644162484654555139, 'admin', '2023-04-07 10:17:38', 'admin', '2023-04-07 10:17:38', 1636548298722242561, 'security_menu', 1644162484654555138, 'name', '删除', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644162510969618433, 'admin', '2023-04-07 10:17:44', 'admin', '2023-04-07 10:17:44', 1636548298722242561, 'security_menu', 1644162484654555138, 'name', 'Delete', 'en_US');

SET FOREIGN_KEY_CHECKS = 1;