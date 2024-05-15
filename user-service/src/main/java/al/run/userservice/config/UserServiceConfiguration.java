package al.run.userservice.config;

import al.run.userservice.business.dto.UserDto;
import al.run.userservice.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class UserServiceConfiguration {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
        resource.addBasenames("exception_messages");
        return resource;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getRole().getRole(), UserDto::setRole));
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getRole().getRole(), UserDto::setRole));
        modelMapper.typeMap(UserDto.class, User.class)
                .addMappings(mapper -> mapper.map(src -> src.getRole(), User::setRoleByRoleEnum));
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
