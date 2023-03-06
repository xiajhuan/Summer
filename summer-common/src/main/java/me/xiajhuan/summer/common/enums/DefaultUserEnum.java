package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 默认用户枚举
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Getter
@AllArgsConstructor
public enum DefaultUserEnum {

    /**
     * 系统用户
     */
    SYSTEM_USER("systemUser", "系统用户"),

    /**
     * 定时任务
     */
    TASK_JOB("taskJob", "定时任务"),

    /**
     * 第三方用户
     */
    THIRD_PART("thirdPart", "第三方用户");

    private String value;

    private String name;

}
