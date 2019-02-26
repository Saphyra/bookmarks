package org.github.bookmarks.links.common.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

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
