package bookmarks.controller.response;

import bookmarks.domain.Categorizable;
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
