package com.ssarylog.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC) // <- 알아보기
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public PostEditor.PostEditorBuilder toEditor(){ // 빌더 클래스 자체를 넘겨주기 때문에 build();를 넣지 않는다.
            return PostEditor.builder()
                .title(title)
                .content(content);
    } // builder class안에 들어갔다가 값이 넘어옴 변수들은 기본적으로 null이 들어감

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }

    public Long getUserId(){
        return this.user.getId();
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }
}
