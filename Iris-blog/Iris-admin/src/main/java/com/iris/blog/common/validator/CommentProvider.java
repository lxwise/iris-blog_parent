package com.iris.blog.common.validator;

import com.iris.blog.common.validator.groups.ArticleTalkGroup;
import com.iris.blog.common.validator.groups.LinkGroup;
import com.iris.blog.common.validator.groups.ParentIdNotNullGroup;
import com.iris.blog.common.validator.groups.ParentIdNullGroup;
import com.iris.blog.domain.dto.app.AppCommentDTO;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.iris.blog.common.enums.CommentTypeEnum.*;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 评论分组校验器
 */
public class CommentProvider implements DefaultGroupSequenceProvider<AppCommentDTO> {
    @Override
    public List<Class<?>> getValidationGroups(AppCommentDTO commentDTO) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(AppCommentDTO.class);
        if (commentDTO != null) {
            if (commentDTO.getCommentType().equals(ARTICLE.getType()) || commentDTO.getCommentType().equals(TALK.getType())) {
                defaultGroupSequence.add(ArticleTalkGroup.class);
            }
            if (commentDTO.getCommentType().equals(LINK.getType())) {
                defaultGroupSequence.add(LinkGroup.class);
            }
            if (Objects.isNull(commentDTO.getParentId())) {
                defaultGroupSequence.add(ParentIdNullGroup.class);
            }
            if (Objects.nonNull(commentDTO.getParentId())) {
                defaultGroupSequence.add(ParentIdNotNullGroup.class);
            }
        }
        return defaultGroupSequence;
    }
}