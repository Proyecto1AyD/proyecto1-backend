package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewCardDto;
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

    public ResponseSuccessfullyDto updateCard(CardDto cardDto){

        Optional<Card> optionalCard = cardCrud.findById(cardDto.getId());

        if(optionalCard.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El usuario no ha sido encontrado");
        }
        Card card = optionalCard.get();
        card.setTitle(card.getTitle());
        card.setShippingPrice(card.getShippingPrice());
        card.setDiscount(card.getDiscount());
        card.setCancellationPayment(card.getCancellationPayment());
        card.setFreeCancellations(card.getFreeCancellations());
        card.setMinPackages(card.getMinPackages());
        card.setMaxPackages(card.getMaxPackages());

        try{
            cardCrud.save(card);
            log.info("Tarjeta actualizada...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Tarjeta actualizada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar una Tarjeta.");
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

    public Card getCardByIdCard(Integer id){

        Optional<Card> optionalCard = cardCrud.findById(id);

        if(optionalCard.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Card not exists");
        }

        return optionalCard.get();
    }

    public List<Card> getAllCardList(){
        List<Card> cardList = cardCrud.findAll();

        if(cardList.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Card's not exists");
        }

        return cardList;
    }
}
