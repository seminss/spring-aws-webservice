package com.jojoldu.book.demo.web;

import com.jojoldu.book.demo.domain.posts.PostsRepository;
import com.jojoldu.book.demo.service.posts.PostsService;
import com.jojoldu.book.demo.web.Dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//페이지와 관련된 컨트롤러
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) { //서버템플릿 엔진에서 사용할 수 있는 객체 지정 가능
        model.addAttribute("posts",postsService.findAllDesc());
        //src/main/resources/templates + /index + .mustache 자동 주소 완성
        return "index";
    }

    @GetMapping("/posts/save")
    public String postSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
