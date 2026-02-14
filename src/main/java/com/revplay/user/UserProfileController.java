package com.revplay.user;


import com.revplay.common.SecurityUtil;
import com.revplay.user.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final RpUserRepository userRepository;

    @GetMapping
    public UserProfileResponse getProfile() {

        String username = SecurityUtil.getCurrentUsername();

        RpUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .profileImage(user.getProfileImage())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @PutMapping
    public UserProfileResponse updateProfile(
            @RequestBody UserProfileResponse updatedUser) {

        String username = SecurityUtil.getCurrentUsername();

        RpUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDisplayName(updatedUser.getDisplayName());
        user.setBio(updatedUser.getBio());
        user.setProfileImage(updatedUser.getProfileImage());

        userRepository.save(user);

        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .profileImage(user.getProfileImage())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
