package bookmarks.controller.response;

import bookmarks.domain.Categorizable;
import lombok.Data;

@Data
public class DataResponse {
    public static enum Type{
        CATEGORY, LINK
    }

    private Categorizable element;
    private Type type;
}
