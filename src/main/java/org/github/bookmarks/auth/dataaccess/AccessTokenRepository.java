package org.github.bookmarks.auth.dataaccess;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.github.bookmarks.auth.domain.AccessTokenEntity;

@Repository
interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    @Transactional
    @Modifying
    @Query(value = "DELETE AccessTokenEntity a WHERE a.lastAccess < :expiration AND a.persistent=false")
    void deleteExpired(@Param("expiration") Long expiration);

    void deleteByUserId(String userId);
}
