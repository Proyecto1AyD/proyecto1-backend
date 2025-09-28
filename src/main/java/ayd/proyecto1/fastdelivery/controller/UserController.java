package ayd.proyecto1.fastdelivery.controller;


import ayd.proyecto1.fastdelivery.controller.api.UserApi;
import ayd.proyecto1.fastdelivery.dto.request.*;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.dto.response.UserDto;
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
        log.info("GET user/code");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.validateCode(validateCodeDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> prueba(LoginDto loginDto, String token, String id, String nombre) {
        log.info("POST user/test");
        ResponseSuccessfullyDto responseSuccessfullyDto = ResponseSuccessfullyDto
                .builder()
                .code(HttpStatus.OK)
                .message("Alex ha sido agregado al sistema")
                .build();

        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    public ResponseEntity<ResponseSuccessfullyDto> getAllBussines(Integer authorization) {
        log.info("GET user/allBussines");
        userService.validateAuthorizationHeader(authorization);
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.getAllByIdRole(4);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    public ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryPersons (Integer authorization) {
        log.info("GET user/allDeliveryPerson");
        userService.validateAuthorizationHeader(authorization);
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.getAllByIdRole(3);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    public ResponseEntity<ResponseSuccessfullyDto> getAllCoordinators(Integer authorization) {
        log.info("GET user/allCoordinators");
        userService.validateAuthorizationHeader(authorization);
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.getAllByIdRole(2);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAll(Integer authorization) {
        log.info("GET user/all");
        userService.validateAuthorizationHeader(authorization);
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.getAllUsers();
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getUserById(Integer id, Integer userId) {
        log.info("GET /user/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.getUserById(id);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    public ResponseEntity<ResponseSuccessfullyDto> updateUser(UserDto updateUserDto, Integer authorization) {
        log.info("PUT user/");
        userService.validateAuthorizationHeader(authorization);
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.updateUser(updateUserDto);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    public ResponseEntity<ResponseSuccessfullyDto> deleteUser(Integer idUser, Integer authorization) {
        log.info("DELETE user/");
        userService.validateAuthorizationHeader(authorization);
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.deleteUser(idUser);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateAuthStatus(UpdateAuthStatusDto updateAuthStatusDto, Integer userId) {
        log.info("PUT user/authentication/status");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.updateAuthenticationStatus(userId,updateAuthStatusDto.getStatus());
        return null;
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> userForgotPassword(UserForgotPasswordDto userForgotPasswordDto) {
        log.info("POST user/forgot_password");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.userForgotPassword(userForgotPasswordDto);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> recoveryPassword(RecoveryPasswordDto recoveryPasswordDto, Integer userId) {
        log.info("POST user/recovery_password");
        ResponseSuccessfullyDto responseSuccessfullyDto = userService.recoveryPassword(recoveryPasswordDto,userId);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }


}
