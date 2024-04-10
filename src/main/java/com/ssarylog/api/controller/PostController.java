package com.ssarylog.api.controller;

import com.ssarylog.api.request.PostCreate;
import com.ssarylog.api.request.PostEdit;
import com.ssarylog.api.request.PostSearch;
import com.ssarylog.api.response.PostResponse;
import com.ssarylog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService; // final이 붙지 않으면 기본 생성자로 주입이 되지 않습니다.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        request.isValid();
        postService.write(request);
    }

    /**
     * 조회 API
     * /posts -> 글 전체 조회(검색 + 페이징)
     * /posts -> 글 한 개만 조회
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/posts/{postsId}")
    public PostResponse get(@PathVariable(name = "postsId") Long id) {

        PostResponse post = postService.get(id);
        // 응답 클래스 분리하세요 (서버스 정책에 맞는)
        return post;
    }

    // 조회 API
    // 여러개의 글을 조회 API
    // /posts
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }
}
