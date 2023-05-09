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

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.log.service.LogMailService;
import me.xiajhuan.summer.system.message.dto.MessageMailDto;
import me.xiajhuan.summer.system.message.dto.SendMailDto;
import me.xiajhuan.summer.system.message.entity.MessageMailEntity;
import me.xiajhuan.summer.system.message.mapper.MessageMailMapper;
import me.xiajhuan.summer.system.message.service.MessageMailService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 邮件 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/5/9
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class MessageMailServiceImpl extends ServiceImpl<MessageMailMapper, MessageMailEntity> implements MessageMailService, MpHelper<MessageMailDto, MessageMailEntity> {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 临时文件路径（绝对路径）
     */
    private final String tmpDir = FileUtil.getTmpDirPath();

    @Resource
    private MailAccount mailAccount;

    @Resource
    private LogMailService logMailService;

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
    public void send(SendMailDto dto, MultipartFile[] files) {
        LambdaQueryWrapper<MessageMailEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(MessageMailEntity::getId, dto.getMailId());
        queryWrapper.select(MessageMailEntity::getSubject, MessageMailEntity::getContent);
        MessageMailEntity entity = getOne(queryWrapper);
        // 邮件不存在
        if (entity == null) {
            throw ValidationException.of(ErrorCode.MAIL_NOT_EXISTS, String.valueOf(dto.getMailId()));
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
                String name = file.getOriginalFilename();
                try {
                    // 将流的内容写入文件（自动关闭输入流）
                    return FileUtil.writeFromStream(file.getInputStream(),
                            tmpDir + File.separator + name);
                } catch (IOException e) {
                    throw FileUploadException.of(e);
                }
            }).toArray(File[]::new);
        }

        // 发送处理
        sendInternal(entity.getSubject(), entity.getContent(), dto.getJson(), dto.getReceiversTo(), dto.getReceiversCc(), dto.getReceiversBcc(), fileArray);
    }

    /**
     * 发送处理
     *
     * @param subject      邮件标题
     * @param content      邮件正文
     * @param json         参数（Json格式）
     * @param receiversTo  收件人，多个以“,”分隔
     * @param receiversCc  抄送人，多个以“,”分隔
     * @param receiversBcc 密送人，多个以“,”分隔
     * @param files        附件
     */
    private void sendInternal(String subject, String content, String json, String receiversTo, String receiversCc, String receiversBcc, File... files) {
        // TODO

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
