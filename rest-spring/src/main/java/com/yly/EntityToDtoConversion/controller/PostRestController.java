package com.yly.EntityToDtoConversion.controller;

import com.yly.EntityToDtoConversion.dto.PostDto;

import com.yly.EntityToDtoConversion.model.Post;
import com.yly.EntityToDtoConversion.service.IPostService;
import com.yly.EntityToDtoConversion.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostRestController {

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseBody
    public List<PostDto> getPosts(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort) {

        List<Post> posts = postService.getPostsList(page, size, sortDir, sort);
        return posts.stream()
          .map(this::convertToDto)
          .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PostDto createPost(@RequestBody PostDto postDto) throws ParseException {
        Post post = convertToEntity(postDto);
        Post postCreated = postService.createPost(post);
        return convertToDto(postCreated);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public PostDto getPost(@PathVariable("id") Long id) {
        return convertToDto(postService.getPostById(id));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@RequestBody PostDto postDto) throws ParseException {
        Post post = convertToEntity(postDto);
        postService.updatePost(post);
    }

    // 从Post实体到PostDto 的转换：
    private PostDto convertToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setSubmissionDate(post.getSubmissionDate(),
            userService.getCurrentUser().getPreference().getTimezone());
        return postDto;
    }

    // 从 DTO 到实体的转换
    // 在更新时，你必须做一次主读，以获得一个上下文实体对象，并使用你的DTO更新该实体，然后合并。
    // 记住，你需要一个ID或其他唯一的属性来做主读。所以DTO是为了确保客户拥有它所需要的东西。
    // 如果说你在一个实体中拥有20个属性，而你只需要其中的5个，那么就为这些属性创建一个DTO。
    private Post convertToEntity(PostDto postDto) throws ParseException {
        Post post = modelMapper.map(postDto, Post.class);
        post.setSubmissionDate(postDto.getSubmissionDateConverted(
          userService.getCurrentUser().getPreference().getTimezone()));

        if (postDto.getId() != null) {
            Post oldPost = postService.getPostById(postDto.getId());
            post.setRedditID(oldPost.getRedditID());
            post.setSent(oldPost.isSent());
        }
        return post;
    }

    // 正如我们所见，在模型映射器的帮助下，转换逻辑既快速又简单。我们使用映射器的映射API，无需编写一行转换逻辑即可转换数据。
}
