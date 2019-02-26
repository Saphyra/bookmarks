package org.github.bookmarks.links.link.controller;

import java.util.List;

import javax.validation.Valid;

import org.github.bookmarks.auth.PropertySourceImpl;
import org.github.bookmarks.links.link.LinkFacade;
import org.github.bookmarks.links.link.controller.request.CreateLinkRequest;
import org.github.bookmarks.links.link.controller.request.UpdateLinkRequest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LinkController {
    private static final String CREATE_LINK_MAPPING = "link";
    private static final String DELETE_LINK_MAPPING = "link";
    private static final String UPDATE_LINK_MAPPING = "link";

    private final LinkFacade linkFacade;

    @PutMapping(CREATE_LINK_MAPPING)
    public void createLink(
        @RequestBody @Valid CreateLinkRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to create a new link.", userId);
        linkFacade.create(request, userId);
    }

    @DeleteMapping(DELETE_LINK_MAPPING)
    public void deleteLink(
        @RequestBody List<String> linkIds,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to delete link {}.", userId, linkIds);
        linkFacade.delete(linkIds, userId);
    }

    @PostMapping(UPDATE_LINK_MAPPING)
    public void updateLink(
        @RequestBody @Valid List<UpdateLinkRequest> requests,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to update links.", userId);
        linkFacade.update(requests, userId);
    }
}
