package bookmarks.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FilteredRequest {
    @NotNull
    private String label;

    @NotNull
    private String secondary;

    @NotNull
    @Size(min = 1)
    private String type;
}
