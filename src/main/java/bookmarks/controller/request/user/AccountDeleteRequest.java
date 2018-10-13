package bookmarks.controller.request.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AccountDeleteRequest {
    @NotNull
    @Size(min = 1)
    private String password;
}
