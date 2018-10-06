package bookmarks.domain.link;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link")
public class LinkEntity {
    @Id
    @Column(name = "link_id", length = 50)
    private String linkId;

    @Column(name = "root", length = 50)
    private String root;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "label")
    @Type(type = "text")
    private String label;

    @Column(name = "url")
    @Type(type = "text")
    private String url;

    @Column(name = "archived")
    private Boolean archived;
}
