package com.zigzagcoding.controller;

import com.zigzagcoding.config.JwtProvider;
import com.zigzagcoding.model.Cart;
import com.zigzagcoding.model.USER_ROLE;
import com.zigzagcoding.model.User;
import com.zigzagcoding.repository.CartRepository;
import com.zigzagcoding.repository.UserRepository;
import com.zigzagcoding.request.LoginRequest;
import com.zigzagcoding.response.AuthResponse;
import com.zigzagcoding.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CartRepository cartRepository;



    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw new Exception("Email already exist");
        }

        if(user.getPassword() == null){
            throw new Exception("Password cannot be null");
        }
        User createUser = new User();
        createUser.setFullName(user.getFullName());
        createUser.setEmail(user.getEmail());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setRole(user.getRole());

        User savedUser = userRepository.save(createUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Registered Sucessfully!");
        authResponse.setRole(savedUser.getRole());
        authResponse.setJwt(jwt);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest req){
      String userName = req.getEmail();
      String password = req.getPassword();


      Authentication authentication = authenticate(userName, password);
      String jwt = jwtProvider.generateToken(authentication);

      Collection<? extends GrantedAuthority > authorities = authentication.getAuthorities();
      String roles = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

      AuthResponse authResponse = new AuthResponse();
      authResponse.setJwt(jwt);
      authResponse.setMessage("Logged in  Sucessfully!");

      authResponse.setRole(USER_ROLE.valueOf(roles));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails;
        try {
            userDetails = customUserDetailsService.loadUserByUsername(userName);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid Username....");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password....");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
