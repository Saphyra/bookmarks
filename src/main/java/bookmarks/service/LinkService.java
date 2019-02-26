package bookmarks.service;

import static org.github.bookmarks.common.util.Util.replaceIfNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.github.bookmarks.common.util.CategoryUtil;
import org.github.bookmarks.user.UserFacade;
import org.springframework.stereotype.Service;

import org.github.bookmarks.links.link.controller.request.CreateLinkRequest;
import org.github.bookmarks.links.link.controller.request.UpdateLinkRequest;
import bookmarks.dataaccess.LinkDao;
import bookmarks.domain.link.Link;
import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final CategoryUtil categoryUtil;
    private final IdGenerator idGenerator;
    private final LinkDao linkDao;
    private final UserFacade userFacede;

    public void create(CreateLinkRequest request, String userId) {
        userFacede.checkUserWithIdExists(userId);

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
