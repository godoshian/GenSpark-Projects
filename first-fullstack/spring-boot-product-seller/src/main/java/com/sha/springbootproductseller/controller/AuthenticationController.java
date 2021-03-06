package com.sha.springbootproductseller.controller;

import com.sha.springbootproductseller.model.User;
import com.sha.springbootproductseller.service.AuthenticationService;
import com.sha.springbootproductseller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sa
 * @date 18.12.2021
 * @time 13:48
 */
// RestController implies RESTful (instead of regular controller)
@RestController
@RequestMapping("api/authentication")// pre-path (already "permit All" in SecurityConfig)
public class AuthenticationController
{
    // need this for sign-in
    @Autowired
    private AuthenticationService authenticationService;

    // this is for saving the user (registration)
    // remember: service layer talks to database!!
    @Autowired
    private UserService userService;

    @PostMapping("sign-up")//api/authentication/sign-up
    public ResponseEntity<?> signUp(@RequestBody User user)
    {
        // even though designated as unique, need to check
        if (userService.findByUsername(user.getUsername()).isPresent())
        {
            // this covers the 409 error
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("sign-in")//api/authentication/sign-in
    public ResponseEntity<?> signIn(@RequestBody User user)
    {
        // call authenticationService & return JWT as the response
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }
}