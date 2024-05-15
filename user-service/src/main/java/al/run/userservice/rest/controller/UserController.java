package al.run.userservice.rest.controller;

import al.run.userservice.business.dto.UserDto;
import al.run.userservice.business.service.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto, HttpServletRequest request) throws URISyntaxException {
        UserDto createdUser = this.userService.save(userDto);
        return ResponseEntity.created(new URI(request.getRequestURI())).body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
}
