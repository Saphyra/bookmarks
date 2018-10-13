package bookmarks.domain.link;

import bookmarks.domain.Categorizable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Link implements Categorizable {
    private String linkId;
    private String root;
    private String userId;
    private String label;
    private String url;
    private Boolean archived;

    @Override
    public String getSecondary() {
        return url;
    }

    @Override
    public String getId() {
        return linkId;
    }

    @Override
    public String getRoot() {
        return root;
    }
}
