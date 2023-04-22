SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Records of security_role
-- ----------------------------
INSERT INTO `security_role` VALUES (1644589480094113793, 'superAdmin', '2023-04-08 14:34:22', 'superAdmin', '2023-04-11 19:12:29', '系统管理员', '拥有系统管理权限');
INSERT INTO `security_role` VALUES (1644591359763062786, 'superAdmin', '2023-04-08 14:41:50', 'superAdmin', '2023-04-08 14:41:50', '运维人员', '拥有查看日志的权限');

-- ----------------------------
-- Records of security_role_user
-- ----------------------------
INSERT INTO `security_role_user` VALUES (1645392640714924033, 'superAdmin', '2023-04-10 19:45:50', 1644589480094113793, 1645392640257744897);
INSERT INTO `security_role_user` VALUES (1645392640844947458, 'superAdmin', '2023-04-10 19:45:50', 1644591359763062786, 1645392640257744897);
INSERT INTO `security_role_user` VALUES (1645393565953220610, 'superAdmin', '2023-04-10 19:49:31', 1644591359763062786, 1645393565563150337);

-- ----------------------------
-- Records of security_user
-- ----------------------------
INSERT INTO `security_user` VALUES (1598688513380720706, 'systemUser', '2023-03-17 08:46:41', 'superAdmin', '2023-04-10 15:26:49', NULL, 'superAdmin', '$2a$10$WqQqJ7EU.AN.CeG.DJ3Bdel3ZMkPSpIINo.ym1G9DF772iRJDwlnm', '超级管理员', 0, '', 'xiajhuan@163.com', '', 1, 0, 0);
INSERT INTO `security_user` VALUES (1645392640257744897, 'superAdmin', '2023-04-10 19:45:50', 'superAdmin', '2023-04-10 19:45:50', 1636548298722242561, 'systemAdmin', '$2a$10$/9P6GsRq0um4DAzN.vRLIexEhiWYMDt2heIQTwgi.mGLCRTN5.lHO', '系统管理员', 2, '', 'xiajhuan@163.com', '13615752444', 1, 1, 1);
INSERT INTO `security_user` VALUES (1645393565563150337, 'superAdmin', '2023-04-10 19:49:31', 'superAdmin', '2023-04-10 19:49:31', 1636548627622785026, 'test233', '$2a$10$afiXsN.BcQ4J2sBwQdH3q.LrP5fj97K3qsHKvsReDUH2AVYiG0a3e', '测试账号', 0, 'xxx', '', '', 1, 1, 3);

-- ----------------------------
-- Records of security_user_post
-- ----------------------------
INSERT INTO `security_user_post` VALUES (1645392641306320898, 'superAdmin', '2023-04-10 19:45:50', 1645392640257744897, 1644573719426424833);
INSERT INTO `security_user_post` VALUES (1645393566473314306, 'superAdmin', '2023-04-10 19:49:31', 1645393565563150337, 1644573768797577217);

-- ----------------------------
-- Records of security_post
-- ----------------------------
INSERT INTO `security_post` VALUES (1644572881052164098, 'superAdmin', '2023-04-08 13:28:24', 'superAdmin', '2023-04-08 13:28:24', '1001', '技术岗', 1);
INSERT INTO `security_post` VALUES (1644573719426424833, 'superAdmin', '2023-04-08 13:31:44', 'superAdmin', '2023-04-08 13:31:44', '1002', '管理岗', 1);
INSERT INTO `security_post` VALUES (1644573768797577217, 'superAdmin', '2023-04-08 13:31:56', 'superAdmin', '2023-04-08 13:48:29', '1003', '测试岗233', 0);

