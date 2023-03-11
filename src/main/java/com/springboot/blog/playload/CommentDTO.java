package com.springboot.blog.playload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CommentDTO {
    // name should not be null or empty
    private long id;
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    @NotEmpty(message = "email should not be null or empty")
    @Email
    private String email;

    //comment body should not be null or empty
    //comment body must be minimum 10 characters
    @NotEmpty
    @Size(min = 10, message = "comment body must be minimum 10 characters")
    private String body;

}
