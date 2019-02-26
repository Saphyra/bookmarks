package org.github.bookmarks.links.link.service;

import java.util.List;

import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.link.LinkFacade;
import org.github.bookmarks.links.link.controller.request.CreateLinkRequest;
import org.github.bookmarks.links.link.controller.request.UpdateLinkRequest;
import org.github.bookmarks.links.link.domain.Link;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class LinkFacadeImpl implements LinkFacade {
    private final CreateLinkService createLinkService;
    private final DeleteLinkService deleteLinkService;
    private final LinkQueryService linkQueryService;
    private final UpdateLinkService updateLinkService;

    @Override
    public void create(CreateLinkRequest request, String userId) {
        createLinkService.create(request, userId);
    }

    @Override
    public void delete(List<String> linkIds, String userId) {
        deleteLinkService.delete(linkIds, userId);
    }

    @Override
    public List<Link> getByUserId(String userId) {
        return linkQueryService.getByUserId(userId);
    }

    @Override
    public List<DataResponse> getLinksOfParent(String userId, String parent) {
        return linkQueryService.getLinksOfParent(userId, parent);
    }

    @Override
    public void updateParent(String categoryId, String userId, String newRoot) {
        updateLinkService.updateParent(categoryId, userId, newRoot);
    }

    @Override
    public void update(List<UpdateLinkRequest> requests, String userId) {
        updateLinkService.update(requests, userId);
    }
}
