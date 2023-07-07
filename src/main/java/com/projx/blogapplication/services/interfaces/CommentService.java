package com.projx.blogapplication.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.projx.blogapplication.models.CommentDto;
import com.projx.blogapplication.models.entities.Comment;
import com.projx.blogapplication.models.payloads.CommentRequest;

public interface CommentService {

    Page<Comment> findAllByBlogId(PageRequest pageRequest, Long blogId);
    void createComment(CommentRequest commentRequest);
    void deleteById(Long commentId);

}
