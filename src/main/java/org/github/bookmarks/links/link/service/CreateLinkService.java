package org.github.bookmarks.links.link.service;

import org.github.bookmarks.links.common.util.CategoryUtil;
import org.github.bookmarks.links.link.controller.request.CreateLinkRequest;
import org.github.bookmarks.links.link.dataaccess.LinkDao;
import org.github.bookmarks.links.link.domain.Link;
import org.github.bookmarks.user.UserFacade;
import org.springframework.stereotype.Service;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateLinkService {
    private final CategoryUtil categoryUtil;
    private final IdGenerator idGenerator;
    private final LinkDao linkDao;
    private final UserFacade userFacade;

    public void create(CreateLinkRequest request, String userId) {
        userFacade.checkUserWithIdExists(userId);

        categoryUtil.validateRoot(request.getRoot(), userId);

        Link link = Link.builder()
            .linkId(idGenerator.generateRandomId())
            .userId(userId)
            .root(request.getRoot())
            .label(request.getLabel())
            .url(request.getUrl())
            .archived(false)
            .build();

        linkDao.save(link);
    }
}
