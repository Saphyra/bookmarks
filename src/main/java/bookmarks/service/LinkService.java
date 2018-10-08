package bookmarks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bookmarks.common.exception.ForbiddenException;
import bookmarks.common.exception.NotFoundException;
import bookmarks.controller.request.LinkRequest;
import bookmarks.dataaccess.LinkDao;
import bookmarks.domain.link.Link;
import bookmarks.util.CategoryUtil;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final CategoryUtil categoryUtil;
    private final IdGenerator idGenerator;
    private final LinkDao linkDao;
    private final UserService userService;

    public void create(LinkRequest request, String userId) {
        userService.findByUserIdAuthorized(userId);

        categoryUtil.validateRoot(request.getRoot(), userId);

        Link link = Link.builder()
            .linkId(idGenerator.getRandomId())
            .userId(userId)
            .root(request.getRoot())
            .label(request.getLabel())
            .url(request.getUrl())
            .archived(false)
            .build();

        linkDao.save(link);
    }

    public void delete(String linkId, String userId) {
        Link link = findByIdAuthorized(linkId, userId);
        linkDao.delete(link);
    }

    private Link findByIdAuthorized(String linkId, String userId) {
        Link link = linkDao.findById(linkId).orElseThrow(() -> new NotFoundException("Link not found with id " + linkId));
        if(!link.getUserId().equals(userId)){
            throw new ForbiddenException(userId + " has no access for link " + linkId);
        }
        return link;
    }

    public List<Link> getLinksByRoot(String root) {
        return linkDao.getByRoot(root);
    }

    public void update(LinkRequest request, String linkId, String userId) {
        categoryUtil.validateRoot(request.getRoot(), userId);

        Link link = findByIdAuthorized(linkId, userId);
        link.setLabel(request.getLabel());
        link.setUrl(request.getUrl());
        link.setRoot(request.getRoot());
        link.setArchived(request.getArchived() == null ? false : request.getArchived());

        linkDao.save(link);
    }
}
