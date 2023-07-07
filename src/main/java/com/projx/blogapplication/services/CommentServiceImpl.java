package com.projx.blogapplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.projx.blogapplication.exceptions.StatusException;
import com.projx.blogapplication.models.CommentDto;
import com.projx.blogapplication.models.entities.Blog;
import com.projx.blogapplication.models.entities.Comment;
import com.projx.blogapplication.models.entities.User;
import com.projx.blogapplication.models.payloads.CommentRequest;
import com.projx.blogapplication.repositories.interfaces.BlogRepository;
import com.projx.blogapplication.repositories.interfaces.CommentRepository;
import com.projx.blogapplication.repositories.interfaces.UserRepository;
import com.projx.blogapplication.services.interfaces.CommentService;
import com.projx.blogapplication.utils.Helper;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }

    @Override
    public Page<Comment> findAllByBlogId(PageRequest pageRequest, Long blogId) {
        log.info("Fetching comments for blog {}",blogId);
        Page<Comment> comments = commentRepository.findCommentsByBlog_IdAndDeletedFalse(pageRequest,blogId);
        return comments;
    }

    @Override
    public void createComment(CommentRequest commentRequest) {
        Blog blog = blogRepository.findById(commentRequest.getBlogId())
                .orElseThrow(
                        ()->new StatusException("Invalid blog.", HttpStatus.UNPROCESSABLE_ENTITY)
                );
        User user = Helper.getLoggedInUserDetails().getUser();
        Comment comment = Comment
                .builder()
                .comment(commentRequest.getComment())
                .blog(blog)
                .user(user)
                .build();
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
