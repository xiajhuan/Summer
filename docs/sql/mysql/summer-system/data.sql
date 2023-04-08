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
INSERT INTO `security_menu` VALUES (1644159962489536514, 'admin', '2023-04-07 10:07:37', 'admin', '2023-04-07 10:07:37', 0, '', '', 0, 0, 'icon-setting', 1);
INSERT INTO `security_menu` VALUES (1644160692621393922, 'admin', '2023-04-07 10:10:31', 'admin', '2023-04-07 10:10:31', 1644159962489536514, 'security/menu', '', 0, 0, 'icon-unorderedlist', 0);
INSERT INTO `security_menu` VALUES (1644161676202459138, 'admin', '2023-04-07 10:14:25', 'admin', '2023-04-07 10:14:25', 1644160692621393922, '', 'security:menu:list,security:menu:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644162022752632834, 'admin', '2023-04-07 10:15:48', 'admin', '2023-04-07 10:15:48', 1644160692621393922, '', 'security:menu:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644162242701934594, 'admin', '2023-04-07 10:16:40', 'admin', '2023-04-07 10:16:40', 1644160692621393922, '', 'security:menu:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644162484654555138, 'admin', '2023-04-07 10:17:38', 'admin', '2023-04-07 11:13:24', 1644160692621393922, '', 'security:menu:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1644247399261769730, 'admin', '2023-04-07 15:55:03', 'admin', '2023-04-07 15:55:03', 1644155270992642049, '', 'security:role:page,security:role:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644247996216086530, 'admin', '2023-04-07 15:57:26', 'admin', '2023-04-07 15:57:26', 1644155270992642049, '', 'security:role:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644248300751917057, 'admin', '2023-04-07 15:58:38', 'admin', '2023-04-07 15:58:38', 1644155270992642049, '', 'security:role:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644248585805205505, 'admin', '2023-04-07 15:59:46', 'admin', '2023-04-07 15:59:46', 1644155270992642049, '', 'security:role:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1644249552424505346, 'admin', '2023-04-07 16:03:37', 'admin', '2023-04-07 16:03:37', 0, '', '', 0, 0, 'icon-container', 2);
INSERT INTO `security_menu` VALUES (1644250886280286210, 'admin', '2023-04-07 16:08:55', 'admin', '2023-04-07 16:08:55', 1644249552424505346, 'log/operation', '', 0, 0, 'icon-solution', 0);
INSERT INTO `security_menu` VALUES (1644251503899938817, 'admin', '2023-04-07 16:11:22', 'admin', '2023-04-07 16:11:22', 1644249552424505346, 'log/error', '', 0, 0, 'icon-file-exception', 1);
INSERT INTO `security_menu` VALUES (1644251921644228609, 'admin', '2023-04-07 16:13:02', 'admin', '2023-04-07 16:13:02', 1644249552424505346, 'log/login', '', 0, 0, 'icon-filedone', 2);
INSERT INTO `security_menu` VALUES (1644252869271719938, 'admin', '2023-04-07 16:16:47', 'admin', '2023-04-07 16:16:47', 1644250886280286210, '', 'log:operation:page', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644253286663688193, 'admin', '2023-04-07 16:18:27', 'admin', '2023-04-07 16:18:27', 1644250886280286210, '', 'log:operation:excelExport', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644253821047377922, 'admin', '2023-04-07 16:20:34', 'admin', '2023-04-07 16:20:34', 1644251503899938817, '', 'log:error:page,log:error:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644254129991421954, 'admin', '2023-04-07 16:21:48', 'admin', '2023-04-07 16:21:48', 1644251503899938817, '', 'log:error:excelExport', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644254438981603329, 'admin', '2023-04-07 16:23:02', 'admin', '2023-04-07 16:23:02', 1644251921644228609, '', 'log:login:page', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644254772470714369, 'admin', '2023-04-07 16:24:21', 'admin', '2023-04-07 16:24:21', 1644251921644228609, '', 'log:login:excelExport', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644256030950658049, 'admin', '2023-04-07 16:29:21', 'admin', '2023-04-07 16:29:21', 1644159962489536514, 'locale/internationalName', '', 0, 0, 'icon-file-word', 1);
INSERT INTO `security_menu` VALUES (1644256659760713730, 'admin', '2023-04-07 16:31:51', 'admin', '2023-04-07 16:31:51', 1644256030950658049, '', 'locale:internationalName:page,locale:internationalName:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644256943945781250, 'admin', '2023-04-07 16:32:59', 'admin', '2023-04-07 16:32:59', 1644256030950658049, '', 'locale:internationalName:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644257173638451202, 'admin', '2023-04-07 16:33:54', 'admin', '2023-04-07 16:33:54', 1644256030950658049, '', 'locale:internationalName:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644257432523476994, 'admin', '2023-04-07 16:34:55', 'admin', '2023-04-07 16:34:55', 1644256030950658049, '', 'locale:internationalName:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1644257749889683457, 'admin', '2023-04-07 16:36:11', 'admin', '2023-04-07 16:36:11', 1644256030950658049, '', 'locale:internationalName:excelTemplate', 1, NULL, '', 4);
INSERT INTO `security_menu` VALUES (1644258035748278274, 'admin', '2023-04-07 16:37:19', 'admin', '2023-04-07 16:37:19', 1644256030950658049, '', 'locale:internationalName:excelImport', 1, NULL, '', 5);
INSERT INTO `security_menu` VALUES (1644258340347023362, 'admin', '2023-04-07 16:38:32', 'admin', '2023-04-07 16:38:32', 1644256030950658049, '', 'locale:internationalName:excelExport', 1, NULL, '', 6);

-- ----------------------------
-- Records of security_role
-- ----------------------------
INSERT INTO `security_role` VALUES (1644288389922058242, 'admin', '2023-04-07 18:37:56', 'admin', '2023-04-07 18:37:56', '超级管理员', '拥有所有权限');

-- ----------------------------
-- Records of security_role_dept
-- ----------------------------
INSERT INTO `security_role_dept` VALUES (1644288400038719489, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636548298722242561);
INSERT INTO `security_role_dept` VALUES (1644288400302960641, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636548627622785026);
INSERT INTO `security_role_dept` VALUES (1644288400495898625, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636548808003022850);
INSERT INTO `security_role_dept` VALUES (1644288400751751170, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636548954166128641);
INSERT INTO `security_role_dept` VALUES (1644288400944689154, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636549079382880258);
INSERT INTO `security_role_dept` VALUES (1644288401213124609, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636549217312567298);
INSERT INTO `security_role_dept` VALUES (1644288401406062594, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636549260430012417);
INSERT INTO `security_role_dept` VALUES (1644288401666109442, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636549294890414082);
INSERT INTO `security_role_dept` VALUES (1644288401926156290, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1636549534884294658);

-- ----------------------------
-- Records of security_role_menu
-- ----------------------------
INSERT INTO `security_role_menu` VALUES (1644288390379237377, 'admin', '2023-04-07 18:37:56', 1644288389922058242, 1644147910819700738);
INSERT INTO `security_role_menu` VALUES (1644288390639284226, 'admin', '2023-04-07 18:37:56', 1644288389922058242, 1644152845405667330);
INSERT INTO `security_role_menu` VALUES (1644288390832222210, 'admin', '2023-04-07 18:37:56', 1644288389922058242, 1644153599092736001);
INSERT INTO `security_role_menu` VALUES (1644288391297789954, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644154672847142914);
INSERT INTO `security_role_menu` VALUES (1644288391557836802, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644155270992642049);
INSERT INTO `security_role_menu` VALUES (1644288391880798209, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644157656800935937);
INSERT INTO `security_role_menu` VALUES (1644288392140845058, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644158403013754882);
INSERT INTO `security_role_menu` VALUES (1644288392400891906, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644158848205570050);
INSERT INTO `security_role_menu` VALUES (1644288392660938754, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644159233980874754);
INSERT INTO `security_role_menu` VALUES (1644288392920985601, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644159962489536514);
INSERT INTO `security_role_menu` VALUES (1644288393118117889, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644160692621393922);
INSERT INTO `security_role_menu` VALUES (1644288393386553345, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644161676202459138);
INSERT INTO `security_role_menu` VALUES (1644288393646600194, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644162022752632834);
INSERT INTO `security_role_menu` VALUES (1644288393910841346, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644162242701934594);
INSERT INTO `security_role_menu` VALUES (1644288394170888193, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644162484654555138);
INSERT INTO `security_role_menu` VALUES (1644288394368020481, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644247399261769730);
INSERT INTO `security_role_menu` VALUES (1644288394560958465, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644247996216086530);
INSERT INTO `security_role_menu` VALUES (1644288395013943298, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644248300751917057);
INSERT INTO `security_role_menu` VALUES (1644288395211075585, 'admin', '2023-04-07 18:37:57', 1644288389922058242, 1644248585805205505);
INSERT INTO `security_role_menu` VALUES (1644288395408207874, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644249552424505346);
INSERT INTO `security_role_menu` VALUES (1644288395672449026, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644250886280286210);
INSERT INTO `security_role_menu` VALUES (1644288395865387010, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644251503899938817);
INSERT INTO `security_role_menu` VALUES (1644288396146405378, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644251921644228609);
INSERT INTO `security_role_menu` VALUES (1644288396385480706, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644252869271719938);
INSERT INTO `security_role_menu` VALUES (1644288396578418689, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644253286663688193);
INSERT INTO `security_role_menu` VALUES (1644288396838465538, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644253821047377922);
INSERT INTO `security_role_menu` VALUES (1644288397161426946, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644254129991421954);
INSERT INTO `security_role_menu` VALUES (1644288397484388353, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644254438981603329);
INSERT INTO `security_role_menu` VALUES (1644288397677326337, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644254772470714369);
INSERT INTO `security_role_menu` VALUES (1644288397937373185, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644256030950658049);
INSERT INTO `security_role_menu` VALUES (1644288398134505474, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644256659760713730);
INSERT INTO `security_role_menu` VALUES (1644288398327443457, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644256943945781250);
INSERT INTO `security_role_menu` VALUES (1644288398583296002, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644257173638451202);
INSERT INTO `security_role_menu` VALUES (1644288398784622594, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644257432523476994);
INSERT INTO `security_role_menu` VALUES (1644288399053058050, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644257749889683457);
INSERT INTO `security_role_menu` VALUES (1644288399245996033, 'admin', '2023-04-07 18:37:58', 1644288389922058242, 1644258035748278274);
INSERT INTO `security_role_menu` VALUES (1644288399573151745, 'admin', '2023-04-07 18:37:59', 1644288389922058242, 1644258340347023362);

-- ----------------------------
-- Records of locale_international_name
-- ----------------------------
INSERT INTO `locale_international_name` VALUES (1644147911247519745, 'admin', '2023-04-07 09:19:44', 'admin', '2023-04-07 09:19:44', 'security_menu', 1644147910819700738, 'name', '权限管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644150551851188226, 'admin', '2023-04-07 09:30:13', 'admin', '2023-04-07 09:30:13', 'security_menu', 1644147910819700738, 'name', 'Authority management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644152845405667331, 'admin', '2023-04-07 09:39:20', 'admin', '2023-04-07 09:39:20', 'security_menu', 1644152845405667330, 'name', '用户管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644153014499033090, 'admin', '2023-04-07 09:40:00', 'admin', '2023-04-07 09:40:00', 'security_menu', 1644152845405667330, 'name', 'User management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644153599092736002, 'admin', '2023-04-07 09:42:20', 'admin', '2023-04-07 09:42:20', 'security_menu', 1644153599092736001, 'name', '岗位管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644153831411040258, 'admin', '2023-04-07 09:43:15', 'admin', '2023-04-07 09:43:15', 'security_menu', 1644153599092736001, 'name', 'Post management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644154672847142915, 'admin', '2023-04-07 09:46:36', 'admin', '2023-04-07 09:46:36', 'security_menu', 1644154672847142914, 'name', '部门管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644154920399159299, 'admin', '2023-04-07 09:47:35', 'admin', '2023-04-07 09:47:35', 'security_menu', 1644154672847142914, 'name', 'Department management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644155270992642050, 'admin', '2023-04-07 09:48:58', 'admin', '2023-04-07 09:48:58', 'security_menu', 1644155270992642049, 'name', '角色管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644155423136825345, 'admin', '2023-04-07 09:49:34', 'admin', '2023-04-07 09:49:34', 'security_menu', 1644155270992642049, 'name', 'Role management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644157657094537217, 'admin', '2023-04-07 09:58:27', 'admin', '2023-04-07 09:58:27', 'security_menu', 1644157656800935937, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644157964012732418, 'admin', '2023-04-07 09:59:40', 'admin', '2023-04-07 09:59:40', 'security_menu', 1644157656800935937, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644158403013754883, 'admin', '2023-04-07 10:01:25', 'admin', '2023-04-07 10:01:25', 'security_menu', 1644158403013754882, 'name', '新增', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644158554126139394, 'admin', '2023-04-07 10:02:01', 'admin', '2023-04-07 10:02:01', 'security_menu', 1644158403013754882, 'name', 'Add', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644158848205570051, 'admin', '2023-04-07 10:03:11', 'admin', '2023-04-07 10:03:11', 'security_menu', 1644158848205570050, 'name', '修改', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644159040074006531, 'admin', '2023-04-07 10:03:57', 'admin', '2023-04-07 10:03:57', 'security_menu', 1644158848205570050, 'name', 'Update', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644159234047983618, 'admin', '2023-04-07 10:04:43', 'admin', '2023-04-07 10:04:43', 'security_menu', 1644159233980874754, 'name', '删除', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644159270886555651, 'admin', '2023-04-07 10:04:52', 'admin', '2023-04-07 10:04:52', 'security_menu', 1644159233980874754, 'name', 'Delete', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644159962556645378, 'admin', '2023-04-07 10:07:37', 'admin', '2023-04-07 10:07:37', 'security_menu', 1644159962489536514, 'name', '系统设置', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644160077732233219, 'admin', '2023-04-07 10:08:04', 'admin', '2023-04-07 10:08:04', 'security_menu', 1644159962489536514, 'name', 'System setting', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644160692621393923, 'admin', '2023-04-07 10:10:31', 'admin', '2023-04-07 10:10:31', 'security_menu', 1644160692621393922, 'name', '菜单管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644160890819035139, 'admin', '2023-04-07 10:11:18', 'admin', '2023-04-07 10:11:18', 'security_menu', 1644160692621393922, 'name', 'Menu management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644161676202459139, 'admin', '2023-04-07 10:14:25', 'admin', '2023-04-07 10:14:25', 'security_menu', 1644161676202459138, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644161815008755714, 'admin', '2023-04-07 10:14:58', 'admin', '2023-04-07 10:14:58', 'security_menu', 1644161676202459138, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644162022752632835, 'admin', '2023-04-07 10:15:48', 'admin', '2023-04-07 10:15:48', 'security_menu', 1644162022752632834, 'name', '新增', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644162042633633795, 'admin', '2023-04-07 10:15:53', 'admin', '2023-04-07 10:15:53', 'security_menu', 1644162022752632834, 'name', 'Add', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644162242701934595, 'admin', '2023-04-07 10:16:40', 'admin', '2023-04-07 10:16:40', 'security_menu', 1644162242701934594, 'name', '修改', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644162299870298114, 'admin', '2023-04-07 10:16:54', 'admin', '2023-04-07 10:16:54', 'security_menu', 1644162242701934594, 'name', 'Update', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644162484654555139, 'admin', '2023-04-07 10:17:38', 'admin', '2023-04-07 11:13:24', 'security_menu', 1644162484654555138, 'name', '删除', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644162510969618433, 'admin', '2023-04-07 10:17:44', 'admin', '2023-04-07 10:17:44', 'security_menu', 1644162484654555138, 'name', 'Delete', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644247399328878594, 'admin', '2023-04-07 15:55:03', 'admin', '2023-04-07 15:55:03', 'security_menu', 1644247399261769730, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644247677201518593, 'admin', '2023-04-07 15:56:10', 'admin', '2023-04-07 15:56:10', 'security_menu', 1644247399261769730, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644247996283195393, 'admin', '2023-04-07 15:57:26', 'admin', '2023-04-07 15:57:26', 'security_menu', 1644247996216086530, 'name', '新增', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644248148909723651, 'admin', '2023-04-07 15:58:02', 'admin', '2023-04-07 15:58:02', 'security_menu', 1644247996216086530, 'name', 'Add', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644248300751917058, 'admin', '2023-04-07 15:58:38', 'admin', '2023-04-07 15:58:38', 'security_menu', 1644248300751917057, 'name', '修改', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644248368116633601, 'admin', '2023-04-07 15:58:54', 'admin', '2023-04-07 15:58:54', 'security_menu', 1644248300751917057, 'name', 'Update', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644248585805205506, 'admin', '2023-04-07 15:59:46', 'admin', '2023-04-07 15:59:46', 'security_menu', 1644248585805205505, 'name', '删除', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644248660694503425, 'admin', '2023-04-07 16:00:04', 'admin', '2023-04-07 16:00:04', 'security_menu', 1644248585805205505, 'name', 'Delete', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644249552424505347, 'admin', '2023-04-07 16:03:37', 'admin', '2023-04-07 16:03:37', 'security_menu', 1644249552424505346, 'name', '日志管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644249910454489089, 'admin', '2023-04-07 16:05:02', 'admin', '2023-04-07 16:05:02', 'security_menu', 1644249552424505346, 'name', 'Log management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644250886280286211, 'admin', '2023-04-07 16:08:55', 'admin', '2023-04-07 16:08:55', 'security_menu', 1644250886280286210, 'name', '操作日志', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644251048700514306, 'admin', '2023-04-07 16:09:33', 'admin', '2023-04-07 16:09:33', 'security_menu', 1644250886280286210, 'name', 'Operation log', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644251503899938818, 'admin', '2023-04-07 16:11:22', 'admin', '2023-04-07 16:11:22', 'security_menu', 1644251503899938817, 'name', '错误日志', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644251628453990402, 'admin', '2023-04-07 16:11:52', 'admin', '2023-04-07 16:11:52', 'security_menu', 1644251503899938817, 'name', 'Error log', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644251921711337474, 'admin', '2023-04-07 16:13:02', 'admin', '2023-04-07 16:13:02', 'security_menu', 1644251921644228609, 'name', '登录日志', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644252262326571010, 'admin', '2023-04-07 16:14:23', 'admin', '2023-04-07 16:14:23', 'security_menu', 1644251921644228609, 'name', 'Login log', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644252869338828802, 'admin', '2023-04-07 16:16:47', 'admin', '2023-04-07 16:16:47', 'security_menu', 1644252869271719938, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644252983587475459, 'admin', '2023-04-07 16:17:15', 'admin', '2023-04-07 16:17:15', 'security_menu', 1644252869271719938, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644253286663688194, 'admin', '2023-04-07 16:18:27', 'admin', '2023-04-07 16:18:27', 'security_menu', 1644253286663688193, 'name', '导出', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644253383296258049, 'admin', '2023-04-07 16:18:50', 'admin', '2023-04-07 16:18:50', 'security_menu', 1644253286663688193, 'name', 'Export', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644253821110292481, 'admin', '2023-04-07 16:20:34', 'admin', '2023-04-07 16:20:34', 'security_menu', 1644253821047377922, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644253898168045569, 'admin', '2023-04-07 16:20:53', 'admin', '2023-04-07 16:20:53', 'security_menu', 1644253821047377922, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644254130050142210, 'admin', '2023-04-07 16:21:48', 'admin', '2023-04-07 16:21:48', 'security_menu', 1644254129991421954, 'name', '导出', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644254218940026883, 'admin', '2023-04-07 16:22:09', 'admin', '2023-04-07 16:22:09', 'security_menu', 1644254129991421954, 'name', 'Export', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644254438981603330, 'admin', '2023-04-07 16:23:02', 'admin', '2023-04-07 16:23:02', 'security_menu', 1644254438981603329, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644254514835591171, 'admin', '2023-04-07 16:23:20', 'admin', '2023-04-07 16:23:20', 'security_menu', 1644254438981603329, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644254772533628930, 'admin', '2023-04-07 16:24:21', 'admin', '2023-04-07 16:24:21', 'security_menu', 1644254772470714369, 'name', '导出', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644254838614888450, 'admin', '2023-04-07 16:24:37', 'admin', '2023-04-07 16:24:37', 'security_menu', 1644254772470714369, 'name', 'Export', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644256030950658050, 'admin', '2023-04-07 16:29:21', 'admin', '2023-04-07 16:29:21', 'security_menu', 1644256030950658049, 'name', '国际化名称', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644256271422689283, 'admin', '2023-04-07 16:30:19', 'admin', '2023-04-07 16:30:19', 'security_menu', 1644256030950658049, 'name', 'Internationalized name', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644256659760713731, 'admin', '2023-04-07 16:31:51', 'admin', '2023-04-07 16:31:51', 'security_menu', 1644256659760713730, 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644256729704927234, 'admin', '2023-04-07 16:32:08', 'admin', '2023-04-07 16:32:08', 'security_menu', 1644256659760713730, 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644256943945781251, 'admin', '2023-04-07 16:32:59', 'admin', '2023-04-07 16:32:59', 'security_menu', 1644256943945781250, 'name', '新增', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644257008533868547, 'admin', '2023-04-07 16:33:14', 'admin', '2023-04-07 16:33:14', 'security_menu', 1644256943945781250, 'name', 'Add', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644257173705560066, 'admin', '2023-04-07 16:33:54', 'admin', '2023-04-07 16:33:54', 'security_menu', 1644257173638451202, 'name', '修改', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644257269612515330, 'admin', '2023-04-07 16:34:17', 'admin', '2023-04-07 16:34:17', 'security_menu', 1644257173638451202, 'name', 'Update', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644257432590585858, 'admin', '2023-04-07 16:34:55', 'admin', '2023-04-07 16:34:55', 'security_menu', 1644257432523476994, 'name', '删除', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644257501062598657, 'admin', '2023-04-07 16:35:12', 'admin', '2023-04-07 16:35:12', 'security_menu', 1644257432523476994, 'name', 'Delete', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644257749889683458, 'admin', '2023-04-07 16:36:11', 'admin', '2023-04-07 16:36:11', 'security_menu', 1644257749889683457, 'name', '模板下载', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644257842877403138, 'admin', '2023-04-07 16:36:33', 'admin', '2023-04-07 16:36:33', 'security_menu', 1644257749889683457, 'name', 'Template download', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644258035815387137, 'admin', '2023-04-07 16:37:19', 'admin', '2023-04-07 16:37:19', 'security_menu', 1644258035748278274, 'name', '导入', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644258112684396546, 'admin', '2023-04-07 16:37:38', 'admin', '2023-04-07 16:37:38', 'security_menu', 1644258035748278274, 'name', 'Import', 'en_US');
INSERT INTO `locale_international_name` VALUES (1644258340347023363, 'admin', '2023-04-07 16:38:32', 'admin', '2023-04-07 16:38:32', 'security_menu', 1644258340347023362, 'name', '导出', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1644258435306065922, 'admin', '2023-04-07 16:38:54', 'admin', '2023-04-07 16:38:54', 'security_menu', 1644258340347023362, 'name', 'Export', 'en_US');

SET FOREIGN_KEY_CHECKS = 1;