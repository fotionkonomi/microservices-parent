package al.run.notificationservicemodule.services.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private Gender gender;
}
