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
 * 例如：10004（10代表权限相关模块，004代表具体业务）
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
     * 验证码获取失败
     */
    int CAPTCHA_GET_FAILURE = 1008;

    /**
     * Excel导入失败，第{0}行：
     */
    int EXCEL_IMPORT_FAILURE_PREFIX = 1009;

    /**
     * Excel模板下载失败
     */
    int EXCEL_TEMPLATE_DOWNLOAD_FAILURE = 1010;

    /**
     * Excel导出失败
     */
    int EXCEL_EXPORT_FAILURE = 1011;

    /**
     * 最多导出{0}条记录
     */
    int EXCEL_EXPORT_MAXIMUM_LIMIT = 1012;

    /**
     * 不能包含非法字符
     */
    int INVALID_SYMBOL = 1013;

    /**
     * {0}不能为空
     */
    int NOT_NULL = 1014;

    /**
     * 业务定时任务异常【{0}】
     */
    int BUSINESS_TASK_ERROR = 1015;

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
     * 请先删除部门下的用户
     */
    int DEPT_USER_DELETE_ERROR = 10015;

    /**
     * 名称行ID【{0}】+地区语言【{1}】已存在
     */
    int NAME_LINE_ID_AND_LOCALE_EXISTS = 11000;

    /**
     * 只有超级管理员可以踢出用户
     */
    int KICK_OUT_ERROR = 12000;

    //*******************business********************

}
