package org.github.bookmarks.links.link.service;

import java.util.List;
import java.util.stream.Collectors;

import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.common.util.CategorizableToDataResponseConverter;
import org.github.bookmarks.links.link.dataaccess.LinkDao;
import org.github.bookmarks.links.link.domain.Link;
import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class LinkQueryService {
    private final LinkDao linkDao;
    private final CategorizableToDataResponseConverter converter;

    Link findByIdAuthorized(String linkId, String userId) {
        Link link = linkDao.findById(linkId).orElseThrow(() -> new NotFoundException("Link not found with id " + linkId));
        if (!link.getUserId().equals(userId)) {
            throw new ForbiddenException(userId + " has no access for link " + linkId);
        }
        return link;
    }

    public List<Link> getByUserId(String userId) {
        return linkDao.getByUserId(userId);
    }

    List<DataResponse> getLinksOfParent(String userId, String parent) {
        return linkDao.getByRootAndUserId(parent, userId).stream()
            .map(converter::convertEntity)
            .collect(Collectors.toList());
    }
}
