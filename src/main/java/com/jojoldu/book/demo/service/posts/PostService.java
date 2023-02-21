package com.jojoldu.book.demo.service.posts;

import com.jojoldu.book.demo.domain.posts.PostsRepository;
import com.jojoldu.book.demo.web.Dto.PostSaveRequestDto;
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
}
