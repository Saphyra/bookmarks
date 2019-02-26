package org.github.bookmarks.links.link.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateLinkRequest {
    @NotNull
    @Size(min = 1, max = 50)
    private String linkId;

    @Size(min = 1, max = 100)
    private String label;

    @Size(min = 1, max = 4000)
    private String url;

    @Size(max = 50)
    private String root;

    private Boolean archived;
}
