package org.github.bookmarks.links.link.dataaccess;

import java.util.List;

import org.github.bookmarks.links.link.domain.Link;
import org.github.bookmarks.links.link.domain.LinkConverter;
import org.github.bookmarks.links.link.domain.LinkEntity;
import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;

@Component
public class LinkDao extends AbstractDao<LinkEntity, Link, String, LinkRepository> {
    public LinkDao(LinkConverter converter, LinkRepository repository) {
        super(converter, repository);
    }

    public void deleteByUserId(String userId){
        repository.deleteByUserId(userId);
    }

    public List<Link> getByUserId(String userId){
        return converter.convertEntity(repository.getByUserId(userId));
    }

    public List<Link> getByRootAndUserId(String categoryId, String userId){
        return converter.convertEntity(repository.getByRootAndUserId(categoryId, userId));
    }
}
