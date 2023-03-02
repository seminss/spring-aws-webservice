package com.jojoldu.book.demo.config.auth;

import com.jojoldu.book.demo.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.demo.config.auth.dto.SessionUser;
import com.jojoldu.book.demo.domain.user.User;
import com.jojoldu.book.demo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /* 구글 로그인 이후 가져온 사용자의 정보들을 기반으로
    가입 및 정보 수정, 세션 저장 등의 기능 지원 */
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate=new DefaultOAuth2UserService();
        OAuth2User oAuth2User= delegate.loadUser(userRequest);

        //현재 로그인 진행 중인 서비스 구분 (구글, 네이버 등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2 로그인 진행 시 키가 되는 필드값 (Primary Key와 같은 의미)
        String userNameAttributeName=userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        httpSession.setAttribute("user",new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),attributes.getNameAttributeKey());
    }

    //구글 사용자 정보가 업데이트 되었을 때를 대비하여 Update 기능 같이 구현
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user=userRepository.findByEmail(attributes.getEmail())
                .map(entity->entity.update(attributes.getName(),attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);

    }

}
