package bookmarks.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateCategoryRequest {
    @NotNull
    @Size(min = 1, max = 50)
    private String categoryId;

    @NotNull
    @Size(min = 1, max = 100)
    private String label;

    @NotNull
    @Size(max = 1000)
    private String description;

    @NotNull
    @Size(max = 50)
    private String root;
}
