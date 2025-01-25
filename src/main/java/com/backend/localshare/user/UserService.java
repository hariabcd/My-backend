package com.backend.localshare.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    // Todo: security context holder
    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElse(null);
    }
}
