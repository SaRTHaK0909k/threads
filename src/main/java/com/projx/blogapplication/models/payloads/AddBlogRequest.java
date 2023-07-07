package com.projx.blogapplication.models.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBlogRequest {

    private String title;
    private String content;
    private String thumbnail;
    private Long category;

}
