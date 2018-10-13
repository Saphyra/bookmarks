package bookmarks.service;

import static bookmarks.util.Util.replaceIfNotNull;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import bookmarks.domain.Categorizable;
import org.springframework.stereotype.Service;

import bookmarks.common.exception.ForbiddenException;
import bookmarks.common.exception.NotFoundException;
import bookmarks.controller.request.CreateLinkRequest;
import bookmarks.controller.request.UpdateLinkRequest;
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

    public void create(CreateLinkRequest request, String userId) {
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

    @Transactional
    public void delete(List<String> linkIds, String userId) {
        linkIds.forEach(linkId -> {
            Link link = findByIdAuthorized(linkId, userId);
            linkDao.delete(link);
        });
    }

    private Link findByIdAuthorized(String linkId, String userId) {
        Link link = linkDao.findById(linkId).orElseThrow(() -> new NotFoundException("Link not found with id " + linkId));
        if (!link.getUserId().equals(userId)) {
            throw new ForbiddenException(userId + " has no access for link " + linkId);
        }
        return link;
    }

    public List<Link> getLinksByRootAndUserId(String root, String userId) {
        return linkDao.getByRootAndUserId(root, userId);
    }

    @Transactional
    public void update(List<UpdateLinkRequest> requests, String userId) {
        requests.forEach(request ->{
            categoryUtil.validateRoot(request.getRoot(), userId);

            Link link = findByIdAuthorized(request.getLinkId(), userId);
            link.setLabel(replaceIfNotNull(link.getLabel(), request.getLabel()));
            link.setUrl(replaceIfNotNull(link.getUrl(), request.getUrl()));
            link.setRoot(replaceIfNotNull(link.getRoot(), request.getRoot()));
            link.setArchived(replaceIfNotNull(link.getArchived(), request.getArchived()));

            linkDao.save(link);
        });
    }

    public List<Link> getByUserId(String userId) {
        return linkDao.getByUserId(userId);
    }
}
