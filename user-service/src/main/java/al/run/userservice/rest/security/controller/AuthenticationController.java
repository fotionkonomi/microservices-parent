package al.run.userservice.rest.security.controller;

import al.run.userservice.exception.UserNotFoundException;
import al.run.userservice.rest.security.model.AuthenticationRequest;
import al.run.userservice.rest.security.model.AuthenticationResponse;
import al.run.userservice.rest.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/validate/token")
    public ResponseEntity<Boolean> validateToken(@RequestBody String token) {
        Boolean tokenValid = authenticationService.validateToken(token);
        return ResponseEntity.status(tokenValid ? HttpStatus.ACCEPTED : HttpStatus.UNAUTHORIZED).body(tokenValid);
    }
}
