package com.jojoldu.book.demo.service.posts;

import com.jojoldu.book.demo.domain.posts.Posts;
import com.jojoldu.book.demo.domain.posts.PostsRepository;
import com.jojoldu.book.demo.web.Dto.PostsSaveRequestDto;
import com.jojoldu.book.demo.web.Dto.PostsResponseDto;
import com.jojoldu.book.demo.web.Dto.PostsListResponseDto;
import com.jojoldu.book.demo.web.Dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(),requestDto.getContent()); //Post Entity(DB에 맞닿아있는)를 실제 update
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity); //response : 반환 객체
    }

    @Transactional
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                //.map(posts -> new PostListResponseDto(posts))
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);
    }
}
