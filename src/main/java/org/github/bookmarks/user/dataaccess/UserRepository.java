package org.github.bookmarks.user.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.github.bookmarks.user.domain.UserEntity;

@Repository
interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(String userName);
}
