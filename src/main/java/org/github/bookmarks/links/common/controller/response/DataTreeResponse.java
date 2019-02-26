package org.github.bookmarks.links.common.controller.response;

import java.util.List;

import org.github.bookmarks.links.category.domain.Category;
import lombok.Data;

@Data
public class DataTreeResponse {
    private Category category;
    private List<DataTreeResponse> children;
}
