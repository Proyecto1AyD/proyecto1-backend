package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.LoginDto;
import ayd.proyecto1.fastdelivery.dto.request.NewUserDto;
import ayd.proyecto1.fastdelivery.dto.request.ValidateCodeDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.dto.response.UserInfoDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.UserCrud;
import ayd.proyecto1.fastdelivery.repository.crud.ValidationCodeCrud;
import ayd.proyecto1.fastdelivery.repository.entities.Role;
import ayd.proyecto1.fastdelivery.repository.entities.User;
import ayd.proyecto1.fastdelivery.repository.entities.ValidationCode;
import ayd.proyecto1.fastdelivery.utils.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserCrud userCrud;

    private final GeneralUtils utils;

    private final EmailService emailService;

    private final RoleService roleService;

    private final ValidationCodeService validationCodeService;


    public ResponseSuccessfullyDto createUser(NewUserDto newUserDto){
        User user = new User();
        user.setName(newUserDto.getNombre());
        user.setEmail(newUserDto.getEmail());
        user.setAddress(newUserDto.getDireccion());
        user.setPhone(newUserDto.getTelefono());
        user.setUsername(newUserDto.getUsername());
        user.setPassword(utils.hashPassword(newUserDto.getPassword()));
        user.setPhone(newUserDto.getTelefono());
        user.setAddress(newUserDto.getDireccion());
        Role role = roleService.getRoleById(newUserDto.getRol());
        user.setRole(role);

        try{
            userCrud.save(user);
            log.info("User was saved successfully.");
            return ResponseSuccessfullyDto
                    .builder()
                    .code(HttpStatus.CREATED)
                    .message("El usuario fué creado correctamente")
                    .build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al intentar guardar añ usuario.");
        }
    }

    public ResponseSuccessfullyDto login(LoginDto loginDto){

        Optional<User> optionalUser = userCrud.getUserByUsername(loginDto.getUsername());

        if(optionalUser.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Credenciales incorrectas");
        }

        User user = optionalUser.get();

        if(!utils.validatePassword(loginDto.getPassword(), user.getPassword())){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Crendenciales incorrectas");
        }

        if(user.getAuthentication()){
            log.info("Send code to user...");
            sendCodeToUser(user);

            return ResponseSuccessfullyDto.builder()
                    .code(HttpStatus.OK)
                    .message("Se ha enviado un código de verificación a su correo electrónico!")
                    .build();
        }

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole().getRole())
                .autentication(user.getAuthentication())
                .build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Inicio de sesión exitoso").body(userInfoDto).build();
    }


    public void sendCodeToUser(User user){
        String code = utils.generateVerificationCode();
        emailService.sendEmail(user.getEmail(),"Código de Verificación","El codigo es: "+code);

        ValidationCode validationCode = new ValidationCode();
        validationCode.setUser(user);
        validationCode.setCode(code);
        validationCode.setExpirationTime(utils.createExpirationDate(2));
        validationCodeService.createValidationCode(validationCode);
    }

    public ResponseSuccessfullyDto validateCode(ValidateCodeDto validateCodeDto){
        return validationCodeService.getValidationCodeByUser(validateCodeDto);
    }
}
