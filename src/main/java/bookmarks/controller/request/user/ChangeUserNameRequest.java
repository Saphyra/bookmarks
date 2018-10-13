package bookmarks.controller.request.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangeUserNameRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String newUserName;

    @NotNull
    @Size(min = 1)
    private String password;
}
