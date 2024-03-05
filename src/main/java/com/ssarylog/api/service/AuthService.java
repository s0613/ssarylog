package com.ssarylog.api.service;

import com.ssarylog.api.domain.Session;
import com.ssarylog.api.domain.User;
import com.ssarylog.api.exception.AlreadyExistsEmailException;
import com.ssarylog.api.exception.InvalidSignInformation;
import com.ssarylog.api.repository.UserRepository;
import com.ssarylog.api.request.Login;
import com.ssarylog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
@Transactional
public Long signin(Login login) {
    User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
            .orElseThrow(InvalidSignInformation::new);
    Session session = user.addSession();
    return user.getId();
}

    public void signup(Signup signup) {

       Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

       if(userOptional.isPresent()){
           throw new AlreadyExistsEmailException();
       }

        var user = User.builder()
                .name(signup.getName())
                        .password(signup.getPassword())
                                .email(signup.getEmail())
                                        .build();
        userRepository.save(user);
    }
}
