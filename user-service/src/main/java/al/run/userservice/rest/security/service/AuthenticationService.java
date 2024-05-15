package al.run.userservice.rest.security.service;

import al.run.userservice.rest.security.model.AuthenticationRequest;
import al.run.userservice.rest.security.model.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    boolean validateToken(String token);
}
