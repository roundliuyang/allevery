//package com.yly.EntityToDtoConversion;
//
//
//import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
//
//
//import org.modelmapper.ModelMapper;
//
///**
// * 在本教程中，我们将处理Spring 应用程序的内部实体与发布回客户端的外部DTO（数据传输对象）之间需要发生的转换。
// * 让我们首先介绍我们将用来执行实体-DTO转换的主库ModelMapper。
// * 我们将在pom.xml 中需要这个依赖：
// * <dependency>
// *     <groupId>org.modelmapper</groupId>
// *     <artifactId>modelmapper</artifactId>
// *     <version>2.3.5</version>
// * </dependency>
// */
//public class PostDtoUnitTest {
//
//    private ModelMapper modelMapper = new ModelMapper();
//
//    @Test
//    public void whenConvertPostEntityToPostDto_thenCorrect() {
//        Post post = new Post();
//        post.setId(1L);
//        post.setTitle(randomAlphabetic(6));
//        post.setUrl("www.test.com");
//
//        PostDto postDto = modelMapper.map(post, PostDto.class);
//        assertEquals(post.getId(), postDto.getId());
//        assertEquals(post.getTitle(), postDto.getTitle());
//        assertEquals(post.getUrl(), postDto.getUrl());
//    }
//
//    @Test
//    public void whenConvertPostDtoToPostEntity_thenCorrect() {
//        PostDto postDto = new PostDto();
//        postDto.setId(1L);
//        postDto.setTitle(randomAlphabetic(6));
//        postDto.setUrl("www.test.com");
//
//        Post post = modelMapper.map(postDto, Post.class);
//        assertEquals(postDto.getId(), post.getId());
//        assertEquals(postDto.getTitle(), post.getTitle());
//        assertEquals(postDto.getUrl(), post.getUrl());
//    }
//}