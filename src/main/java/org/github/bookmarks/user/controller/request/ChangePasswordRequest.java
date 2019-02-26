package org.github.bookmarks.user.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotNull
    @Size(min = 6, max = 30)
    private String newPassword;

    @NotNull
    @Size(min = 1)
    private String oldPassword;
}
