package bookmarks.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "user_name")
    @Type(type = "text")
    private String userName;

    @Column(name = "password")
    @Type(type = "text")
    private String password;
}
