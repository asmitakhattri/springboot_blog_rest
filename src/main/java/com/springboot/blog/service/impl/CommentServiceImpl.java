package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.playload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.BadLocationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // set post to comment entity
        comment.setPost(post);
        // comment entity to database
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);

    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        //retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        //convert List of comment entities toList of comment dto
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentID) {
        //retrieve post entity by id
        // we are just calling findById(0 method of post repository we are passing postId, if the post id not exist with this id, then we will throw resource not found
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentID).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentID));
        // simple business logic that is to check whether this comment is belongs to a particular post or not, if not then we will throw the exception. so for that we will create one customer exception
        //here we write the condition that is comment not belongs to a particular post and simply throw the exception
        if(!Objects.equals(comment.getPost().getId(), post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belong to a post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentID, CommentDTO commentRequest) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentID).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentID));

        if(!Objects.equals(comment.getPost().getId(), post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to a post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentID) {
        //retrieve comment by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentID).orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentID));
        if(!Objects.equals(comment.getPost().getId(), post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to a post");
        }
        commentRepository.delete(comment);


    }

    private CommentDTO mapToDto(Comment comment){
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
        //CommentDTO commentDTO = new CommentDTO();
        //commentDTO.setId(comment.getId());
        //commentDTO.setName(comment.getName());
        //commentDTO.setEmail(comment.getEmail());
        //commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = mapper.map(commentDTO, Comment.class);
        //Comment comment = new Comment();
        //comment.setId(commentDTO.getId());
        //comment.setName(commentDTO.getName());
        //comment.setEmail(commentDTO.getEmail());
        //comment.setBody(commentDTO.getBody());
        return comment;
    }

}
