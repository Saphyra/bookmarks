package bookmarks.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LinkRequest {
    @NotNull
    @Size(min = 1, max = 100)
    private String Label;

    @NotNull
    @Size(min = 1, max = 4000)
    private String url;

    @NotNull
    @Size(max = 50)
    private String root;

    private Boolean archived;
}