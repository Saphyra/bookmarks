package bookmarks.controller.request;

import javax.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull
    private String userName;

    @NotNull
    private String password;

    @NotNull
    private Boolean remember;
}
