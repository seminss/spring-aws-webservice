package com.jojoldu.book.demo.web.domain.posts;


import com.jojoldu.book.demo.domain.posts.Posts;
import com.jojoldu.book.demo.domain.posts.PostsRepository;
import com.jojoldu.book.demo.web.HelloController;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content ="테스트 본문";
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts =postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2023,2,21,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList=postsRepository.findAll();

        //then
        Posts posts=postsList.get(0);

        System.out.println(">>>>>>>>>>> createDate="+posts.getCreatedDate()+", modifiedDate= "+posts.getModifiedDate());


        Assertions.assertThat(posts.getCreatedDate()).isAfter(now);
        Assertions.assertThat(posts.getModifiedDate()).isAfter(now);

    }

    @AfterEach
    public void cleanUp(){
        postsRepository.deleteAll();
    }
}
