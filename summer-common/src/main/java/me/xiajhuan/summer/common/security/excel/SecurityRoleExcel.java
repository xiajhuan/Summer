package me.xiajhuan.summer.common.security.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.xiajhuan.summer.common.constant.DateFormatConst;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色 Excel
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class SecurityRoleExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @ExcelProperty(value = "角色名称", index = 0)
    private String name;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述", index = 1)
    private String desc;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 2)
    @DateTimeFormat(DateFormatConst.DATE_TIME_PATTERN)
    private Date createTime;

}
