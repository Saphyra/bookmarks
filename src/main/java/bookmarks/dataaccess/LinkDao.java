package bookmarks.dataaccess;

import java.util.List;

import org.springframework.stereotype.Component;

import bookmarks.common.AbstractDao;
import bookmarks.dataaccess.repository.LinkRepository;
import bookmarks.domain.link.Link;
import bookmarks.domain.link.LinkConverter;
import bookmarks.domain.link.LinkEntity;

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
