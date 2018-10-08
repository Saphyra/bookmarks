package bookmarks.controller.response;

import bookmarks.domain.Categorizable;
import lombok.Data;

@Data
public class DataResponse {
    public enum Type{
        CATEGORY, LINK
    }

    private Categorizable element;
    private Type type;
}
