package bookmarks.controller;

import bookmarks.controller.request.CreateLinkRequest;
import bookmarks.filter.FilterHelper;
import bookmarks.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LinkController {
    private static final String CREATE_LINK_MAPPING = "link";

    private final LinkService linkService;

    @PutMapping(CREATE_LINK_MAPPING)
    public void createLink(
        @RequestBody @Valid CreateLinkRequest request,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to create a new category.", userId);
        linkService.create(request, userId);
    }
}
