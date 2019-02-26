package org.github.bookmarks.links.link;

import java.util.List;

import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.link.controller.request.CreateLinkRequest;
import org.github.bookmarks.links.link.controller.request.UpdateLinkRequest;
import org.github.bookmarks.links.link.domain.Link;

public interface LinkFacade {
    void create(CreateLinkRequest request, String userId);

    void delete(List<String> linkIds, String userId);

    List<Link> getByUserId(String userId);

    List<DataResponse> getLinksOfParent(String userId, String parent);

    void updateParent(String categoryId, String userId, String rootId);

    void update(List<UpdateLinkRequest> requests, String userId);
}
