package al.run.userservice.rest.security.userdetails;

import al.run.userservice.business.dto.UserDto;
import al.run.userservice.business.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements UserDetailsService {
    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDTO = this.userService.findByEmail(username);
        return MyUserDetails.builder()
                .id(userDTO.getUserId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(userDTO.getRole().name())
                .build();
    }
}
