package com.backend.localshare.status;

import com.backend.localshare.common.PageResponse;
import com.backend.localshare.dto.StatusDTO;
import com.backend.localshare.exception.StatusCustomException;
import com.backend.localshare.post.*;
import com.backend.localshare.s3.S3Service;
import com.backend.localshare.user.User;
import com.backend.localshare.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final S3Service s3;

    @Value("${aws.s3.status_bucket}")
    private String bucket;

    public List<Status> getAllPosts() {
        return statusRepository.findAll();
    }

    public ResponseEntity<?> uploadStatus(
            Long userId, MultipartFile file, String caption, ContentType contentType
    ) {
        User user = userService.getUserById(userId);

        if (contentType == null) {
            throw new IllegalArgumentException("Invalid content type.");
        }

        try {
            if (contentType == ContentType.IMAGE) {
                imageService.processImage(file);
                // Additional image-specific logic
            } else {
                // Placeholder for video processing logic
            }

            String type = imageService.detectMimeType(file.getInputStream());
            String key = generateObjectKey(type);

            String version = s3.putObject(
                    bucket, key, file, "max-age=86400, public", type
            ).versionId();

            String url = s3.getPublicUrl(bucket, key);

            Status status = Status.builder()
                    .caption(caption)
                    .statusType(contentType)
                    .statusUrl(url)
                    .stars(0)
                    .views(1)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusHours(24))
                    .objectKey(key)
                    .versionId(version)
                    .userId(user.getUserId())
                    .build();

            Long savedPostId = statusRepository.save(status).getStatusId();
            return ResponseEntity.ok(savedPostId);
        } catch (IOException e) {
            throw new StatusCustomException("Failed to process the uploaded file.", e);
        }
    }

    public List<Status> getStatusesFromLast24Hours() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        return statusRepository.findByCreatedAtAfter(cutoffTime);
    }

    public boolean deleteStatusById(Long id) {
        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String generateObjectKey(String type) {
        SecureRandom secureRandom = new SecureRandom();
        var extension = imageService.determineExtension(type);
        String key;
        do {
            StringBuilder codeBuilder = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                int randomIndex = secureRandom.nextInt("1234567890abcdefghijklmnopqrstuvwxyz_".length());
                codeBuilder.append("1234567890abcdefghijklmnopqrstuvwxyz_".charAt(randomIndex));
            }
            key = codeBuilder + extension;
        } while (statusRepository.existsByImageKey(key));
        return key;
    }

    public PageResponse<StatusDTO> getUnseenStatus(Integer page, Long userId, String location) {
        validatePaginationInputs(page, userId, location);

        Pageable pageable = PageRequest.of(page, 30);
        Page<StatusDTO> unseenStatus = statusRepository.getUnseenStatus(
                location, userId, pageable, LocalDateTime.now()
        );
        return buildPageResponse(unseenStatus);
    }

    public PageResponse<StatusDTO> getSeenStatus(Integer page, Long userId, String location) {
        validatePaginationInputs(page, userId, location);

        Pageable pageable = PageRequest.of(page, 30);
        Page<StatusDTO> seenStatus = statusRepository.getSeenStatus(
                location, userId, pageable, LocalDateTime.now()
        );
        return buildPageResponse(seenStatus);
    }

    private PageResponse<StatusDTO> buildPageResponse(Page<StatusDTO> status) {
        return new PageResponse<>(
                status.getContent(),
                status.getNumber(),
                status.getTotalElements(),
                status.getTotalPages(),
                status.isFirst(),
                status.isLast(),
                status.hasNext(),
                status.hasPrevious()
        );
    }

    private void validatePaginationInputs(Integer page, Long userId, String location) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be greater than or equal to 0.");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
    }
}