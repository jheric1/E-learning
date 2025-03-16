package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.configurations.JwtUtil;
import com.nbp.onlinelearning.model.LoginRequest;
import com.nbp.onlinelearning.model.LoginResponse;
import com.nbp.onlinelearning.model.User;
import com.nbp.onlinelearning.model.UserActivityDto;
import com.nbp.onlinelearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.createUser(user);
    }

    public User updateUser(User user) {
        if (user.getId() == 0) {
            return null;
            //TODO throw exception because on update ID has to be provided
        }
        return userRepository.updateUser(user);
    }

    public boolean deleteUser(long id) {
        if (id == 0) {
            return false;
            //TODO SAME AS ABOVE
        }

        return userRepository.deleteUser(id);
    }

    public List<User> findAllStudents() {
        return userRepository.findAllStudents();
    }

    public void resetPassword(long userId, String password) {
        User user = userRepository.findUserById(userId);
        if(user == null){
            throw new BadCredentialsException("User with id: " + userId + " not found!");
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.updateUser(user);
    }

    public LoginResponse loginUserService(LoginRequest loginRequest) {
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        UserDetails userDetails = identityService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return new LoginResponse(userRepository.findUserByEmail(loginRequest.getEmail()), token);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("User " + username + " is disabled", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password", e);
        }
    }

    public List<User> findAllInstructors() {
        return userRepository.findAllInstructors();
    }

    public User getUserFromToken(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<UserActivityDto> getUserActivity(){
        return userRepository.getLatestUserActivity();
    }
}
