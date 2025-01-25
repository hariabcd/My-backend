package com.backend.localshare.status;

import com.backend.localshare.post.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;
    private final StatusCleanUpService statusCleanUpService;

    @PostMapping("/create-status")
    public ResponseEntity<?> uploadStatus(
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "caption", required = false) String caption,
            @RequestParam("type") String type
    ) {
        ContentType contentType = ContentType.valueOf(type);
        return statusService.uploadStatus(userId, file, caption, contentType);
    }

    // Get statuses created in the last 24 hours
    @GetMapping("/get-status")
    public ResponseEntity<List<Status>> getRecentStatuses() {
        List<Status> recentStatuses = statusService.getStatusesFromLast24Hours();
        return ResponseEntity.ok(recentStatuses);
    }

    // Delete a specific status by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable Long id) {
        boolean isDeleted = statusService.deleteStatusById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Status deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Status not found.");
        }
    }

    /**
     * Manually delete expired statuses
     * @return Number of statuses deleted
     */
    @DeleteMapping("/expired")
    public ResponseEntity<String> deleteExpiredStatuses() {
        int deletedCount = statusCleanUpService.deleteExpiredStatuses();
        return ResponseEntity.ok("Deleted " + deletedCount + " expired statuses.");
    }

}
