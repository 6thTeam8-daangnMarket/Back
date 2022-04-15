package com.sparta.clone_backend.controller;

import com.sparta.clone_backend.model.Post;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class PostController {

    @PostMapping("/api/posts")
    public List<Post> getPost() {
        System.out.println(userId);
        return postService.getPost(userId);
    }
}
