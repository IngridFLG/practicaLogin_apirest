package com.ufps.practicalogin.controllers;

import com.ufps.practicalogin.entities.User;
import com.ufps.practicalogin.model.requests.UserRequest;
import com.ufps.practicalogin.model.responses.UserResponse;
import com.ufps.practicalogin.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class UserControllers {
    private final String secretKey = "mySecretKey";

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public UserResponse login(@RequestBody UserRequest userRequest){
        UserResponse userResponse = new UserResponse();
        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());
        if(user.isPresent()){
            if (user.get().getPass().equals(userRequest.getPass())) {
                BeanUtils.copyProperties(userResponse, user);
                userResponse.setMensaje("Bienvenido");
                userResponse.setLoginValido(true);
                userResponse.setEmail(user.get().getEmail());
                userResponse.setUsername(user.get().getUsername());
                String token = generarToken(userResponse.getUsername());
                userResponse.setToken(token);

            }
            else{
                userResponse.setLoginValido(false);
                userResponse.setMensaje("Usuario o contraseña invalidos");
            }
        }else{
            userResponse.setLoginValido(false);
            userResponse.setMensaje("El usuario no se encuentra registrado");
        }
        return userResponse;
    }


    private String generarToken(String username) {

        Date expirationDate = new Date(System.currentTimeMillis() + 3600000); // 1 hora

        String token =  Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        return token;
    }

}
