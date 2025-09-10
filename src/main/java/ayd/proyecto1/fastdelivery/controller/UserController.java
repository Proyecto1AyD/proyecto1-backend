package ayd.proyecto1.fastdelivery.controller;


import ayd.proyecto1.fastdelivery.controller.api.UserApi;
import ayd.proyecto1.fastdelivery.dto.request.LoginDto;
import ayd.proyecto1.fastdelivery.dto.request.NewUserDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
