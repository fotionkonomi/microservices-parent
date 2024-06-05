package al.run.notificationservicemodule.services.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
}
