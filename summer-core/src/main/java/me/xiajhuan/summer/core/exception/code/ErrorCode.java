/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * Summer is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.core.exception.code;

/**
 * 错误编码
 * <p>
 * 编码定义规则：由5位数字组成，前2位为模块编码，后3位为业务编码，
 * 例如：10004，其中10代表权限管理模块，004代表具体业务
 * </p>
 *
 * @author xiajhuan
 * @date 2022/11/19
 */
public interface ErrorCode {

    //*******************fail********************

    /**
     * 未授权，请先登录
     */
    int UNAUTHORIZED = 401;

    /**
     * 拒绝访问，没有权限
     */
    int FORBIDDEN = 403;

    /**
     * 服务器内部异常
     */
    int INTERNAL_SERVER_ERROR = 500;

    //*******************common********************

    /**
     * 操作失败
     */
    int OPERATION_FAILURE = 1000;

    /**
     * 服务器繁忙，请稍后再试~
     */
    int SERVER_BUSY = 1001;

    /**
     * 您的操作太频繁了，请稍后再试~
     */
    int FREQUENT_OPERATION = 1002;

    /**
     * 不支持的参数类型
     */
    int UNSUPPORTED_CONTENT_TYPE = 1003;

    /**
     * 不支持的文件类型：{0}
     */
    int UNSUPPORTED_FILE_TYPE = 1004;

    /**
     * {0}文件不能超过{1}
     */
    int FILE_TYPE_EXCEED = 1005;

    /**
     * 文件上传失败
     */
    int FILE_UPLOAD_FAILURE = 1006;

    /**
     * 文件下载失败
     */
    int FILE_DOWNLOAD_FAILURE = 1007;

    /**
     * 文件删除失败【{0}】
     */
    int FILE_DELETE_FAILURE = 1008;

    /**
     * 没有开启验证码校验功能
     */
    int CAPTCHA_DISABLE = 1009;

    /**
     * 验证码获取失败
     */
    int CAPTCHA_GET_FAILURE = 1010;

    /**
     * Excel导入失败，第{0}行：
     */
    int EXCEL_IMPORT_FAILURE_PREFIX = 1011;

    /**
     * Excel模板下载失败
     */
    int EXCEL_TEMPLATE_DOWNLOAD_FAILURE = 1012;

    /**
     * Excel导出失败
     */
    int EXCEL_EXPORT_FAILURE = 1013;

    /**
     * 最多导出{0}条记录
     */
    int EXCEL_EXPORT_MAXIMUM_LIMIT = 1014;

    /**
     * 不能包含非法字符
     */
    int INVALID_SYMBOL = 1015;

    /**
     * {0}不能为空
     */
    int NOT_NULL = 1016;

    /**
     * 定时任务异常【{0}】
     */
    int SCHEDULE_ERROR = 1017;

    //*******************system********************

    /**
     * Token失效，请重新登录
     */
    int TOKEN_INVALID = 10000;

    /**
     * 验证码不能为空
     */
    int CAPTCHA_NOT_NULL = 10001;

    /**
     * 验证码不正确
     */
    int CAPTCHA_ERROR = 10002;

    /**
     * 账号或密码错误
     */
    int ACCOUNT_PASSWORD_ERROR = 10003;

    /**
     * 账号已停用
     */
    int ACCOUNT_DISABLE = 10004;

    /**
     * 角色【{0}】已存在
     */
    int ROLE_EXISTS = 10005;

    /**
     * 用户【{0}】已存在
     */
    int USER_EXISTS = 10006;

    /**
     * 原密码不正确
     */
    int OLD_PASSWORD_ERROR = 10007;

    /**
     * 密码和确认密码不一致
     */
    int PASSWORD_CONFIRM_ERROR = 10008;

    /**
     * 只有超级管理员可以重置密码
     */
    int PASSWORD_RESET_ERROR = 10009;

    /**
     * 岗位【{0}】已存在
     */
    int POST_EXISTS = 10010;

    /**
     * 上级菜单不能为自身
     */
    int SUPERIOR_MENU_ERROR = 10011;

    /**
     * 请先删除下级菜单或按钮
     */
    int MENU_SUB_DELETE_ERROR = 10012;

    /**
     * 上级部门选择错误
     */
    int SUPERIOR_DEPT_ERROR = 10013;

    /**
     * 请先删除下级部门
     */
    int DEPT_SUB_DELETE_ERROR = 10014;

    /**
     * 请先删除部门下用户
     */
    int DEPT_USER_DELETE_ERROR = 10015;

    /**
     * 行ID【{0}】+地区语言【{1}】已存在
     */
    int LINE_ID_AND_LOCALE_EXISTS = 11000;

    /**
     * 只有超级管理员可以踢出用户
     */
    int KICK_OUT_ERROR = 12000;

    /**
     * Cron表达式格式错误
     */
    int CRON_EXPRESSION_ERROR = 13000;

    /**
     * Bean名称【{0}】已存在
     */
    int BEAN_NAME_EXISTS = 13001;

    /**
     * 操作失败，定时任务未启动
     */
    int NOT_STARTED_ERROR = 13002;

    /**
     * 检查定时任务启动状态失败
     */
    int CHECK_STARTUP_FAILURE = 13003;

    /**
     * 只有超级管理员可以手动启动定时任务
     */
    int MANUAL_START_ERROR = 13004;

    /**
     * 定时任务已启动，请勿重复操作
     */
    int REPEAT_START_ERROR = 13005;

    /**
     * 手动启动定时任务失败【{0}】
     */
    int MANUAL_START_FAILURE = 13006;

    /**
     * 字典【{0}】已存在
     */
    int DICTIONARY_EXISTS = 14000;

    /**
     * 值【{0}】已存在
     */
    int VALUE_EXISTS = 14001;

    /**
     * 类别ID不能为空
     */
    int CATEGORY_ID_NOT_NULL = 14002;

    /**
     * 上级区域级别必须比自身大一级
     */
    int SUPERIOR_REGION_LEVEL_ERROR = 15000;

    /**
     * 请先删除下级区域
     */
    int REGION_SUB_DELETE_ERROR = 15001;

    /**
     * 邮件【{0}】已存在
     */
    int MAIL_EXISTS = 16000;

    //*******************business********************

}
