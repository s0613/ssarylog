package com.ssarylog.api.service;

import com.ssarylog.api.domain.Comment;
import com.ssarylog.api.domain.Post;
import com.ssarylog.api.exception.PostNotFound;
import com.ssarylog.api.repository.comment.CommentRepository;
import com.ssarylog.api.repository.post.PostRepository;
import com.ssarylog.api.request.comment.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void write(Long postId, CommentCreate request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        Comment comment = Comment.builder().
                author(request.getAuthor())
                        .password(encryptedPassword)
                                .content(request.getContent()).
                build();

        post.addComment(comment);
    }
}
