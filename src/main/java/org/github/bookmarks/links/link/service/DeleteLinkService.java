package org.github.bookmarks.links.link.service;

import java.util.List;

import javax.transaction.Transactional;

import org.github.bookmarks.links.link.dataaccess.LinkDao;
import org.github.bookmarks.links.link.domain.Link;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class DeleteLinkService {
    private final LinkDao linkDao;
    private final LinkQueryService linkQueryService;

    @Transactional
    public void delete(List<String> linkIds, String userId) {
        linkIds.forEach(linkId -> {
            deleteLink(userId, linkId);
        });
    }

    private void deleteLink(String userId, String linkId) {
        try {
            Link link = linkQueryService.findByIdAuthorized(linkId, userId);
            linkDao.delete(link);
        } catch (RuntimeException e) {
            log.error("Error occurred during link deletion: {}", e);
        }
    }
}
