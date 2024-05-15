package al.run.userservice.rest.security.service;

import al.run.userservice.rest.security.exception.MyBadCredentialsException;
import al.run.userservice.rest.security.model.AuthenticationRequest;
import al.run.userservice.rest.security.model.AuthenticationResponse;
import al.run.userservice.rest.security.userdetails.MyUserDetails;
import al.run.userservice.rest.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtUtil.generateToken((MyUserDetails) userDetails));
            return authenticationResponse;
        } catch (BadCredentialsException e) {
            throw new MyBadCredentialsException();
        }
    }

    @Override
    public boolean validateToken(String token) {
        UserDetails userDetailsExtractedByToken = jwtUtil.extractUserFromToken(token);
        String username = userDetailsExtractedByToken.getUsername();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        return jwtUtil.validateToken(token, userDetails);
    }
}

