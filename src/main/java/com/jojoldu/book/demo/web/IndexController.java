package com.jojoldu.book.demo.web;

import com.jojoldu.book.demo.config.auth.LoginUser;
import com.jojoldu.book.demo.config.auth.dto.SessionUser;
import com.jojoldu.book.demo.domain.user.User;
import com.jojoldu.book.demo.service.posts.PostsService;
import com.jojoldu.book.demo.web.Dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;


//페이지와 관련된 컨트롤러
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){

        model.addAttribute("posts",postsService.findAllDesc());

        //세션에 저장된 값이 있을 때만 model 에 userName 으로 등록
        if(user!=null){
            model.addAttribute("userId",user.getName());
        }
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
