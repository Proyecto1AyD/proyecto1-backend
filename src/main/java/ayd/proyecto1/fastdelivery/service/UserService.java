package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.LoginDto;
import ayd.proyecto1.fastdelivery.dto.request.NewUserDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateUserDto;
import ayd.proyecto1.fastdelivery.dto.request.ValidateCodeDto;
import ayd.proyecto1.fastdelivery.dto.response.BussinesInfoDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.dto.response.RoleInfoDto;
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

import java.util.ArrayList;
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

    private static final Integer ADMIN_ID = 1;


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


    public void validateAuthorizationHeader(Integer userId){
        Optional<User> optionalUser = userCrud.findById(userId);

        if(optionalUser.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Credenciales incorrectas");
        }

        User user = optionalUser.get();

        if(!user.getRole().getId().equals(ADMIN_ID)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"El usuario no cuenta con permisos para realizar la acción");
        }
    }

    public ResponseSuccessfullyDto getAllBussines(){

        List<User> bussines = userCrud.getUserByIdRole(4);
        List<BussinesInfoDto> bussinesInfoDtoList = new ArrayList<>();
        Role role = roleService.getRoleById(4);
        bussines.forEach(bussines1 -> {
            BussinesInfoDto bussineInfoDto = BussinesInfoDto.builder().userId(bussines1.getId()).name(bussines1.getName()).username(bussines1.getUsername()).role(role.getRole()).email(bussines1.getEmail()).phone(bussines1.getPhone()).address(bussines1.getAddress()).build();
            bussinesInfoDtoList.add(bussineInfoDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(bussinesInfoDtoList).build();
    }

    public ResponseSuccessfullyDto updateUserField(UpdateUserDto updateUserDto) {
        Optional<User> optionalUser = userCrud.findById(updateUserDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }
        User user = optionalUser.get();

        switch (updateUserDto.getFieldName().toLowerCase()) {
            case "name":
                user.setName(updateUserDto.getNewValue());
                break;
            case "email":
                user.setEmail(updateUserDto.getNewValue());
                break;
            case "phone":
                user.setPhone(updateUserDto.getNewValue());
                break;
            case "address":
                user.setAddress(updateUserDto.getNewValue());
                break;
            case "username":
                user.setUsername(updateUserDto.getNewValue());
                break;
            default:
                throw new BusinessException(HttpStatus.BAD_REQUEST, "Campo no válido para actualización.");
        }

        try {
            userCrud.save(user);
            log.info("Campo " + updateUserDto.getFieldName() + " del usuario fue actualizado correctamente.");

            return ResponseSuccessfullyDto.builder()
                    .code(HttpStatus.OK)
                    .message("El campo " + updateUserDto.getFieldName() + " fue actualizado correctamente")
                    .build();
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Error al intentar actualizar el campo del usuario.");
        }
    }

    public ResponseSuccessfullyDto deleteUser(Integer idUser){
        Optional<User> user = userCrud.findById(idUser);
        if (user.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }
        userCrud.deleteById(idUser);
        log.info("User was deleted successfully.");
        return ResponseSuccessfullyDto
                .builder()
                .code(HttpStatus.OK)
                .message("El usuario fué eliminado correctamente")
                .build();
    }

}
