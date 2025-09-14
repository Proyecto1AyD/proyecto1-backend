package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewCardDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateEntityDto;
import ayd.proyecto1.fastdelivery.dto.response.CardDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.CardCrud;
import ayd.proyecto1.fastdelivery.repository.entities.Card;
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
public class CardService {

    private final CardCrud cardCrud;

    public ResponseSuccessfullyDto createCard(NewCardDto newCardDto){
        Card card = new Card();
        card.setTitle(newCardDto.getTitle());
        card.setShippingPrice(newCardDto.getShippingPrice());
        card.setDiscount(newCardDto.getDiscount());
        card.setCancellationPayment(newCardDto.getCancellationPayment());
        card.setFreeCancellations(newCardDto.getFreeCancellations());
        card.setMinPackages(newCardDto.getMinPackages());
        card.setMaxPackages(newCardDto.getMaxPackages());

        try{
            cardCrud.save(card);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("Tarjeta creada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar la tarjeta");
        }
    }

    public ResponseSuccessfullyDto getCardById(Integer id){
        Optional<Card> card = cardCrud.findById(id);

        if(card.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La tarjeta no ha sido encontrada");
        }
        Card cardTemp = card.get();
        CardDto cardDto = CardDto.builder().id(cardTemp.getId()).title(cardTemp.getTitle()).shippingPrice(cardTemp.getShippingPrice()).discount(cardTemp.getDiscount()).cancellationPayment(cardTemp.getCancellationPayment()).freeCancellations(cardTemp.getFreeCancellations()).minPackages(cardTemp.getMinPackages()).maxPackages(cardTemp.getMaxPackages()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(cardDto).build();
    }

    public ResponseSuccessfullyDto getAllCard(){
        List<Card> cardList = cardCrud.findAll();
        ArrayList<CardDto> cardDtoList = new ArrayList<>();

        cardList.forEach(cardTemp -> {
            CardDto cardDto = CardDto.builder().id(cardTemp.getId()).title(cardTemp.getTitle()).shippingPrice(cardTemp.getShippingPrice()).discount(cardTemp.getDiscount()).cancellationPayment(cardTemp.getCancellationPayment()).freeCancellations(cardTemp.getFreeCancellations()).minPackages(cardTemp.getMinPackages()).maxPackages(cardTemp.getMaxPackages()).build();
            cardDtoList.add(cardDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(cardDtoList).build();
    }

    public ResponseSuccessfullyDto updateCard(UpdateEntityDto cardDto){

        Optional<Card> optionalCard = cardCrud.findById(cardDto.getId());
        if (optionalCard.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Tarjeta no encontrada.");
        }
        Card card = optionalCard.get();

        switch (cardDto.getFieldName()) {
            case "title":
                card.setTitle(cardDto.getNewValue());
                break;
            case "shippingPrice":
                Double hola = Double.valueOf(cardDto.getNewValue());
                card.setShippingPrice(Double.valueOf(cardDto.getNewValue()));
                break;
            case "discount":
                card.setDiscount(Integer.valueOf(cardDto.getNewValue()));
                break;
            case "cancellationPayment":
                card.setCancellationPayment(Integer.valueOf(cardDto.getNewValue()));
                break;
            case "freeCancellations":
                card.setFreeCancellations(Integer.valueOf(cardDto.getNewValue()));
                break;
            case "minPackages":
                card.setMinPackages(Integer.valueOf(cardDto.getNewValue()));
                break;
            case "maxPackages":
                card.setMaxPackages(Integer.valueOf(cardDto.getNewValue()));
                break;
            default:
                throw new BusinessException(HttpStatus.BAD_REQUEST, "Campo no válido para actualización o dato no ingresado correctamente");
        }

        try {
            cardCrud.save(card);
            log.info("Campo " + cardDto.getFieldName() + " de la tarjeta fue actualizado correctamente.");

            return ResponseSuccessfullyDto.builder()
                    .code(HttpStatus.OK)
                    .message("El campo " + cardDto.getFieldName() + " fue actualizado correctamente")
                    .build();
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Error al intentar actualizar el campo de la tarjeta.");
        }
    }

    public ResponseSuccessfullyDto deleteCard(Integer cardId){

        Optional<Card> optionalCard = cardCrud.findById(cardId);

        if(optionalCard.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La tarjeta no ha sido encontrada");
        }

        Card card = optionalCard.get();
        log.info(card.getTitle());
        try{
            cardCrud.delete(card);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Tarjeta eliminada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al eliminar la tarjeta");
        }
    }
}
