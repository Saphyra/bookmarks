package bookmarks.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateCategoryRequest {
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
