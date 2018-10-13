package bookmarks.controller.request.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {
    @NotNull
    @Size(min = 6, max = 30)
    private String newPassword;

    @NotNull
    @Size(min = 1)
    private String oldPassword;
}
