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

package me.xiajhuan.summer.system.message.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.enums.OperationStatusEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.enums.MailContentTypeEnum;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.log.entity.LogMailEntity;
import me.xiajhuan.summer.system.log.service.LogMailService;
import me.xiajhuan.summer.system.message.dto.MessageMailDto;
import me.xiajhuan.summer.system.message.dto.SendMailDto;
import me.xiajhuan.summer.system.message.entity.MessageMailEntity;
import me.xiajhuan.summer.system.message.mapper.MessageMailMapper;
import me.xiajhuan.summer.system.message.service.MessageMailService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 邮件 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/5/9
 * @see TemplateEngine
 * @see MailAccount
 * @see MailUtil
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class MessageMailServiceImpl extends ServiceImpl<MessageMailMapper, MessageMailEntity> implements MessageMailService, MpHelper<MessageMailDto, MessageMailEntity> {

    private static final Log LOGGER = LogFactory.get();

    @Resource(name = SettingConst.CORE)
    private Setting coreSetting;

    @Resource(name = SettingConst.SYSTEM)
    private Setting systemSetting;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private MailAccount mailAccount;

    @Resource
    private LogMailService logMailService;

    /**
     * 临时文件路径（绝对路径）
     */
    private final String tmpDir = FileUtil.getTmpDirPath();

    /**
     * 发件人
     */
    private String sender;

    /**
     * 邮件正文最大长度
     */
    private int contentMaxLength;

    /**
     * 异常堆栈最大长度
     */
    private int stacktraceMaxLength;

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        sender = coreSetting.getByGroup("sender", "Mail");
        if (StrUtil.isBlank(sender)) {
            LOGGER.warn("core.setting中没有配置发件人");
        }
        contentMaxLength = systemSetting.getInt("mail.content-length", "Log", 65535);
        stacktraceMaxLength = systemSetting.getInt("mail.stacktrace-length", "Log", 65535);
    }

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<MessageMailEntity> getQueryWrapper(MessageMailDto dto) {
        LambdaQueryWrapper<MessageMailEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 邮件名称
        String name = dto.getName();
        queryWrapper.eq(StrUtil.isNotBlank(name), MessageMailEntity::getName, name);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<MessageMailEntity> getSelectWrapper(MessageMailDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<MessageMailEntity> addSelectField(LambdaQueryWrapper<MessageMailEntity> queryWrapper) {
        return queryWrapper.select(MessageMailEntity::getId, MessageMailEntity::getName, MessageMailEntity::getSubject,
                MessageMailEntity::getContent, MessageMailEntity::getContentType, MessageMailEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(MessageMailDto dto) {
        // 邮件名称不能重复
        String name = dto.getName();
        if (baseMapper.exist(name) != null) {
            throw ValidationException.of(ErrorCode.MAIL_EXISTS, name);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<MessageMailDto> page(MessageMailDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), MessageMailDto.class);
    }

    @Override
    public MessageMailDto getById(Long id) {
        LambdaQueryWrapper<MessageMailEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(MessageMailEntity::getId, id);

        return BeanUtil.convert(getOne(addSelectField(queryWrapper)), MessageMailDto.class);
    }

    @Override
    public void add(MessageMailDto dto) {
        handleDtoBefore(dto);

        save(BeanUtil.convert(dto, MessageMailEntity.class));
    }

    @Override
    public void update(MessageMailDto dto) {
        updateById(BeanUtil.convert(dto, MessageMailEntity.class));
    }

    @Override
    public void delete(Long[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean send(SendMailDto dto, MultipartFile[] files) {
        // 参数
        Map<String, String> params = null;
        String json = dto.getJson();
        if (StrUtil.isNotBlank(json)) {
            try {
                params = JSONUtil.toBean(json, Map.class);
            } catch (RuntimeException e) {
                throw SystemException.of(e, ErrorCode.JSON_PARSE_ERROR, e.getMessage());
            }
        }

        long mailId = dto.getMailId();
        LambdaQueryWrapper<MessageMailEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(MessageMailEntity::getId, mailId);
        queryWrapper.select(MessageMailEntity::getName, MessageMailEntity::getSubject, MessageMailEntity::getContent,
                MessageMailEntity::getContentType);
        MessageMailEntity entity = getOne(queryWrapper);
        // 邮件不存在
        if (entity == null) {
            throw ValidationException.of(ErrorCode.MAIL_NOT_EXISTS, String.valueOf(mailId));
        }

        // 邮件正文
        String content = entity.getContent();
        if (MapUtil.isNotEmpty(params)) {
            try {
                // 渲染邮件正文
                content = templateEngine.getTemplate(content).render(params);
            } catch (RuntimeException e) {
                throw SystemException.of(e, ErrorCode.PARAMS_NOT_MATCHES_CONTENT);
            }
        }

        // 抄送人集合
        Set<String> ccSet = null;
        String receiversCc = dto.getReceiversCc();
        if (StrUtil.isNotBlank(receiversCc)) {
            ccSet = Arrays.stream(receiversCc.split(StrPool.COMMA)).collect(Collectors.toSet());
        }
        // 密送人集合
        Set<String> bccSet = null;
        String receiversBcc = dto.getReceiversBcc();
        if (StrUtil.isNotBlank(receiversBcc)) {
            bccSet = Arrays.stream(receiversBcc.split(StrPool.COMMA)).collect(Collectors.toSet());
        }

        // 附件
        File[] fileArray = null;
        if (files != null) {
            fileArray = Arrays.stream(files).filter(file -> {
                if (file.isEmpty()) {
                    // 空文件
                    LOGGER.warn("不允许上传空文件【{}】", file.getOriginalFilename());
                    return false;
                }
                return true;
            }).map(file -> {
                try {
                    // 将流的内容写入文件（自动关闭输入流）
                    return FileUtil.writeFromStream(file.getInputStream(),
                            tmpDir + File.separator + file.getOriginalFilename());
                } catch (IOException e) {
                    throw FileUploadException.of(e);
                }
            }).toArray(File[]::new);
        }

        // 发送处理
        return sendInternal(entity.getName(), entity.getSubject(), content, entity.getContentType(),
                Arrays.stream(dto.getReceiversTo().split(StrPool.COMMA)).collect(Collectors.toSet()), ccSet, bccSet, fileArray);
    }

    /**
     * 发送处理
     *
     * @param name        邮件名称
     * @param subject     邮件标题
     * @param content     邮件正文
     * @param contentType 正文类型，参考{@link MailContentTypeEnum}
     * @param toSet       收件人集合
     * @param ccSet       抄送人集合
     * @param bccSet      密送人集合
     * @param files       附件
     * @return 是否发送成功，true：是 false：否
     */
    private boolean sendInternal(String name, String subject, String content, int contentType, Set<String> toSet, Set<String> ccSet, Set<String> bccSet, File... files) {
        // 构建邮件日志
        LogMailEntity entity = LogMailEntity.builder()
                .mailName(name)
                .sender(sender)
                .receiversTo(toSet.toString())
                .receiversCc(ccSet != null ? ccSet.toString() : null)
                .receiversBcc(bccSet != null ? bccSet.toString() : null)
                .subject(subject)
                .content(content.length() > contentMaxLength ?
                        content.substring(0, contentMaxLength) : content).build();
        try {
            // 发送邮件
            MailUtil.send(mailAccount, toSet, ccSet, bccSet, subject, content,
                    MailContentTypeEnum.HTML.getValue() == contentType, files);
            // 发送成功
            entity.setStatus(OperationStatusEnum.SUCCESS.getValue());

            return true;
        } catch (RuntimeException e) {
            // 发送失败
            entity.setStatus(OperationStatusEnum.FAIL.getValue());
            entity.setErrorInfo(ExceptionUtil.stacktraceToString(e, stacktraceMaxLength));

            return false;
        } finally {
            // 异步保存邮件日志
            logMailService.saveAsync(entity);

            // 删除临时文件
            if (ArrayUtil.isNotEmpty(files)) {
                Arrays.stream(files).forEach(file -> {
                    try {
                        if (!FileUtil.del(file)) {
                            LOGGER.warn("临时文件【{}】删除失败", file.getName());
                        }
                    } catch (IORuntimeException e) {
                        throw SystemException.of(e, ErrorCode.FILE_DELETE_FAILURE, e.getMessage());
                    }
                });
            }
        }
    }

}
