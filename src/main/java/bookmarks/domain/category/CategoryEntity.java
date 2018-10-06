package bookmarks.domain.category;

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
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "category_id", length = 50)
    private String categoryId;

    @Column(name = "root", length = 50)
    private String root;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "label")
    @Type(type = "text")
    private String label;

    @Column(name = "description")
    @Type(type = "text")
    private String description;
}
