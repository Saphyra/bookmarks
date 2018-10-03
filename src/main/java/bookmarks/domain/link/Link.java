package bookmarks.domain.link;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    private String linkId;
    private String categoryId;
    private String userId;
    private String text;
    private String url;
    private Boolean archived;
}
