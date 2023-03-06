package me.xiajhuan.summer.common.data;

import lombok.Data;

import java.io.Serializable;

import me.xiajhuan.summer.common.constant.SortConst;

/**
 * 分页排序参数
 *
 * @author xiajhuan
 * @date 2022/11/21
 */
@Data
public class PageAndSort implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码，默认1
     */
    private int pageNum = 1;

    /**
     * 每页记录数，默认10
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String field;

    /**
     * 排序规则
     *
     * @see SortConst
     */
    private String order;

}
