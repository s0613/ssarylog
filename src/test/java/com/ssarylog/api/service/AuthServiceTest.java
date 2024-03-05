package com.ssarylog.api.service;

import com.ssarylog.api.domain.User;
import com.ssarylog.api.exception.AlreadyExistsEmailException;
import com.ssarylog.api.repository.UserRepository;
import com.ssarylog.api.request.Signup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1(){
        // given
        Signup signup = Signup.builder()
                .name("ssary")
                        .password("1234")
                                .email("ssary00@naver.com")
                                        .build();

        // when
        authService.signup(signup);
        // then
        Assertions.assertEquals(1,userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("ssary00@naver.com",user.getEmail());
        assertEquals("ssary",user.getName());
        assertEquals("1234",user.getPassword());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2(){
        // given
        User user = User.builder()
                .email("ssary00@naver.com")
                .password("1234")
                .name("ZZANG")
                .build();
        userRepository.save(user);
        Signup signup = Signup.builder()
                .name("ssary")
                .password("1234")
                .email("ssary00@naver.com")
                .build();

        // when
        Assertions.assertThrows(AlreadyExistsEmailException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                authService.signup(signup);
            }
        });
    }

}