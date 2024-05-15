package al.run.userservice.business.service.impl;

import al.run.userservice.business.dto.UserDto;
import al.run.userservice.business.service.interfaces.IUserService;
import al.run.userservice.exception.EmailViolationException;
import al.run.userservice.exception.UserNotFoundException;
import al.run.userservice.exception.UsernameViolationException;
import al.run.userservice.persistence.entity.User;
import al.run.userservice.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(UserDto dto) {
        if (dto.getUserId() == null) {
            if (this.userRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new EmailViolationException();
            }
            if (this.userRepository.findByUsername(dto.getUsername()).isPresent()) {
                throw new UsernameViolationException();
            }
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return modelMapper.map(this.userRepository.save(modelMapper.map(dto, User.class)), UserDto.class);
    }

    @Override
    public UserDto findByEmail(String email) {
        UserDto userDTO = findByEmailOrNull(email);
        if(userDTO != null) {
            return userDTO;
        }
        throw new UserNotFoundException();
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalUser = ((UserRepository) userRepository).findById(id);
        if(optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), UserDto.class);
        } else {
            throw new UserNotFoundException();
        }
    }

    private UserDto findByEmailOrNull(String email) {
        Optional<User> optionalUser = ((UserRepository) userRepository).findByEmail(email);
        if(optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), UserDto.class);
        } else {
            return null;
        }
    }


}
