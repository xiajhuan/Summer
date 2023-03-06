package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作分组枚举
 *
 * @author xiajhuan
 * @date 2022/12/3
 */
@Getter
@AllArgsConstructor
public enum OperationGroupEnum {

    /**
     * 通用CRUD，包含：分页/列表/新增/修改/删除
     */
    COMMON_CRUD(0, "Common Crud"),

    /**
     * Excel操作，包含：Excel模板下载/Excel导入/Excel导出
     */
    EXCEL_OPERATION(1, "Excel Operation"),

    /**
     * 其他操作
     */
    OTHER_OPERATION(2, "Other Operation");

    private Integer value;

    private String desc;

}