package com.ssarylog.api.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssarylog.api.domain.Post;
import com.ssarylog.api.domain.QPost;
import com.ssarylog.api.request.post.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Post> getList(PostSearch postSearch){
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();

    }
}
