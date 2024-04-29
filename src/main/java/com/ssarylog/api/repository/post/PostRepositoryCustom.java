package com.ssarylog.api.repository.post;

import com.ssarylog.api.domain.Post;
import com.ssarylog.api.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
