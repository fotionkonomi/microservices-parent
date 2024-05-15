package al.run.userservice.business.service.interfaces;

import al.run.userservice.business.dto.UserDto;

public interface IUserService {

    UserDto save(UserDto user);
    UserDto findByEmail(String email);
    UserDto findById(Long id);
}
