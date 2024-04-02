package com.ssarylog.api.service;
import com.ssarylog.api.domain.User;
import com.ssarylog.api.exception.AlreadyExistsEmailException;
import com.ssarylog.api.exception.InvalidSignInformation;
import com.ssarylog.api.repository.UserRepository;
import com.ssarylog.api.request.Signup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@Profile("test")
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
    @Test
    @DisplayName("로그인 성공")
    void test3(){
        // given
//        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
//        String encryptedPassword = encoder.encrypt("1234");
//
//        User user = User.builder()
//                .email("ssary00@naver.com")
//                .password(encryptedPassword)
//                .name("ZZANG")
//                .build();
//        userRepository.save(user);
//
//        Login login = Login.builder()
//                .email("ssary00@naver.com")
//                .password("1234")
//                .build();
//        // when
//        Long userId = authService.signin(login);
//
//        // then
//        assertNotNull(userId);
    }
    @Test
    @DisplayName("로그인시 비밀번호 틀림")
    void test4(){
        // given
//        Signup signup = Signup.builder()
//                .name("ssary")
//                .password("1234")
//                .email("ssary00@naver.com")
//                .build();
//        authService.signup(signup);
//        Login login = Login.builder()
//                .email("ssary00@naver.com")
//                .password("5678")
//                .build();
//        // when
//        Long userId = authService.signin(login);
//
//        // then
//        Assertions.assertThrows(InvalidSignInformation.class, () -> authService.signin(login));

    }

}