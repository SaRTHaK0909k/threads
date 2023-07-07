package com.projx.blogapplication.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.projx.blogapplication.models.enums.Action;

@Entity
@Table(name = "blog_actions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogAction extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Action action;

}

