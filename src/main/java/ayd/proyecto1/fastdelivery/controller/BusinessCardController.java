package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.BusinessCardApi;
import ayd.proyecto1.fastdelivery.dto.request.NewBussinesCardDto;
import ayd.proyecto1.fastdelivery.dto.response.BussinesCardDto;
import ayd.proyecto1.fastdelivery.dto.response.CardDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.BusinessCardService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BusinessCardController implements BusinessCardApi {

    private final BusinessCardService businessCardService;

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createBusinessCard(NewBussinesCardDto newBussinesCardDto, Integer userId) {
        log.info("POST /businessCard");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = businessCardService.createBusinessCard(newBussinesCardDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getBusinessCardById(Integer id, Integer userId) {
        log.info("GET /businessCard/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = businessCardService.getBusinessCardById(id);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllBusinessCardByIdBusiness(Integer id, Integer userId) {
        log.info("GET /businessCard/allByIdBusiness/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = businessCardService.getAllBusinessCardByIdBusiness(id);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateBusinessCard(BussinesCardDto bussinesCardDto, Integer userId) {
        log.info("PUT /businessCard");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = businessCardService.updateBusinessCard(bussinesCardDto);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteBusinessCard(Integer id, Integer userId) {
        log.info("DELETE /businessCard/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = businessCardService.deleteBusinessCard(id);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    public ResponseEntity<ResponseSuccessfullyDto> validateNewCard(Integer idBusiness, Integer userId) {
        log.info("POST /validateNewCard/{idBusiness}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = businessCardService.validateNewCard(idBusiness);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }
}
