package com.ssarylog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 *  PostCreate와 비슷하다고 하여 PostCreate에 수정 기능을 넣는 다면 줄빠따 맞는다.
 *  나중에 가면 수정이 어려워지니까 처음부터 분리해서 만들자
 */
@ToString
@Setter
@Getter
public class PostEdit {
    @NotBlank(message = "타이틀을 입력하세요.")
    private String title;
    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
