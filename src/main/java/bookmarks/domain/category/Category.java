package bookmarks.domain.category;

import bookmarks.domain.Categorizable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Categorizable {
    private String categoryId;
    private String parentId;
    private String userId;
    private String text;
    private String description;

    @Override
    public String getId() {
        return categoryId;
    }

    @Override
    public String getRoot() {
        return parentId;
    }
}