-- ----------------------------
-- Records of security_role_menu
-- ----------------------------
INSERT INTO `security_role_menu` VALUES (1649759603721437185, 'superAdmin', '2023-04-22 20:58:35', 1644591359763062786, 1644250886280286210);
INSERT INTO `security_role_menu` VALUES (1649759603968901121, 'superAdmin', '2023-04-22 20:58:35', 1644591359763062786, 1644251921644228609);
INSERT INTO `security_role_menu` VALUES (1649759604233142273, 'superAdmin', '2023-04-22 20:58:35', 1644591359763062786, 1644254129991421954);
INSERT INTO `security_role_menu` VALUES (1649759604497383425, 'superAdmin', '2023-04-22 20:58:35', 1644591359763062786, 1644254438981603329);
INSERT INTO `security_role_menu` VALUES (1649759604765818881, 'superAdmin', '2023-04-22 20:58:35', 1644591359763062786, 1644249552424505346);
INSERT INTO `security_role_menu` VALUES (1649759605025865729, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1644254772470714369);
INSERT INTO `security_role_menu` VALUES (1649759605285912578, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1644253821047377922);
INSERT INTO `security_role_menu` VALUES (1649759605545959425, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1644251503899938817);
INSERT INTO `security_role_menu` VALUES (1649759605814394882, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1644253286663688193);
INSERT INTO `security_role_menu` VALUES (1649759606074441729, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1644252869271719938);
INSERT INTO `security_role_menu` VALUES (1649759606342877185, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1649750393847742465);
INSERT INTO `security_role_menu` VALUES (1649759606544203777, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1649751116480184322);
INSERT INTO `security_role_menu` VALUES (1649759606812639233, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1649751675778039809);
INSERT INTO `security_role_menu` VALUES (1649760227770957826, 'superAdmin', '2023-04-22 21:01:04', 1644589480094113793, 1644147910819700738);
INSERT INTO `security_role_menu` VALUES (1649760228039393282, 'superAdmin', '2023-04-22 21:01:04', 1644589480094113793, 1644152845405667330);
INSERT INTO `security_role_menu` VALUES (1649760228236525570, 'superAdmin', '2023-04-22 21:01:04', 1644589480094113793, 1644153599092736001);
INSERT INTO `security_role_menu` VALUES (1649760228563681282, 'superAdmin', '2023-04-22 21:01:04', 1644589480094113793, 1644154672847142914);
INSERT INTO `security_role_menu` VALUES (1649760228827922434, 'superAdmin', '2023-04-22 21:01:04', 1644589480094113793, 1644155270992642049);
INSERT INTO `security_role_menu` VALUES (1649760229092163586, 'superAdmin', '2023-04-22 21:01:04', 1644589480094113793, 1644157656800935937);
INSERT INTO `security_role_menu` VALUES (1649760229348016129, 'superAdmin', '2023-04-22 21:01:04', 1644589480094113793, 1644158403013754882);
INSERT INTO `security_role_menu` VALUES (1649760229608062978, 'superAdmin', '2023-04-22 21:01:04', 1644589480094113793, 1644158848205570050);
INSERT INTO `security_role_menu` VALUES (1649760229876498434, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644159233980874754);
INSERT INTO `security_role_menu` VALUES (1649760230140739586, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644159962489536514);
INSERT INTO `security_role_menu` VALUES (1649760230543392769, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644160692621393922);
INSERT INTO `security_role_menu` VALUES (1649760230878937090, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644161676202459138);
INSERT INTO `security_role_menu` VALUES (1649760231138983938, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644162022752632834);
INSERT INTO `security_role_menu` VALUES (1649760231331921921, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644162242701934594);
INSERT INTO `security_role_menu` VALUES (1649760231659077633, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644162484654555138);
INSERT INTO `security_role_menu` VALUES (1649760231860404225, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644247399261769730);
INSERT INTO `security_role_menu` VALUES (1649760232195948545, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644247996216086530);
INSERT INTO `security_role_menu` VALUES (1649760232527298562, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644248300751917057);
INSERT INTO `security_role_menu` VALUES (1649760232724430850, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644248585805205505);
INSERT INTO `security_role_menu` VALUES (1649760232988672002, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644249552424505346);
INSERT INTO `security_role_menu` VALUES (1649760233248718849, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644250886280286210);
INSERT INTO `security_role_menu` VALUES (1649760233450045441, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644251503899938817);
INSERT INTO `security_role_menu` VALUES (1649760233718480898, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644251921644228609);
INSERT INTO `security_role_menu` VALUES (1649760233919807489, 'superAdmin', '2023-04-22 21:01:05', 1644589480094113793, 1644252869271719938);
INSERT INTO `security_role_menu` VALUES (1649760234121134082, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644253286663688193);
INSERT INTO `security_role_menu` VALUES (1649760234381180929, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644253821047377922);
INSERT INTO `security_role_menu` VALUES (1649760234641227777, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644254129991421954);
INSERT INTO `security_role_menu` VALUES (1649760234909663233, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644254438981603329);
INSERT INTO `security_role_menu` VALUES (1649760235106795521, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644254772470714369);
INSERT INTO `security_role_menu` VALUES (1649760235303927810, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644256030950658049);
INSERT INTO `security_role_menu` VALUES (1649760235572363265, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644256659760713730);
INSERT INTO `security_role_menu` VALUES (1649760235769495554, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644256943945781250);
INSERT INTO `security_role_menu` VALUES (1649760236033736705, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644257173638451202);
INSERT INTO `security_role_menu` VALUES (1649760236226674690, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644257432523476994);
INSERT INTO `security_role_menu` VALUES (1649760236495110146, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644257749889683457);
INSERT INTO `security_role_menu` VALUES (1649760236830654466, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644258035748278274);
INSERT INTO `security_role_menu` VALUES (1649760237090701313, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644258340347023362);
INSERT INTO `security_role_menu` VALUES (1649760237292027905, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644587091928719361);
INSERT INTO `security_role_menu` VALUES (1649760237493354498, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644587720784912385);
INSERT INTO `security_role_menu` VALUES (1649760237761789953, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644587976218025985);
INSERT INTO `security_role_menu` VALUES (1649760238021836802, 'superAdmin', '2023-04-22 21:01:06', 1644589480094113793, 1644588210742534145);
INSERT INTO `security_role_menu` VALUES (1649760238223163393, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645732824954413057);
INSERT INTO `security_role_menu` VALUES (1649760238416101378, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645733333916426241);
INSERT INTO `security_role_menu` VALUES (1649760238684536833, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645733692504252418);
INSERT INTO `security_role_menu` VALUES (1649760239015886850, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645734043018043393);
INSERT INTO `security_role_menu` VALUES (1649760239280128002, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645734305539530753);
INSERT INTO `security_role_menu` VALUES (1649760239481454593, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645738549151768577);
INSERT INTO `security_role_menu` VALUES (1649760239749890049, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645740146187542529);
INSERT INTO `security_role_menu` VALUES (1649760239947022337, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645740925552140289);
INSERT INTO `security_role_menu` VALUES (1649760240207069185, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1645742458788356097);
INSERT INTO `security_role_menu` VALUES (1649760240467116033, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1649738171612332034);
INSERT INTO `security_role_menu` VALUES (1649760240660054017, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1649739344822075394);
INSERT INTO `security_role_menu` VALUES (1649760240928489473, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1649739992561025026);
INSERT INTO `security_role_menu` VALUES (1649760241196924929, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1649740221444194306);
INSERT INTO `security_role_menu` VALUES (1649760241394057218, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1649740418698117122);
INSERT INTO `security_role_menu` VALUES (1649760241662492673, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1649740703201951745);
INSERT INTO `security_role_menu` VALUES (1649760241930928129, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1649741035432771585);
INSERT INTO `security_role_menu` VALUES (1649760242132254722, 'superAdmin', '2023-04-22 21:01:07', 1644589480094113793, 1649741628071149570);
INSERT INTO `security_role_menu` VALUES (1649760242467799041, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1649750393847742465);
INSERT INTO `security_role_menu` VALUES (1649760242664931330, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1649751116480184322);
INSERT INTO `security_role_menu` VALUES (1649760243000475649, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1649751675778039809);

