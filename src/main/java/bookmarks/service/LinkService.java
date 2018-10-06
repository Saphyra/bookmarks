package bookmarks.service;

import bookmarks.controller.request.CreateLinkRequest;
import bookmarks.dataaccess.LinkDao;
import bookmarks.domain.link.Link;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final IdGenerator idGenerator;
    private final LinkDao linkDao;
    private final UserService userService;

    public void create(CreateLinkRequest request, String userId) {
        userService.findByUserIdAuthorized(userId);

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

    public List<Link> getLinksByRoot(String root) {
        return linkDao.getByRoot(root);
    }
}
