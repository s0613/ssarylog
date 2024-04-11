package com.ssarylog.api.service;

import com.ssarylog.api.domain.Post;
import com.ssarylog.api.domain.User;
import com.ssarylog.api.exception.PostNotFound;
import com.ssarylog.api.repository.PostRepository;
import com.ssarylog.api.repository.UserRepository;
import com.ssarylog.api.request.PostCreate;
import com.ssarylog.api.request.PostEdit;
import com.ssarylog.api.request.PostSearch;
import com.ssarylog.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    void clean(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        //given
        var user = User.builder()
                .name("ssj")
                .email("ssary00@naver.com")
                .password("123")
                .build();

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postService.write(user.getId(),postCreate);
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        // 클라이언트 요구사항
            // json응답에서 title갑 길이를 최대 10글자로 해주세요.
            // 이런 처리는 클라이언트에서 하는게 맞음
        // when
        PostResponse post = postService.get(requestPost.getId());

        //then
        Assertions.assertNotNull(post);
        Assertions.assertEquals("foo", post.getTitle());
        Assertions.assertEquals("bar", post.getContent());
    }
    @Test
    @DisplayName("글 1페이지 조회")
    void test3(){
        // given
        List<Post> requestPosts = IntStream.range(1,31)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("승주의 제목 - " + i)
                            .content("반포자이 - " + i)
                            .build();
                })
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();


        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        Assertions.assertEquals(10L,posts.size());
        Assertions.assertEquals("승주의 제목 - 30",posts.get(0).getTitle());

    }
    @Test
    @DisplayName("글 제목 수정")
    void test4(){
        // given
        Post post = Post.builder()
                .title("승주")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("승주맨")
                .content("반포자이")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
        Assertions.assertEquals("승주맨", changePost.getTitle());
    }
    @Test
    @DisplayName("글 제목 수정") // 클라이언트와 약속을 해야한다. 수정한 데이터만 보낼 것인지 아니면 수정한 것과 기존 데이터를 다 같이 보낼 것인지
    void test5(){
        // given
        Post post = Post.builder()
                .title("승주")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("승주맨")
                .content("초가집")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
        Assertions.assertEquals("초가집", changePost.getContent());
    }

    @Test
    @DisplayName("delete")
    void test6(){
        //given
        Post post = Post.builder()
                .title("승주")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        Assertions.assertEquals(0,postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });

        // PostNotFound class에 구현한 예외와 postService에 get이 예외를 발생하면 나오는 예외와 같은지 비교
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test9() {
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("호돌맨")
                .content("초가집")
                .build();

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });
    }
}