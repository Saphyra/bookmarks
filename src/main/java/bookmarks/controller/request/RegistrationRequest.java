package bookmarks.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegistrationRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String userName;

    @NotNull
    @Size(min = 6, max = 30)
    private String password;
}
