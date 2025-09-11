package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.LoginDto;
import ayd.proyecto1.fastdelivery.dto.request.NewUserDto;
import ayd.proyecto1.fastdelivery.dto.request.ValidateCodeDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
public interface UserApi {


    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createUser(@RequestBody NewUserDto newUserDto);


    @PostMapping("/login")
    ResponseEntity<ResponseSuccessfullyDto> login(@RequestBody LoginDto loginDto);


    @PostMapping("/code")
    ResponseEntity<ResponseSuccessfullyDto> validateToken(@RequestBody ValidateCodeDto validateCodeDto);


    @PostMapping("/test/{nombre}")
    ResponseEntity<ResponseSuccessfullyDto> prueba(@RequestBody LoginDto loginDto, @RequestHeader(value = "token") String token,@RequestParam("id") String id,
                @PathVariable("nombre") String nombre);

}
