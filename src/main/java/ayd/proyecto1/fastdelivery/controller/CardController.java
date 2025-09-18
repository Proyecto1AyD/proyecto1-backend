package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.BranchApi;
import ayd.proyecto1.fastdelivery.controller.api.CardApi;
import ayd.proyecto1.fastdelivery.dto.request.NewBranchDto;
import ayd.proyecto1.fastdelivery.dto.request.NewCardDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateEntityDto;
import ayd.proyecto1.fastdelivery.dto.response.BranchDto;
import ayd.proyecto1.fastdelivery.dto.response.CardDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.CardService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class CardController implements CardApi {

    private final CardService cardService;

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createCard(NewCardDto newCardDto, Integer userId) {
        log.info("POST /card");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = cardService.createCard(newCardDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllCard(Integer userId) {
        log.info("GET /card/all");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = cardService.getAllCard();
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getCardById(Integer id, Integer userId) {
        log.info("GET /card/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = cardService.getCardById(id);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateCard(CardDto cardDto, Integer userId) {
        log.info("PUT /card");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = cardService.updateCard(cardDto);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteBranch(Integer id, Integer userId) {
        log.info("DELETE /card/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = cardService.deleteCard(id);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }
}
