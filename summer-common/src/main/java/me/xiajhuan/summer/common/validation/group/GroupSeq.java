package me.xiajhuan.summer.common.validation.group;

import javax.validation.GroupSequence;

/**
 * 分组校验顺序<br>
 * 如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author xiajhuan
 * @date 2023/2/25
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface GroupSeq {
}
