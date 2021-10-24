package com.yly.EntityToDtoConversion.service;
;

import com.yly.EntityToDtoConversion.model.Post;

import java.util.List;

public interface IPostService {

    List<Post> getPostsList(int page, int size, String sortDir, String sort);

    void updatePost(Post post);

    Post createPost(Post post);

    Post getPostById(Long id);

}
