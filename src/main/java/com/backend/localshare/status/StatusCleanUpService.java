package com.backend.localshare.status;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatusCleanUpService {

    private final StatusRepository statusRepository;

    /**
     * Scheduled task to delete expired statuses every hour.
     */
    @Scheduled(fixedRate = 3600000) // Runs every hour
    public Integer deleteExpiredStatuses() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        return statusRepository.deleteByCreatedAtBefore(cutoffTime);
    }
}
