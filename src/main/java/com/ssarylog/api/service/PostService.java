package com.ssarylog.api.service;

import com.ssarylog.api.domain.Post;
import com.ssarylog.api.domain.PostEditor;
import com.ssarylog.api.exception.PostNotFound;
import com.ssarylog.api.repository.PostRepository;
import com.ssarylog.api.request.PostCreate;
import com.ssarylog.api.request.PostEdit;
import com.ssarylog.api.request.PostSearch;
import com.ssarylog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository; // 기본 생성자로 인젝션함 @AutoWired를 안 쓴다는 거임

    public void write(PostCreate postCreate){
        // postCreate -> Entity로 바꾸어 주어야함 현재 request DTO형태임

        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        postRepository.save(post);
    }
    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

    }

    // 글이 너무 많은 경우 -> 비용이 너무 많이 든다.
    // 글이  -> 100,000,000 -> DB글 모두 조회하는 경우 -> DB가 뻗을 수 있다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 들이 많이 발생할 수 있다.

    public List<PostResponse> getList(PostSearch postSearch) {
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));

        return postRepository.getList(postSearch).stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(()->new PostNotFound());
//        post.setTitle(postEdit.getTitle());
//        post.setContent(postEdit.getContent());

        // postRepository.save(post); -> @Transactional를 하면 commit를 알아서 친다.

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();
        post.edit(postEditor);
    }


    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new PostNotFound());
        postRepository.delete(post);
    }
}
