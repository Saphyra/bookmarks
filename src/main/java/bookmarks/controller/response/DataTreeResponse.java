package bookmarks.controller.response;

import bookmarks.domain.category.Category;
import lombok.Data;

import java.util.List;

@Data
public class DataTreeResponse {
    private Category category;
    private List<DataTreeResponse> children;
}
