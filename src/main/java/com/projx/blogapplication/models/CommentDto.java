package com.projx.blogapplication.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public record CommentDto(
        Long id,
         String comment,
         String user,
         Date createdOn
) {

}