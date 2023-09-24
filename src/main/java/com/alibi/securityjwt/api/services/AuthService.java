package com.alibi.securityjwt.api.services;

import com.alibi.securityjwt.api.dtos.JwtRequest;
import com.alibi.securityjwt.api.dtos.JwtResponse;
import com.alibi.securityjwt.api.dtos.RegistrationUserDTO;
import com.alibi.securityjwt.api.dtos.UserDto;
import com.alibi.securityjwt.api.exceptions.BadRequestException;
import com.alibi.securityjwt.store.entities.User;
import com.alibi.securityjwt.api.exceptions.AppError;
import com.alibi.securityjwt.api.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AppError("Username or password might be wrong");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new BadRequestException("Password aren't the same");
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            throw new AppError("A user with the same username or email already exists.");
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
