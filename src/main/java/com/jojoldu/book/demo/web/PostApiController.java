package com.jojoldu.book.demo.web;

import com.jojoldu.book.demo.service.posts.PostsService;
import com.jojoldu.book.demo.web.Dto.PostsResponseDto;
import com.jojoldu.book.demo.web.Dto.PostsSaveRequestDto;
import com.jojoldu.book.demo.web.Dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostsService postService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postService.update(id,requestDto); //id로 post 찾기, 넘겨받은 Dto 정보로 업데이트
    }

    @GetMapping("api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }
}
