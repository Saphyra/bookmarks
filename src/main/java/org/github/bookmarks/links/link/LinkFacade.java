package org.github.bookmarks.links.link;

import java.util.List;

import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.link.domain.Link;

public interface LinkFacade {
    List<Link> getByUserId(String userId);

    List<DataResponse> getLinksOfParent(String userId, String parent);
}
