package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.LoginDto;
import ayd.proyecto1.fastdelivery.dto.request.NewUserDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserApi {


    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createUser(@RequestBody NewUserDto newUserDto);


    @PostMapping("/login")
    ResponseEntity<ResponseSuccessfullyDto> login(@RequestBody LoginDto loginDto);

}
