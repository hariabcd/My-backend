package com.backend.localshare.status_reaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/status-reaction")
@RequiredArgsConstructor
public class StatusReactionController {
    private final StatusReactionService statusReactionService;

    @PutMapping("/{postId}/views/increment")
    public String incrementViews(@PathVariable Long StatusId,
                                 @PathVariable Long userId) {
        statusReactionService.addStatusView(StatusId, userId);
        return "View count incremented for post ID: " + StatusId;
    }
}
