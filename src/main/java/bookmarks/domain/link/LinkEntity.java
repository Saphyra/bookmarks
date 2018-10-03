package bookmarks.domain.link;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;


@Entity
@Table(name = "link")
@Data
public class LinkEntity {
    @Id
    @Column(name = "link_id", length = 50)
    private String linkId;

    @Column(name = "categoryId", length = 50)
    private String categoryId;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "text")
    @Type(type = "text")
    private String text;

    @Column(name = "url")
    @Type(type = "text")
    private String url;

    @Column(name = "archived")
    private Boolean archived;
}
