package org.github.bookmarks.user.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChangeUserNameRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String newUserName;

    @NotNull
    @Size(min = 1)
    private String password;
}
