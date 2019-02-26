package org.github.bookmarks.links.link.service;

import static org.github.bookmarks.common.util.Util.replaceIfNotNull;

import java.util.List;

import org.github.bookmarks.links.common.util.CategoryUtil;
import org.github.bookmarks.links.link.controller.request.UpdateLinkRequest;
import org.github.bookmarks.links.link.dataaccess.LinkDao;
import org.github.bookmarks.links.link.domain.Link;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateLinkService {
    private final CategoryUtil categoryUtil;
    private final LinkDao linkDao;
    private final LinkQueryService linkQueryService;

    void updateParent(String categoryId, String userId, String newRoot) {
        linkDao.getByRootAndUserId(categoryId, userId).forEach(link -> {
            link.setRoot(newRoot);
            linkDao.save(link);
        });
    }

    public void update(List<UpdateLinkRequest> requests, String userId) {
        requests.forEach(request -> updateLink(userId, request));
    }

    private void updateLink(String userId, UpdateLinkRequest request) {
        try {
            categoryUtil.validateRoot(request.getRoot(), userId);

            Link link = linkQueryService.findByIdAuthorized(request.getLinkId(), userId);
            link.setLabel(replaceIfNotNull(link.getLabel(), request.getLabel()));
            link.setUrl(replaceIfNotNull(link.getUrl(), request.getUrl()));
            link.setRoot(replaceIfNotNull(link.getRoot(), request.getRoot()));
            link.setArchived(replaceIfNotNull(link.getArchived(), request.getArchived()));

            linkDao.save(link);
        } catch (RuntimeException e) {
            log.error("Error occurred during link update: {}", e);
        }

    }
}
