package com.ssarylog.api.service;

import com.ssarylog.api.crypto.PasswordEncoder;
import com.ssarylog.api.domain.Session;
import com.ssarylog.api.domain.User;
import com.ssarylog.api.exception.AlreadyExistsEmailException;
import com.ssarylog.api.exception.InvalidSignInformation;
import com.ssarylog.api.repository.UserRepository;
import com.ssarylog.api.request.Login;
import com.ssarylog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
@Transactional
public Long signin(Login login) {
    User user = userRepository.findByEmail(login.getEmail())
            .orElseThrow(InvalidSignInformation::new);
    PasswordEncoder encoder = new PasswordEncoder();
    boolean matches = encoder.matches(login.getPassword(), user.getPassword());
    if (!matches) {
        throw new InvalidSignInformation();
    }
    return user.getId();
}

    public void signup(Signup signup) {

       Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

       if(userOptional.isPresent()){
           throw new AlreadyExistsEmailException();
       }

        PasswordEncoder encoder = new PasswordEncoder();

        String encryptedPassword = encoder.encrypt(signup.getPassword());

        var user = User.builder()
                .name(signup.getName())
                        .password(encryptedPassword)
                                .email(signup.getEmail())
                                        .build();
        userRepository.save(user);
    }
}
