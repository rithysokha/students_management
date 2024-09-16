package com.student.student_management.service;

import com.student.student_management.model.UserModel;
import com.student.student_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            var userModel = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userModel.getUsername())
                    .password(userModel.getPassword())
                    .build();
        }
        throw new UsernameNotFoundException("Invalid credentials");
    }
}
