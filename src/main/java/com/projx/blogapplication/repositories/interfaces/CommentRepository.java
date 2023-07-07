package com.projx.blogapplication.repositories.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projx.blogapplication.models.CommentDto;
import com.projx.blogapplication.models.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findCommentsByBlog_IdAndDeletedFalse(PageRequest pageRequest, Long blogId);

}
