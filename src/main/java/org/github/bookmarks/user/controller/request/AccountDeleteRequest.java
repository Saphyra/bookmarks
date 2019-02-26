package org.github.bookmarks.user.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AccountDeleteRequest {
    @NotNull
    @Size(min = 1)
    private String password;
}