-- ----------------------------
-- Records of security_menu
-- ----------------------------
INSERT INTO `security_menu` VALUES (1644147910819700738, 'superAdmin', '2023-04-07 09:19:43', 'superAdmin', '2023-04-07 09:19:43', 0, '', '', 0, 0, 'icon-safetycertificate', 0);
INSERT INTO `security_menu` VALUES (1644152845405667330, 'superAdmin', '2023-04-07 09:39:20', 'superAdmin', '2023-04-07 09:39:20', 1644147910819700738, 'security/user', '', 0, 0, 'icon-user', 1);
INSERT INTO `security_menu` VALUES (1644153599092736001, 'superAdmin', '2023-04-07 09:42:20', 'superAdmin', '2023-04-07 09:42:20', 1644147910819700738, 'security/post', '', 0, 0, 'icon-pic-left', 2);
INSERT INTO `security_menu` VALUES (1644154672847142914, 'superAdmin', '2023-04-07 09:46:36', 'superAdmin', '2023-04-07 09:46:36', 1644147910819700738, 'security/dept', '', 0, 0, 'icon-apartment', 4);
INSERT INTO `security_menu` VALUES (1644155270992642049, 'superAdmin', '2023-04-07 09:48:58', 'superAdmin', '2023-04-07 09:48:58', 1644147910819700738, 'security/role', '', 0, 0, 'icon-team', 0);
INSERT INTO `security_menu` VALUES (1644157656800935937, 'superAdmin', '2023-04-07 09:58:27', 'superAdmin', '2023-04-07 09:58:27', 1644154672847142914, '', 'security:dept:list,security:dept:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644158403013754882, 'superAdmin', '2023-04-07 10:01:25', 'superAdmin', '2023-04-07 10:01:25', 1644154672847142914, '', 'security:dept:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644158848205570050, 'superAdmin', '2023-04-07 10:03:11', 'superAdmin', '2023-04-07 10:03:11', 1644154672847142914, '', 'security:dept:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644159233980874754, 'superAdmin', '2023-04-07 10:04:43', 'superAdmin', '2023-04-07 10:04:43', 1644154672847142914, '', 'security:dept:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1644159962489536514, 'superAdmin', '2023-04-07 10:07:37', 'superAdmin', '2023-04-07 10:07:37', 0, '', '', 0, 0, 'icon-setting', 1);
INSERT INTO `security_menu` VALUES (1644160692621393922, 'superAdmin', '2023-04-07 10:10:31', 'superAdmin', '2023-04-07 10:10:31', 1644147910819700738, 'security/menu', '', 0, 0, 'icon-unorderedlist', 3);
INSERT INTO `security_menu` VALUES (1644161676202459138, 'superAdmin', '2023-04-07 10:14:25', 'superAdmin', '2023-04-07 10:14:25', 1644160692621393922, '', 'security:menu:list,security:menu:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644162022752632834, 'superAdmin', '2023-04-07 10:15:48', 'superAdmin', '2023-04-07 10:15:48', 1644160692621393922, '', 'security:menu:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644162242701934594, 'superAdmin', '2023-04-07 10:16:40', 'superAdmin', '2023-04-07 10:16:40', 1644160692621393922, '', 'security:menu:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644162484654555138, 'superAdmin', '2023-04-07 10:17:38', 'superAdmin', '2023-04-07 11:13:24', 1644160692621393922, '', 'security:menu:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1644247399261769730, 'superAdmin', '2023-04-07 15:55:03', 'superAdmin', '2023-04-07 15:55:03', 1644155270992642049, '', 'security:role:page,security:role:list,security:role:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644247996216086530, 'superAdmin', '2023-04-07 15:57:26', 'superAdmin', '2023-04-07 15:57:26', 1644155270992642049, '', 'security:role:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644248300751917057, 'superAdmin', '2023-04-07 15:58:38', 'superAdmin', '2023-04-07 15:58:38', 1644155270992642049, '', 'security:role:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644248585805205505, 'superAdmin', '2023-04-07 15:59:46', 'superAdmin', '2023-04-07 15:59:46', 1644155270992642049, '', 'security:role:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1644249552424505346, 'superAdmin', '2023-04-07 16:03:37', 'superAdmin', '2023-04-07 16:03:37', 0, '', '', 0, 0, 'icon-container', 2);
INSERT INTO `security_menu` VALUES (1644250886280286210, 'superAdmin', '2023-04-07 16:08:55', 'superAdmin', '2023-04-07 16:08:55', 1644249552424505346, 'log/operation', '', 0, 0, 'icon-solution', 0);
INSERT INTO `security_menu` VALUES (1644251503899938817, 'superAdmin', '2023-04-07 16:11:22', 'superAdmin', '2023-04-07 16:11:22', 1644249552424505346, 'log/error', '', 0, 0, 'icon-file-exception', 1);
INSERT INTO `security_menu` VALUES (1644251921644228609, 'superAdmin', '2023-04-07 16:13:02', 'superAdmin', '2023-04-07 16:13:02', 1644249552424505346, 'log/login', '', 0, 0, 'icon-filedone', 2);
INSERT INTO `security_menu` VALUES (1644252869271719938, 'superAdmin', '2023-04-07 16:16:47', 'superAdmin', '2023-04-07 16:16:47', 1644250886280286210, '', 'log:operation:page', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644253286663688193, 'superAdmin', '2023-04-07 16:18:27', 'superAdmin', '2023-04-07 16:18:27', 1644250886280286210, '', 'log:operation:excelExport', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644253821047377922, 'superAdmin', '2023-04-07 16:20:34', 'superAdmin', '2023-04-07 16:20:34', 1644251503899938817, '', 'log:error:page,log:error:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644254129991421954, 'superAdmin', '2023-04-07 16:21:48', 'superAdmin', '2023-04-07 16:21:48', 1644251503899938817, '', 'log:error:excelExport', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644254438981603329, 'superAdmin', '2023-04-07 16:23:02', 'superAdmin', '2023-04-07 16:23:02', 1644251921644228609, '', 'log:login:page', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644254772470714369, 'superAdmin', '2023-04-07 16:24:21', 'superAdmin', '2023-04-07 16:24:21', 1644251921644228609, '', 'log:login:excelExport', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644256030950658049, 'superAdmin', '2023-04-07 16:29:21', 'superAdmin', '2023-04-07 16:29:21', 1644159962489536514, 'locale/name', '', 0, 0, 'icon-file-word', 1);
INSERT INTO `security_menu` VALUES (1644256659760713730, 'superAdmin', '2023-04-07 16:31:51', 'superAdmin', '2023-04-07 16:31:51', 1644256030950658049, '', 'locale:name:page,locale:name:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644256943945781250, 'superAdmin', '2023-04-07 16:32:59', 'superAdmin', '2023-04-07 16:32:59', 1644256030950658049, '', 'locale:name:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644257173638451202, 'superAdmin', '2023-04-07 16:33:54', 'superAdmin', '2023-04-07 16:33:54', 1644256030950658049, '', 'locale:name:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644257432523476994, 'superAdmin', '2023-04-07 16:34:55', 'superAdmin', '2023-04-07 16:34:55', 1644256030950658049, '', 'locale:name:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1644257749889683457, 'superAdmin', '2023-04-07 16:36:11', 'superAdmin', '2023-04-07 16:36:11', 1644256030950658049, '', 'locale:name:excelTemplate', 1, NULL, '', 4);
INSERT INTO `security_menu` VALUES (1644258035748278274, 'superAdmin', '2023-04-07 16:37:19', 'superAdmin', '2023-04-07 16:37:19', 1644256030950658049, '', 'locale:name:excelImport', 1, NULL, '', 5);
INSERT INTO `security_menu` VALUES (1644258340347023362, 'superAdmin', '2023-04-07 16:38:32', 'superAdmin', '2023-04-07 16:38:32', 1644256030950658049, '', 'locale:name:excelExport', 1, NULL, '', 6);
INSERT INTO `security_menu` VALUES (1644587091928719361, 'superAdmin', '2023-04-08 14:24:52', 'superAdmin', '2023-04-08 14:24:52', 1644153599092736001, '', 'security:post:page,security:post:list,security:post:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1644587720784912385, 'superAdmin', '2023-04-08 14:27:22', 'superAdmin', '2023-04-08 14:27:22', 1644153599092736001, '', 'security:post:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1644587976218025985, 'superAdmin', '2023-04-08 14:28:23', 'superAdmin', '2023-04-08 14:28:23', 1644153599092736001, '', 'security:post:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1644588210742534145, 'superAdmin', '2023-04-08 14:29:19', 'superAdmin', '2023-04-08 14:29:19', 1644153599092736001, '', 'security:post:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1645732824954413057, 'superAdmin', '2023-04-11 18:17:36', 'superAdmin', '2023-04-11 18:17:36', 1644152845405667330, '', 'security:user:page,security:user:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1645733333916426241, 'superAdmin', '2023-04-11 18:19:38', 'superAdmin', '2023-04-11 18:19:38', 1644152845405667330, '', 'security:user:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1645733692504252418, 'superAdmin', '2023-04-11 18:21:03', 'superAdmin', '2023-04-11 18:21:03', 1644152845405667330, '', 'security:user:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1645734043018043393, 'superAdmin', '2023-04-11 18:22:27', 'superAdmin', '2023-04-11 18:22:27', 1644152845405667330, '', 'security:user:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1645734305539530753, 'superAdmin', '2023-04-11 18:23:29', 'superAdmin', '2023-04-11 18:23:29', 1644152845405667330, '', 'security:user:excelExport', 1, NULL, '', 4);
INSERT INTO `security_menu` VALUES (1645738549151768577, 'superAdmin', '2023-04-11 18:40:21', 'superAdmin', '2023-04-11 18:40:21', 0, '', '', 0, 0, 'icon-desktop', 3);
INSERT INTO `security_menu` VALUES (1645740146187542529, 'superAdmin', '2023-04-11 18:46:42', 'superAdmin', '2023-04-11 18:46:42', 1645738549151768577, 'monitor/online', '', 0, 0, 'icon-team', 1);
INSERT INTO `security_menu` VALUES (1645740925552140289, 'superAdmin', '2023-04-11 18:49:48', 'superAdmin', '2023-04-11 18:49:48', 1645740146187542529, '', 'monitor:online:page', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1645742458788356097, 'superAdmin', '2023-04-11 18:55:53', 'superAdmin', '2023-04-11 18:55:53', 1645738549151768577, 'monitor/system', 'monitor:system:info', 0, 0, 'icon-database', 0);
INSERT INTO `security_menu` VALUES (1649738171612332034, 'superAdmin', '2023-04-22 19:33:25', 'superAdmin', '2023-04-22 19:33:25', 1644159962489536514, 'schedule/task', '', 0, 0, 'icon-dashboard', 0);
INSERT INTO `security_menu` VALUES (1649739344822075394, 'superAdmin', '2023-04-22 19:38:05', 'superAdmin', '2023-04-22 19:38:05', 1649738171612332034, '', 'schedule:task:page,schedule:task:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1649739992561025026, 'superAdmin', '2023-04-22 19:40:40', 'superAdmin', '2023-04-22 19:40:40', 1649738171612332034, '', 'schedule:task:add', 1, NULL, '', 1);
INSERT INTO `security_menu` VALUES (1649740221444194306, 'superAdmin', '2023-04-22 19:41:34', 'superAdmin', '2023-04-22 19:41:34', 1649738171612332034, '', 'schedule:task:update', 1, NULL, '', 2);
INSERT INTO `security_menu` VALUES (1649740418698117122, 'superAdmin', '2023-04-22 19:42:21', 'superAdmin', '2023-04-22 19:42:21', 1649738171612332034, '', 'schedule:task:delete', 1, NULL, '', 3);
INSERT INTO `security_menu` VALUES (1649740703201951745, 'superAdmin', '2023-04-22 19:43:29', 'superAdmin', '2023-04-22 19:43:29', 1649738171612332034, '', 'schedule:task:execute', 1, NULL, '', 4);
INSERT INTO `security_menu` VALUES (1649741035432771585, 'superAdmin', '2023-04-22 19:44:48', 'superAdmin', '2023-04-22 19:44:48', 1649738171612332034, '', 'schedule:task:pause', 1, NULL, '', 5);
INSERT INTO `security_menu` VALUES (1649741628071149570, 'superAdmin', '2023-04-22 19:47:10', 'superAdmin', '2023-04-22 19:47:10', 1649738171612332034, '', 'schedule:task:resume', 1, NULL, '', 6);
INSERT INTO `security_menu` VALUES (1649750393847742465, 'superAdmin', '2023-04-22 20:21:59', 'superAdmin', '2023-04-22 20:21:59', 1644249552424505346, 'log/task', '', 0, 0, 'icon-dashboard', 3);
INSERT INTO `security_menu` VALUES (1649751116480184322, 'superAdmin', '2023-04-22 20:24:52', 'superAdmin', '2023-04-22 20:24:52', 1649750393847742465, '', 'log:task:page,log:task:getById', 1, NULL, '', 0);
INSERT INTO `security_menu` VALUES (1649751675778039809, 'superAdmin', '2023-04-22 20:27:05', 'superAdmin', '2023-04-22 20:27:05', 1649750393847742465, '', 'log:task:excelExport', 1, NULL, '', 1);

-- ----------------------------
-- Records of security_role_dept
-- ----------------------------
INSERT INTO `security_role_dept` VALUES (1649759607278206978, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1636548627622785026);
INSERT INTO `security_role_dept` VALUES (1649759607475339266, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1636548954166128641);
INSERT INTO `security_role_dept` VALUES (1649759607726997505, 'superAdmin', '2023-04-22 20:58:36', 1644591359763062786, 1636549079382880258);
INSERT INTO `security_role_dept` VALUES (1649760243537346561, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636548298722242561);
INSERT INTO `security_role_dept` VALUES (1649760243738673153, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636548627622785026);
INSERT INTO `security_role_dept` VALUES (1649760244007108610, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636548808003022850);
INSERT INTO `security_role_dept` VALUES (1649760244208435202, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636548954166128641);
INSERT INTO `security_role_dept` VALUES (1649760244472676354, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636549079382880258);
INSERT INTO `security_role_dept` VALUES (1649760244669808642, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636549217312567298);
INSERT INTO `security_role_dept` VALUES (1649760244938244097, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636549260430012417);
INSERT INTO `security_role_dept` VALUES (1649760245206679553, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636549294890414082);
INSERT INTO `security_role_dept` VALUES (1649760245462532097, 'superAdmin', '2023-04-22 21:01:08', 1644589480094113793, 1636549534884294658);

-- ----------------------------
-- Records of security_dept
-- ----------------------------
INSERT INTO `security_dept` VALUES (1636548298722242561, 'superAdmin', '2023-03-17 10:01:35', 'systemUser', '2023-03-17 10:01:35', 0, '0', '罗邦洁具股份有限公司', 0);
INSERT INTO `security_dept` VALUES (1636548627622785026, 'superAdmin', '2023-03-17 10:02:53', 'systemUser', '2023-03-17 10:02:53', 1636548298722242561, '1636548298722242561', '泰国工厂', 0);
INSERT INTO `security_dept` VALUES (1636548808003022850, 'superAdmin', '2023-03-17 10:03:36', 'systemUser', '2023-03-17 10:03:36', 1636548298722242561, '1636548298722242561', '椒江工厂', 1);
INSERT INTO `security_dept` VALUES (1636548954166128641, 'superAdmin', '2023-03-17 10:04:11', 'systemUser', '2023-03-17 10:04:11', 1636548627622785026, '1636548627622785026,1636548298722242561', '市场部', 0);
INSERT INTO `security_dept` VALUES (1636549079382880258, 'superAdmin', '2023-03-17 10:04:41', 'systemUser', '2023-03-17 10:04:41', 1636548627622785026, '1636548627622785026,1636548298722242561', '销售部', 1);
INSERT INTO `security_dept` VALUES (1636549217312567298, 'superAdmin', '2023-03-17 10:05:14', 'systemUser', '2023-03-17 10:05:14', 1636548808003022850, '1636548808003022850,1636548298722242561', '产品部', 0);
INSERT INTO `security_dept` VALUES (1636549260430012417, 'superAdmin', '2023-03-17 10:05:24', 'systemUser', '2023-03-17 10:05:24', 1636548808003022850, '1636548808003022850,1636548298722242561', '技术部', 1);
INSERT INTO `security_dept` VALUES (1636549294890414082, 'superAdmin', '2023-03-17 10:05:32', 'systemUser', '2023-03-17 10:05:32', 1636548808003022850, '1636548808003022850,1636548298722242561', '销售部', 2);
INSERT INTO `security_dept` VALUES (1636549534884294658, 'superAdmin', '2023-03-17 10:06:29', 'systemUser', '2023-03-17 10:08:10', 1636549260430012417, '1636548808003022850,1636548298722242561,1636549260430012417', '模具研发部', 0);

-- ----------------------------
-- Records of locale_name
-- ----------------------------
INSERT INTO `locale_name` VALUES (1644147911247519745, 'superAdmin', '2023-04-07 09:19:44', 'superAdmin', '2023-04-07 09:19:44', 'security_menu', 1644147910819700738, 'name', '权限管理', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644150551851188226, 'superAdmin', '2023-04-07 09:30:13', 'superAdmin', '2023-04-07 09:30:13', 'security_menu', 1644147910819700738, 'name', 'Authority management', 'en_US');
INSERT INTO `locale_name` VALUES (1644152845405667331, 'superAdmin', '2023-04-07 09:39:20', 'superAdmin', '2023-04-07 09:39:20', 'security_menu', 1644152845405667330, 'name', '用户管理', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644153014499033090, 'superAdmin', '2023-04-07 09:40:00', 'superAdmin', '2023-04-07 09:40:00', 'security_menu', 1644152845405667330, 'name', 'User management', 'en_US');
INSERT INTO `locale_name` VALUES (1644153599092736002, 'superAdmin', '2023-04-07 09:42:20', 'superAdmin', '2023-04-07 09:42:20', 'security_menu', 1644153599092736001, 'name', '岗位管理', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644153831411040258, 'superAdmin', '2023-04-07 09:43:15', 'superAdmin', '2023-04-07 09:43:15', 'security_menu', 1644153599092736001, 'name', 'Post management', 'en_US');
INSERT INTO `locale_name` VALUES (1644154672847142915, 'superAdmin', '2023-04-07 09:46:36', 'superAdmin', '2023-04-07 09:46:36', 'security_menu', 1644154672847142914, 'name', '部门管理', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644154920399159299, 'superAdmin', '2023-04-07 09:47:35', 'superAdmin', '2023-04-07 09:47:35', 'security_menu', 1644154672847142914, 'name', 'Department management', 'en_US');
INSERT INTO `locale_name` VALUES (1644155270992642050, 'superAdmin', '2023-04-07 09:48:58', 'superAdmin', '2023-04-07 09:48:58', 'security_menu', 1644155270992642049, 'name', '角色管理', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644155423136825345, 'superAdmin', '2023-04-07 09:49:34', 'superAdmin', '2023-04-07 09:49:34', 'security_menu', 1644155270992642049, 'name', 'Role management', 'en_US');
INSERT INTO `locale_name` VALUES (1644157657094537217, 'superAdmin', '2023-04-07 09:58:27', 'superAdmin', '2023-04-07 09:58:27', 'security_menu', 1644157656800935937, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644157964012732418, 'superAdmin', '2023-04-07 09:59:40', 'superAdmin', '2023-04-07 09:59:40', 'security_menu', 1644157656800935937, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1644158403013754883, 'superAdmin', '2023-04-07 10:01:25', 'superAdmin', '2023-04-07 10:01:25', 'security_menu', 1644158403013754882, 'name', '新增', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644158554126139394, 'superAdmin', '2023-04-07 10:02:01', 'superAdmin', '2023-04-07 10:02:01', 'security_menu', 1644158403013754882, 'name', 'Add', 'en_US');
INSERT INTO `locale_name` VALUES (1644158848205570051, 'superAdmin', '2023-04-07 10:03:11', 'superAdmin', '2023-04-07 10:03:11', 'security_menu', 1644158848205570050, 'name', '修改', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644159040074006531, 'superAdmin', '2023-04-07 10:03:57', 'superAdmin', '2023-04-07 10:03:57', 'security_menu', 1644158848205570050, 'name', 'Update', 'en_US');
INSERT INTO `locale_name` VALUES (1644159234047983618, 'superAdmin', '2023-04-07 10:04:43', 'superAdmin', '2023-04-07 10:04:43', 'security_menu', 1644159233980874754, 'name', '删除', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644159270886555651, 'superAdmin', '2023-04-07 10:04:52', 'superAdmin', '2023-04-07 10:04:52', 'security_menu', 1644159233980874754, 'name', 'Delete', 'en_US');
INSERT INTO `locale_name` VALUES (1644159962556645378, 'superAdmin', '2023-04-07 10:07:37', 'superAdmin', '2023-04-07 10:07:37', 'security_menu', 1644159962489536514, 'name', '系统设置', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644160077732233219, 'superAdmin', '2023-04-07 10:08:04', 'superAdmin', '2023-04-07 10:08:04', 'security_menu', 1644159962489536514, 'name', 'System setting', 'en_US');
INSERT INTO `locale_name` VALUES (1644160692621393923, 'superAdmin', '2023-04-07 10:10:31', 'superAdmin', '2023-04-07 10:10:31', 'security_menu', 1644160692621393922, 'name', '菜单管理', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644160890819035139, 'superAdmin', '2023-04-07 10:11:18', 'superAdmin', '2023-04-07 10:11:18', 'security_menu', 1644160692621393922, 'name', 'Menu management', 'en_US');
INSERT INTO `locale_name` VALUES (1644161676202459139, 'superAdmin', '2023-04-07 10:14:25', 'superAdmin', '2023-04-07 10:14:25', 'security_menu', 1644161676202459138, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644161815008755714, 'superAdmin', '2023-04-07 10:14:58', 'superAdmin', '2023-04-07 10:14:58', 'security_menu', 1644161676202459138, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1644162022752632835, 'superAdmin', '2023-04-07 10:15:48', 'superAdmin', '2023-04-07 10:15:48', 'security_menu', 1644162022752632834, 'name', '新增', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644162042633633795, 'superAdmin', '2023-04-07 10:15:53', 'superAdmin', '2023-04-07 10:15:53', 'security_menu', 1644162022752632834, 'name', 'Add', 'en_US');
INSERT INTO `locale_name` VALUES (1644162242701934595, 'superAdmin', '2023-04-07 10:16:40', 'superAdmin', '2023-04-07 10:16:40', 'security_menu', 1644162242701934594, 'name', '修改', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644162299870298114, 'superAdmin', '2023-04-07 10:16:54', 'superAdmin', '2023-04-07 10:16:54', 'security_menu', 1644162242701934594, 'name', 'Update', 'en_US');
INSERT INTO `locale_name` VALUES (1644162484654555139, 'superAdmin', '2023-04-07 10:17:38', 'superAdmin', '2023-04-07 11:13:24', 'security_menu', 1644162484654555138, 'name', '删除', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644162510969618433, 'superAdmin', '2023-04-07 10:17:44', 'superAdmin', '2023-04-07 10:17:44', 'security_menu', 1644162484654555138, 'name', 'Delete', 'en_US');
INSERT INTO `locale_name` VALUES (1644247399328878594, 'superAdmin', '2023-04-07 15:55:03', 'superAdmin', '2023-04-07 15:55:03', 'security_menu', 1644247399261769730, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644247677201518593, 'superAdmin', '2023-04-07 15:56:10', 'superAdmin', '2023-04-07 15:56:10', 'security_menu', 1644247399261769730, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1644247996283195393, 'superAdmin', '2023-04-07 15:57:26', 'superAdmin', '2023-04-07 15:57:26', 'security_menu', 1644247996216086530, 'name', '新增', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644248148909723651, 'superAdmin', '2023-04-07 15:58:02', 'superAdmin', '2023-04-07 15:58:02', 'security_menu', 1644247996216086530, 'name', 'Add', 'en_US');
INSERT INTO `locale_name` VALUES (1644248300751917058, 'superAdmin', '2023-04-07 15:58:38', 'superAdmin', '2023-04-07 15:58:38', 'security_menu', 1644248300751917057, 'name', '修改', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644248368116633601, 'superAdmin', '2023-04-07 15:58:54', 'superAdmin', '2023-04-07 15:58:54', 'security_menu', 1644248300751917057, 'name', 'Update', 'en_US');
INSERT INTO `locale_name` VALUES (1644248585805205506, 'superAdmin', '2023-04-07 15:59:46', 'superAdmin', '2023-04-07 15:59:46', 'security_menu', 1644248585805205505, 'name', '删除', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644248660694503425, 'superAdmin', '2023-04-07 16:00:04', 'superAdmin', '2023-04-07 16:00:04', 'security_menu', 1644248585805205505, 'name', 'Delete', 'en_US');
INSERT INTO `locale_name` VALUES (1644249552424505347, 'superAdmin', '2023-04-07 16:03:37', 'superAdmin', '2023-04-07 16:03:37', 'security_menu', 1644249552424505346, 'name', '日志管理', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644249910454489089, 'superAdmin', '2023-04-07 16:05:02', 'superAdmin', '2023-04-07 16:05:02', 'security_menu', 1644249552424505346, 'name', 'Log management', 'en_US');
INSERT INTO `locale_name` VALUES (1644250886280286211, 'superAdmin', '2023-04-07 16:08:55', 'superAdmin', '2023-04-07 16:08:55', 'security_menu', 1644250886280286210, 'name', '操作日志', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644251048700514306, 'superAdmin', '2023-04-07 16:09:33', 'superAdmin', '2023-04-07 16:09:33', 'security_menu', 1644250886280286210, 'name', 'Operation log', 'en_US');
INSERT INTO `locale_name` VALUES (1644251503899938818, 'superAdmin', '2023-04-07 16:11:22', 'superAdmin', '2023-04-07 16:11:22', 'security_menu', 1644251503899938817, 'name', '错误日志', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644251628453990402, 'superAdmin', '2023-04-07 16:11:52', 'superAdmin', '2023-04-07 16:11:52', 'security_menu', 1644251503899938817, 'name', 'Error log', 'en_US');
INSERT INTO `locale_name` VALUES (1644251921711337474, 'superAdmin', '2023-04-07 16:13:02', 'superAdmin', '2023-04-07 16:13:02', 'security_menu', 1644251921644228609, 'name', '登录日志', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644252262326571010, 'superAdmin', '2023-04-07 16:14:23', 'superAdmin', '2023-04-07 16:14:23', 'security_menu', 1644251921644228609, 'name', 'Login log', 'en_US');
INSERT INTO `locale_name` VALUES (1644252869338828802, 'superAdmin', '2023-04-07 16:16:47', 'superAdmin', '2023-04-07 16:16:47', 'security_menu', 1644252869271719938, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644252983587475459, 'superAdmin', '2023-04-07 16:17:15', 'superAdmin', '2023-04-07 16:17:15', 'security_menu', 1644252869271719938, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1644253286663688194, 'superAdmin', '2023-04-07 16:18:27', 'superAdmin', '2023-04-07 16:18:27', 'security_menu', 1644253286663688193, 'name', '导出', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644253383296258049, 'superAdmin', '2023-04-07 16:18:50', 'superAdmin', '2023-04-07 16:18:50', 'security_menu', 1644253286663688193, 'name', 'Export', 'en_US');
INSERT INTO `locale_name` VALUES (1644253821110292481, 'superAdmin', '2023-04-07 16:20:34', 'superAdmin', '2023-04-07 16:20:34', 'security_menu', 1644253821047377922, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644253898168045569, 'superAdmin', '2023-04-07 16:20:53', 'superAdmin', '2023-04-07 16:20:53', 'security_menu', 1644253821047377922, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1644254130050142210, 'superAdmin', '2023-04-07 16:21:48', 'superAdmin', '2023-04-07 16:21:48', 'security_menu', 1644254129991421954, 'name', '导出', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644254218940026883, 'superAdmin', '2023-04-07 16:22:09', 'superAdmin', '2023-04-07 16:22:09', 'security_menu', 1644254129991421954, 'name', 'Export', 'en_US');
INSERT INTO `locale_name` VALUES (1644254438981603330, 'superAdmin', '2023-04-07 16:23:02', 'superAdmin', '2023-04-07 16:23:02', 'security_menu', 1644254438981603329, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644254514835591171, 'superAdmin', '2023-04-07 16:23:20', 'superAdmin', '2023-04-07 16:23:20', 'security_menu', 1644254438981603329, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1644254772533628930, 'superAdmin', '2023-04-07 16:24:21', 'superAdmin', '2023-04-07 16:24:21', 'security_menu', 1644254772470714369, 'name', '导出', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644254838614888450, 'superAdmin', '2023-04-07 16:24:37', 'superAdmin', '2023-04-07 16:24:37', 'security_menu', 1644254772470714369, 'name', 'Export', 'en_US');
INSERT INTO `locale_name` VALUES (1644256030950658050, 'superAdmin', '2023-04-07 16:29:21', 'superAdmin', '2023-04-07 16:29:21', 'security_menu', 1644256030950658049, 'name', '国际化名称', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644256271422689283, 'superAdmin', '2023-04-07 16:30:19', 'superAdmin', '2023-04-07 16:30:19', 'security_menu', 1644256030950658049, 'name', 'Internationalized name', 'en_US');
INSERT INTO `locale_name` VALUES (1644256659760713731, 'superAdmin', '2023-04-07 16:31:51', 'superAdmin', '2023-04-07 16:31:51', 'security_menu', 1644256659760713730, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644256729704927234, 'superAdmin', '2023-04-07 16:32:08', 'superAdmin', '2023-04-07 16:32:08', 'security_menu', 1644256659760713730, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1644256943945781251, 'superAdmin', '2023-04-07 16:32:59', 'superAdmin', '2023-04-07 16:32:59', 'security_menu', 1644256943945781250, 'name', '新增', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644257008533868547, 'superAdmin', '2023-04-07 16:33:14', 'superAdmin', '2023-04-07 16:33:14', 'security_menu', 1644256943945781250, 'name', 'Add', 'en_US');
INSERT INTO `locale_name` VALUES (1644257173705560066, 'superAdmin', '2023-04-07 16:33:54', 'superAdmin', '2023-04-07 16:33:54', 'security_menu', 1644257173638451202, 'name', '修改', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644257269612515330, 'superAdmin', '2023-04-07 16:34:17', 'superAdmin', '2023-04-07 16:34:17', 'security_menu', 1644257173638451202, 'name', 'Update', 'en_US');
INSERT INTO `locale_name` VALUES (1644257432590585858, 'superAdmin', '2023-04-07 16:34:55', 'superAdmin', '2023-04-07 16:34:55', 'security_menu', 1644257432523476994, 'name', '删除', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644257501062598657, 'superAdmin', '2023-04-07 16:35:12', 'superAdmin', '2023-04-07 16:35:12', 'security_menu', 1644257432523476994, 'name', 'Delete', 'en_US');
INSERT INTO `locale_name` VALUES (1644257749889683458, 'superAdmin', '2023-04-07 16:36:11', 'superAdmin', '2023-04-07 16:36:11', 'security_menu', 1644257749889683457, 'name', '模板下载', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644257842877403138, 'superAdmin', '2023-04-07 16:36:33', 'superAdmin', '2023-04-07 16:36:33', 'security_menu', 1644257749889683457, 'name', 'Template download', 'en_US');
INSERT INTO `locale_name` VALUES (1644258035815387137, 'superAdmin', '2023-04-07 16:37:19', 'superAdmin', '2023-04-07 16:37:19', 'security_menu', 1644258035748278274, 'name', '导入', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644258112684396546, 'superAdmin', '2023-04-07 16:37:38', 'superAdmin', '2023-04-07 16:37:38', 'security_menu', 1644258035748278274, 'name', 'Import', 'en_US');
INSERT INTO `locale_name` VALUES (1644258340347023363, 'superAdmin', '2023-04-07 16:38:32', 'superAdmin', '2023-04-07 16:38:32', 'security_menu', 1644258340347023362, 'name', '导出', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644258435306065922, 'superAdmin', '2023-04-07 16:38:54', 'superAdmin', '2023-04-07 16:38:54', 'security_menu', 1644258340347023362, 'name', 'Export', 'en_US');
INSERT INTO `locale_name` VALUES (1644587091995828225, 'superAdmin', '2023-04-08 14:24:52', 'superAdmin', '2023-04-08 14:24:52', 'security_menu', 1644587091928719361, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644587462822633475, 'superAdmin', '2023-04-08 14:26:21', 'superAdmin', '2023-04-08 14:26:21', 'security_menu', 1644587091928719361, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1644587720852021249, 'superAdmin', '2023-04-08 14:27:22', 'superAdmin', '2023-04-08 14:27:22', 'security_menu', 1644587720784912385, 'name', '新增', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644587840339353602, 'superAdmin', '2023-04-08 14:27:51', 'superAdmin', '2023-04-08 14:27:51', 'security_menu', 1644587720784912385, 'name', 'Add', 'en_US');
INSERT INTO `locale_name` VALUES (1644587976218025986, 'superAdmin', '2023-04-08 14:28:23', 'superAdmin', '2023-04-08 14:28:23', 'security_menu', 1644587976218025985, 'name', '修改', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644588030613954562, 'superAdmin', '2023-04-08 14:28:36', 'superAdmin', '2023-04-08 14:28:36', 'security_menu', 1644587976218025985, 'name', 'Update', 'en_US');
INSERT INTO `locale_name` VALUES (1644588210742534146, 'superAdmin', '2023-04-08 14:29:19', 'superAdmin', '2023-04-08 14:29:19', 'security_menu', 1644588210742534145, 'name', '删除', 'zh_CN');
INSERT INTO `locale_name` VALUES (1644588291713572865, 'superAdmin', '2023-04-08 14:29:38', 'superAdmin', '2023-04-08 14:29:38', 'security_menu', 1644588210742534145, 'name', 'Delete', 'en_US');
INSERT INTO `locale_name` VALUES (1645732825248014337, 'superAdmin', '2023-04-11 18:17:36', 'superAdmin', '2023-04-11 18:17:36', 'security_menu', 1645732824954413057, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645733041472774145, 'superAdmin', '2023-04-11 18:18:28', 'superAdmin', '2023-04-11 18:18:28', 'security_menu', 1645732824954413057, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1645733334243581953, 'superAdmin', '2023-04-11 18:19:38', 'superAdmin', '2023-04-11 18:19:38', 'security_menu', 1645733333916426241, 'name', '新增', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645733524279107585, 'superAdmin', '2023-04-11 18:20:23', 'superAdmin', '2023-04-11 18:20:23', 'security_menu', 1645733333916426241, 'name', 'Add', 'en_US');
INSERT INTO `locale_name` VALUES (1645733692827213826, 'superAdmin', '2023-04-11 18:21:03', 'superAdmin', '2023-04-11 18:21:03', 'security_menu', 1645733692504252418, 'name', '修改', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645733795524747265, 'superAdmin', '2023-04-11 18:21:28', 'superAdmin', '2023-04-11 18:21:28', 'security_menu', 1645733692504252418, 'name', 'Update', 'en_US');
INSERT INTO `locale_name` VALUES (1645734043278090242, 'superAdmin', '2023-04-11 18:22:27', 'superAdmin', '2023-04-11 18:22:27', 'security_menu', 1645734043018043393, 'name', '删除', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645734152409686018, 'superAdmin', '2023-04-11 18:22:53', 'superAdmin', '2023-04-11 18:22:53', 'security_menu', 1645734043018043393, 'name', 'Delete', 'en_US');
INSERT INTO `locale_name` VALUES (1645734305803771906, 'superAdmin', '2023-04-11 18:23:29', 'superAdmin', '2023-04-11 18:23:29', 'security_menu', 1645734305539530753, 'name', '导出', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645734542974885889, 'superAdmin', '2023-04-11 18:24:26', 'superAdmin', '2023-04-11 18:24:26', 'security_menu', 1645734305539530753, 'name', 'Export', 'en_US');
INSERT INTO `locale_name` VALUES (1645738549416009729, 'superAdmin', '2023-04-11 18:40:21', 'superAdmin', '2023-04-11 18:40:21', 'security_menu', 1645738549151768577, 'name', '系统监控', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645738955319779329, 'superAdmin', '2023-04-11 18:41:58', 'superAdmin', '2023-04-11 18:41:58', 'security_menu', 1645738549151768577, 'name', 'System Monitoring', 'en_US');
INSERT INTO `locale_name` VALUES (1645740146447589377, 'superAdmin', '2023-04-11 18:46:42', 'superAdmin', '2023-04-11 18:46:42', 'security_menu', 1645740146187542529, 'name', '在线用户', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645740292031881217, 'superAdmin', '2023-04-11 18:47:17', 'superAdmin', '2023-04-11 18:47:17', 'security_menu', 1645740146187542529, 'name', 'Online user', 'en_US');
INSERT INTO `locale_name` VALUES (1645740925812187137, 'superAdmin', '2023-04-11 18:49:48', 'superAdmin', '2023-04-11 18:49:48', 'security_menu', 1645740925552140289, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645741285826076674, 'superAdmin', '2023-04-11 18:51:14', 'superAdmin', '2023-04-11 18:51:14', 'security_menu', 1645740925552140289, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1645742459056791553, 'superAdmin', '2023-04-11 18:55:53', 'superAdmin', '2023-04-11 18:55:53', 'security_menu', 1645742458788356097, 'name', '系统信息', 'zh_CN');
INSERT INTO `locale_name` VALUES (1645742842944659458, 'superAdmin', '2023-04-11 18:57:25', 'superAdmin', '2023-04-11 18:57:25', 'security_menu', 1645742458788356097, 'name', 'System information', 'en_US');
INSERT INTO `locale_name` VALUES (1649738171943682050, 'superAdmin', '2023-04-22 19:33:26', 'superAdmin', '2023-04-22 19:33:26', 'security_menu', 1649738171612332034, 'name', '定时任务', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649738486747168770, 'superAdmin', '2023-04-22 19:34:41', 'superAdmin', '2023-04-22 19:34:41', 'security_menu', 1649738171612332034, 'name', 'Scheduled task', 'en_US');
INSERT INTO `locale_name` VALUES (1649739345157619713, 'superAdmin', '2023-04-22 19:38:05', 'superAdmin', '2023-04-22 19:38:05', 'security_menu', 1649739344822075394, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649739497155002369, 'superAdmin', '2023-04-22 19:38:41', 'superAdmin', '2023-04-22 19:38:41', 'security_menu', 1649739344822075394, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1649739992837849090, 'superAdmin', '2023-04-22 19:40:40', 'superAdmin', '2023-04-22 19:40:40', 'security_menu', 1649739992561025026, 'name', '新增', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649740039449149441, 'superAdmin', '2023-04-22 19:40:51', 'superAdmin', '2023-04-22 19:40:51', 'security_menu', 1649739992561025026, 'name', 'Add', 'en_US');
INSERT INTO `locale_name` VALUES (1649740221771350018, 'superAdmin', '2023-04-22 19:41:34', 'superAdmin', '2023-04-22 19:41:34', 'security_menu', 1649740221444194306, 'name', '修改', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649740263383040001, 'superAdmin', '2023-04-22 19:41:44', 'superAdmin', '2023-04-22 19:41:44', 'security_menu', 1649740221444194306, 'name', 'Update', 'en_US');
INSERT INTO `locale_name` VALUES (1649740418970746881, 'superAdmin', '2023-04-22 19:42:21', 'superAdmin', '2023-04-22 19:42:21', 'security_menu', 1649740418698117122, 'name', '删除', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649740452105748482, 'superAdmin', '2023-04-22 19:42:29', 'superAdmin', '2023-04-22 19:42:29', 'security_menu', 1649740418698117122, 'name', 'Delete', 'en_US');
INSERT INTO `locale_name` VALUES (1649740703466192898, 'superAdmin', '2023-04-22 19:43:29', 'superAdmin', '2023-04-22 19:43:29', 'security_menu', 1649740703201951745, 'name', '执行', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649740869485133825, 'superAdmin', '2023-04-22 19:44:09', 'superAdmin', '2023-04-22 19:44:09', 'security_menu', 1649740703201951745, 'name', 'Execute', 'en_US');
INSERT INTO `locale_name` VALUES (1649741035701207042, 'superAdmin', '2023-04-22 19:44:48', 'superAdmin', '2023-04-22 19:44:48', 'security_menu', 1649741035432771585, 'name', '暂停', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649741359337897985, 'superAdmin', '2023-04-22 19:46:05', 'superAdmin', '2023-04-22 19:46:05', 'security_menu', 1649741035432771585, 'name', 'Pause', 'en_US');
INSERT INTO `locale_name` VALUES (1649741628339585025, 'superAdmin', '2023-04-22 19:47:10', 'superAdmin', '2023-04-22 19:47:10', 'security_menu', 1649741628071149570, 'name', '恢复', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649741663051644929, 'superAdmin', '2023-04-22 19:47:18', 'superAdmin', '2023-04-22 19:47:18', 'security_menu', 1649741628071149570, 'name', 'Resume', 'en_US');
INSERT INTO `locale_name` VALUES (1649750394359447553, 'superAdmin', '2023-04-22 20:22:00', 'superAdmin', '2023-04-22 20:22:00', 'security_menu', 1649750393847742465, 'name', '定时任务日志', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649750630742032385, 'superAdmin', '2023-04-22 20:22:56', 'superAdmin', '2023-04-22 20:22:56', 'security_menu', 1649750393847742465, 'name', 'Scheduled task log', 'en_US');
INSERT INTO `locale_name` VALUES (1649751116744425474, 'superAdmin', '2023-04-22 20:24:52', 'superAdmin', '2023-04-22 20:24:52', 'security_menu', 1649751116480184322, 'name', '查看', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649751311762784258, 'superAdmin', '2023-04-22 20:25:38', 'superAdmin', '2023-04-22 20:25:38', 'security_menu', 1649751116480184322, 'name', 'View', 'en_US');
INSERT INTO `locale_name` VALUES (1649751676113584130, 'superAdmin', '2023-04-22 20:27:05', 'superAdmin', '2023-04-22 20:27:05', 'security_menu', 1649751675778039809, 'name', '导出', 'zh_CN');
INSERT INTO `locale_name` VALUES (1649751739472740354, 'superAdmin', '2023-04-22 20:27:20', 'superAdmin', '2023-04-22 20:27:20', 'security_menu', 1649751675778039809, 'name', 'Export', 'en_US');

-- ----------------------------
-- Records of schedule_task
-- ----------------------------
INSERT INTO `schedule_task` VALUES (1649246831678017538, 'superAdmin', '2023-04-21 11:01:01', 'superAdmin', '2023-04-21 11:01:01', 'logOperationClearTask', '', '0 0 1 * * ? *', 0, 1, '操作日志清理');
INSERT INTO `schedule_task` VALUES (1649247935891460098, 'superAdmin', '2023-04-21 11:05:24', 'superAdmin', '2023-04-21 11:05:24', 'logErrorClearTask', '', '0 30 1 * * ? *', 0, 1, '错误日志清理');
INSERT INTO `schedule_task` VALUES (1649248244256690177, 'superAdmin', '2023-04-21 11:06:38', 'superAdmin', '2023-04-21 11:06:38', 'logLoginClearTask', '', '0 0 2 * * ? *', 0, 1, '登录日志清理');
INSERT INTO `schedule_task` VALUES (1649248434837475330, 'superAdmin', '2023-04-21 11:07:23', 'superAdmin', '2023-04-21 11:07:23', 'logTaskClearTask', '', '0 30 2 * * ? *', 0, 1, '任务日志清理');
INSERT INTO `schedule_task` VALUES (1649250094074777602, 'superAdmin', '2023-04-21 11:13:59', 'superAdmin', '2023-04-21 11:13:59', 'apiDemoTask', '{\"url\": \"http://xxx\"}', '0/20 * * * * ? *', 1, 1, 'ApiDemo');
INSERT INTO `schedule_task` VALUES (1649250416746778626, 'superAdmin', '2023-04-21 11:15:16', 'superAdmin', '2023-04-21 11:15:16', 'businessDemoTask', '{\"test\": \"xxx\"}', '5/20 * * * * ? *', 2, 1, 'BusinessDemo');

SET FOREIGN_KEY_CHECKS = 1;