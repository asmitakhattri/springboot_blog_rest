package com.springboot.blog.service;

import com.springboot.blog.playload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(long postId);
    CommentDTO getCommentById(Long postId, Long commentID);
    CommentDTO updateComment(Long postId, Long commentID, CommentDTO commentRequest);
    void deleteComment(Long postId, Long commentID);







}
