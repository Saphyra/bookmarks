package bookmarks.domain.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class CategoryEntity {
    @Id
    @Column(name = "category_id", length = 50)
    private String categoryId;

    @Column(name = "parent_id", length = 50)
    private String parentId;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "text")
    @Type(type = "text")
    private String text;

    @Column(name = "description")
    @Type(type = "text")
    private String description;
}
