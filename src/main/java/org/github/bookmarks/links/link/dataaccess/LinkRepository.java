package org.github.bookmarks.links.link.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.github.bookmarks.links.link.domain.LinkEntity;

@Repository
interface LinkRepository extends JpaRepository<LinkEntity, String> {
    List<LinkEntity> getByRootAndUserId(String categoryId, String userId);

    List<LinkEntity> getByUserId(String userId);

    void deleteByUserId(String userId);
}
