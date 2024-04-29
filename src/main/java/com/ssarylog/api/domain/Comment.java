package com.ssarylog.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        indexes = {
                @Index(name = "IDX_COMMENT_POST_ID", columnList = "post_id")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn
    private Post post;
    @Builder
    public Comment(String author, String password, String content, Post post) {
        this.author = author;
        this.password = password;
        this.content = content;
        this.post = post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
