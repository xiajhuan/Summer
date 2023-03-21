/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.core.exception;

/**
 * common和business模块的错误编码，由5位数字组成，前2位为模块编码，后3位为业务编码<br>
 * 如：10004（10代表权限相关模块，004代表具体业务）
 *
 * @author xiajhuan
 * @date 2022/11/19
 */
public interface ErrorCode {

    //*******************system********************

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

    //*******************custom********************

    /**
     * 不支持的参数类型
     */
    int UNSUPPORTED_CONTENT_TYPE = 1000;

    /**
     * 操作失败
     */
    int OPERATION_FAILURE = 1001;

    /**
     * 服务器繁忙，请稍后再试~
     */
    int SERVER_BUSY = 1002;

    /**
     * 文件下载失败
     */
    int FILE_DOWNLOAD_FAILURE = 1003;

    /**
     * Excel模板下载失败
     */
    int EXCEL_TEMPLATE_DOWNLOAD_FAILURE = 1004;

    /**
     * Excel导出失败
     */
    int EXCEL_EXPORT_FAILURE = 1005;

    //*******************common********************

    /**
     * {0}不能为空
     */
    int NOT_NULL = 10001;

    /**
     * 数据库中已存在该记录
     */
    int DB_RECORD_EXISTS = 10002;

    /**
     * 获取参数失败
     */
    int PARAMS_GET_ERROR = 10003;

    /**
     * 账号或密码错误
     */
    int ACCOUNT_PASSWORD_ERROR = 10004;

    /**
     * 唯一标识不能为空
     */
    int IDENTIFIER_NOT_NULL = 10006;

    /**
     * 验证码不正确
     */
    int CAPTCHA_ERROR = 10007;

    /**
     * 先删除子菜单或按钮
     */
    int SUB_MENU_EXIST = 10008;

    /**
     * 原密码不正确
     */
    int PASSWORD_ERROR = 10009;

    /**
     * 账号不存在
     */
    int ACCOUNT_NOT_EXIST = 10010;

    /**
     * 上级部门选择错误
     */
    int SUPERIOR_DEPT_ERROR = 10011;

    /**
     * 上级菜单不能为自身
     */
    int SUPERIOR_MENU_ERROR = 10012;

    /**
     * 数据权限接口，只能是BaseDto类型参数
     */
    int DATA_SCOPE_PARAMS_ERROR = 10013;

    /**
     * 请先删除下级部门
     */
    int DEPT_SUB_DELETE_ERROR = 10014;

    /**
     * 请先删除部门下的用户
     */
    int DEPT_USER_DELETE_ERROR = 10015;

    /**
     * 部署失败，没有流程
     */
    int ACT_DEPLOY_ERROR = 10016;

    /**
     * 模型图不正确，请检查
     */
    int ACT_MODEL_IMG_ERROR = 10017;

    /**
     * 导出失败，模型ID为{0}
     */
    int ACT_MODEL_EXPORT_ERROR = 10018;

    /**
     * 请上传文件
     */
    int UPLOAD_FILE_EMPTY = 10019;

    /**
     * Token不能为空
     */
    int TOKEN_NOT_EMPTY = 10020;

    /**
     * Token失效，请重新登录
     */
    int TOKEN_INVALID = 10021;

    /**
     * 账号已停用
     */
    int ACCOUNT_DISABLE = 10022;

    /**
     * 请上传zip、bar、bpmn、bpmn20.xml格式文件
     */
    int ACT_DEPLOY_FORMAT_ERROR = 10023;

    /**
     * 上传文件失败{0}
     */
    int OSS_UPLOAD_FILE_ERROR = 10024;

    /**
     * 发送短信失败{0}
     */
    int SEND_SMS_ERROR = 10025;

    /**
     * 邮件模板不存在
     */
    int MAIL_TEMPLATE_NOT_EXISTS = 10026;

    /**
     * Redis服务异常
     */
    int REDIS_ERROR = 10027;

    /**
     * 定时任务失败
     */
    int JOB_ERROR = 10028;

    /**
     * 不能包含非法字符
     */
    int INVALID_SYMBOL = 10029;

    /**
     * 参数格式不正确，请使用JSON格式
     */
    int JSON_FORMAT_ERROR = 10030;

    /**
     * 请先完成短信配置
     */
    int SMS_CONFIG = 10031;

    /**
     * 账号已存在
     */
    int ACCOUNT_EXIST = 10200;

    /**
     * 任务已被签收，操作失败
     */
    int TASK_CLIME_FAIL = 10032;

    /**
     * 不存在的流程定义
     */
    int NONE_EXIST_PROCESS = 10033;

    /**
     * 上级节点不存在
     */
    int SUPERIOR_NOT_EXIST = 10034;

    /**
     * 驳回
     */
    int REJECT_MESSAGE = 10035;

    /**
     * 回退
     */
    int ROLLBACK_MESSAGE = 10036;

    /**
     * 任务没有分组，无法取消认领
     */
    int UNCLAIM_ERROR_MESSAGE = 10037;

    /**
     * 上级区域选择错误
     */
    int SUPERIOR_REGION_ERROR = 10038;

    /**
     * 请先删除下级区域
     */
    int REGION_SUB_DELETE_ERROR = 10039;

    /**
     * 流程已挂起，不能启动实例
     */
    int PROCESS_START_ERROR = 10040;

    /**
     * 多实例任务不能驳回
     */
    int REJECT_PROCESS_PARALLEL_ERROR = 10041;

    /**
     * 存在多个处理中的任务，不能驳回
     */
    int REJECT_PROCESS_HANDLEING_ERROR = 10042;

    /**
     * 多实例任务不能终止
     */
    int END_PROCESS_PARALLEL_ERROR = 10043;

    /**
     * 终止
     */
    int END_PROCESS_HANDLEING_ERROR = 10044;

    /**
     * 多实例任务不能回退
     */
    int END_PROCESS_MESSAGE = 10045;

    /**
     * 存在多个并行执行的任务，不能回退
     */
    int BACK_PROCESS_PARALLEL_ERROR = 10046;

    /**
     * 存在多个处理中的任务，不能终止流程
     */
    int BACK_PROCESS_HANDLEING_ERROR = 10047;

    //*******************business********************

}
