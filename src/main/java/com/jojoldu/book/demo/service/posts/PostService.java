package com.jojoldu.book.demo.service.posts;

import com.jojoldu.book.demo.domain.posts.Posts;
import com.jojoldu.book.demo.domain.posts.PostsRepository;
import com.jojoldu.book.demo.web.Dto.PostSaveRequestDto;
import com.jojoldu.book.demo.web.PostResponseDto;
import com.jojoldu.book.demo.web.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostsRepository postsRepository;
    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(),requestDto.getContent()); //Post Entity(DB에 맞닿아있는)를 실제 update
        return id;
    }

    public PostResponseDto findById(Long id) {
        Posts entity=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostResponseDto(entity); //response : 반환 객체
    }
}
