package org.github.bookmarks.links.common.controller.response;

import org.github.bookmarks.links.Categorizable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataResponse {
    public enum Type{
        CATEGORY, LINK
    }

    private Categorizable element;
    private Type type;
}
