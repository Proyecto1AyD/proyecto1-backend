package ayd.proyecto1.fastdelivery.service;


import ayd.proyecto1.fastdelivery.dto.request.ValidateCodeDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.dto.response.UserInfoDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.ValidationCodeCrud;
import ayd.proyecto1.fastdelivery.repository.entities.ValidationCode;
import ayd.proyecto1.fastdelivery.utils.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidationCodeService {

    private final ValidationCodeCrud validationCodeCrud;

    private final GeneralUtils generalUtils;


    public void createValidationCode(ValidationCode validationCode){

        Optional<ValidationCode> optionalValidationCode = validationCodeCrud.getByUser(validationCode.getUser().getId());

        if(optionalValidationCode.isEmpty()){
            ValidationCode newValidationCode = new ValidationCode();
            newValidationCode.setCode(validationCode.getCode());
            newValidationCode.setUser(validationCode.getUser());
            newValidationCode.setAttempts(0);
            newValidationCode.setIsUsed(Boolean.FALSE);
            newValidationCode.setExpirationTime(validationCode.getExpirationTime());
            validationCodeCrud.save(newValidationCode);

        }else{
            ValidationCode actualValidationCode = optionalValidationCode.get();

            actualValidationCode.setCode(validationCode.getCode());
            actualValidationCode.setExpirationTime(validationCode.getExpirationTime());
            actualValidationCode.setIsUsed(Boolean.FALSE);
            actualValidationCode.setAttempts(0);

            validationCodeCrud.save(actualValidationCode);
        }

    }


    public ResponseSuccessfullyDto getValidationCodeByUser(ValidateCodeDto validateCodeDto){

        Optional<ValidationCode> optionalValidationCode = validationCodeCrud.getByUser(validateCodeDto.getUserId());

        if(optionalValidationCode.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El usuario no cuenta con un código de validación");
        }

        ValidationCode validationCode = optionalValidationCode.get();

        if(!validateCodeDto.getCode().equals(validationCode.getCode())){
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "El código no le pertenece al usuario que intenta iniciar sesión.");
        }

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(validationCode.getUser().getId())
                .username(validationCode.getUser().getUsername())
                .role(validationCode.getUser().getRole().getRole())
                .autentication(validationCode.getUser().getAuthentication())
                .build();

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Inicio de sesión exitoso")
                .body(userInfoDto)
                .build();
    }

}
