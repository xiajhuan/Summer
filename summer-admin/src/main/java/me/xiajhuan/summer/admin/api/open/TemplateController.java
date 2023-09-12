package me.xiajhuan.summer.admin.api.open;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import me.xiajhuan.summer.core.exception.custom.FileDownloadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 模板 OpenApi
 *
 * @author xiajhuan
 * @date 2023/9/7
 */
@RestController
@RequestMapping("api/open/template")
public class TemplateController {

    /**
     * 临时目录
     */
    private static final String TMP_DIR = FileUtil.getTmpDirPath();

    /**
     * 模板位置
     */
    @Value("${template.location}")
    private String location;

    /**
     * 填充Excel模板
     *
     * @param json     Json格式参数
     * @param response {@link HttpServletResponse}
     */
    @PostMapping("fillExcel")
    public void fillExcel(@RequestBody String json, HttpServletResponse response) {
        if (JSONUtil.isTypeJSON(json)) {
            fillExcelInternal(json, response);
        }
    }

    /**
     * 填充Excel模板
     *
     * @param json     Json格式参数
     * @param response {@link HttpServletResponse}
     */
    private void fillExcelInternal(String json, HttpServletResponse response) {
        JSONObject jsonObject = JSONUtil.parseObj(json);

        // 模板
        String templateName = jsonObject.getStr("template");
        String template = StrUtil.format("{}{}{}.xlsx", location, File.separator, templateName);

        // 要填充的数据
        List<Dict> list = jsonObject.getJSONArray("data").toList(Dict.class);

        response.setCharacterEncoding("UTF-8");
        if (list.size() > 1) {
            // 多文件，压缩后返回.zip
            File[] files = new File[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Dict data = list.get(i);
                // 临时文件（待压缩）
                String fileName = StrUtil.format("{}{}{}-{}.xlsx",
                        TMP_DIR, File.separator, templateName, UUID.fastUUID().toString(true));
                files[i] = FileUtil.file(fileName);
                EasyExcel.write(fileName).withTemplate(template).sheet().doFill(data);
            }

            response.setHeader("Content-disposition",
                    StrUtil.format("attachment;filename={}-{}.zip",
                            templateName, UUID.fastUUID().toString(true)));
            try {
                ZipUtil.zip(response.getOutputStream(), StandardCharsets.UTF_8,
                        false, i -> true, files);
            } catch (IOException e) {
                throw FileDownloadException.of(e, "文件下载失败");
            }
        } else if (list.size() == 1) {
            // 单文件，直接返回.xlsx
            Dict data = list.get(0);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition",
                    StrUtil.format("attachment;filename={}-{}.xlsx",
                            templateName, UUID.fastUUID().toString(true)));
            try {
                EasyExcel.write(response.getOutputStream())
                        .withTemplate(template).sheet().doFill(data);
            } catch (IOException e) {
                throw FileDownloadException.of(e, "文件下载失败");
            }
        }
    }

}
