package bookmarks.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String categoryId;
    private String parentId;
    private String userId;
    private String text;
    private String description;
}
