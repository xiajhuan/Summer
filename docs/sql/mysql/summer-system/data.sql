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
-- Records of locale_international_name
-- ----------------------------
INSERT INTO `locale_international_name` VALUES (1637653692089552898, 'systemUser', '2023-03-20 11:14:01', 'systemUser', '2023-03-20 11:14:01', NULL, 'security_menu', 'name', '部门管理', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1637653834494562306, 'systemUser', '2023-03-20 11:14:35', 'systemUser', '2023-03-20 11:14:35', NULL, 'security_menu', 'name', 'Department Management', 'en_US');
INSERT INTO `locale_international_name` VALUES (1637654261814448130, 'systemUser', '2023-03-20 11:16:17', 'systemUser', '2023-03-20 11:16:17', NULL, 'security_menu', 'name', '查看', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1637654312922042370, 'systemUser', '2023-03-20 11:16:29', 'systemUser', '2023-03-20 11:16:29', NULL, 'security_menu', 'name', 'View', 'en_US');
INSERT INTO `locale_international_name` VALUES (1637654466978828290, 'systemUser', '2023-03-20 11:17:06', 'systemUser', '2023-03-20 11:17:06', NULL, 'security_menu', 'name', '新增', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1637654533332717570, 'systemUser', '2023-03-20 11:17:22', 'systemUser', '2023-03-20 11:17:22', NULL, 'security_menu', 'name', 'Add', 'en_US');
INSERT INTO `locale_international_name` VALUES (1637654587099500545, 'systemUser', '2023-03-20 11:17:34', 'systemUser', '2023-03-20 11:17:34', NULL, 'security_menu', 'name', '修改', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1637654647296151554, 'systemUser', '2023-03-20 11:17:49', 'systemUser', '2023-03-20 11:17:49', NULL, 'security_menu', 'name', 'Edit', 'en_US');
INSERT INTO `locale_international_name` VALUES (1637654712052011010, 'systemUser', '2023-03-20 11:18:04', 'systemUser', '2023-03-20 11:18:04', NULL, 'security_menu', 'name', '删除', 'zh_CN');
INSERT INTO `locale_international_name` VALUES (1637654766259195906, 'systemUser', '2023-03-20 11:18:17', 'systemUser', '2023-03-20 16:18:13', NULL, 'security_menu', 'name', 'Delete', 'en_US');

SET FOREIGN_KEY_CHECKS = 1;