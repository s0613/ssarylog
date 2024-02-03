package com.ssarylog.api.repository;

import com.ssarylog.api.domain.Post;
import com.ssarylog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
