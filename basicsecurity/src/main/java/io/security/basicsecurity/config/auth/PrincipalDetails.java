package io.security.basicsecurity.config.auth;


import io.security.basicsecurity.vo.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티session을 만들어줍니다.(Security ContextHolder)
// 오브젝트 => Authentication 객체
// Authentication 안에 User정보가 있어야 됨.
// User오브젝트타입 => UserDetails 타입 객체
// Security Session => Authentication => UserDetails(PrincipalDetails)
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user;
    private Map<String, Object> attributes;

    // 일반로그인
    public PrincipalDetails(User user) {
        this.user = user;
    }
    
    //Oauth로그인
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 해당 User의 권한을 리턴하는 곳
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함.
        // Entity에 loginDate 속성 추가해서 현재시간-로그인시간 >= 1년
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
