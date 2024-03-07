package com.boot.board_240214.service;

import com.boot.board_240214.model.Role;
import com.boot.board_240214.model.User;
import com.boot.board_240214.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User save(User user){
        log.info("@# save()");
        //    사용자 패스워드를 가져와서 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        log.info("@# encodedPassword===>"+encodedPassword);
        //    암호화된 패스워드를 비밀번호로 저장
        user.setPassword(encodedPassword);
        //    활성화된 사용자
        user.setEnabled(true);

        Role role=new Role();
        role.setId(1L);
        user.getRoles().add(role);

        return userRepository.save(user);
    }
}
