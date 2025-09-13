package ayd.proyecto1.fastdelivery.controller;


import ayd.proyecto1.fastdelivery.controller.api.UserApi;
import ayd.proyecto1.fastdelivery.dto.request.LoginDto;
import ayd.proyecto1.fastdelivery.dto.request.NewUserDto;
import ayd.proyecto1.fastdelivery.dto.request.ValidateCodeDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createUser(NewUserDto newUserDto) {
        log.info("POST /user  -- create user");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.createUser(newUserDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> login(LoginDto loginDto) {
        log.info("POST user/login");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.login(loginDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> validateToken(ValidateCodeDto validateCodeDto) {
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.validateCode(validateCodeDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> prueba(LoginDto loginDto, String token, String id, String nombre) {
        log.info("user/test");
        ResponseSuccessfullyDto responseSuccessfullyDto = ResponseSuccessfullyDto
                .builder()
                .code(HttpStatus.OK)
                .message("Alex ha sido agregado al sistema")
                .build();

        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }


}
