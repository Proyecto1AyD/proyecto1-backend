package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewBussinesCardDto;
import ayd.proyecto1.fastdelivery.dto.response.BussinesCardDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.BusinessCardCrud;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderCrud;
import ayd.proyecto1.fastdelivery.repository.entities.BusinessCard;
import ayd.proyecto1.fastdelivery.repository.entities.Card;
import ayd.proyecto1.fastdelivery.repository.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BusinessCardService {

    private final BusinessCardCrud businessCardCrud;

    private final CardService cardService;

    private final UserService userService;

    private final DeliveryOrderCrud deliveryOrderCrud;

    private static final Integer BUSINESS_ID = 4;

    public ResponseSuccessfullyDto createBusinessCard(NewBussinesCardDto newBussinesCardDto){

        BusinessCard businessCard = new BusinessCard();

        User user = userService.getById(newBussinesCardDto.getIdBussines());
        if(!user.getRole().getId().equals(BUSINESS_ID)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"El usuario no corresponde a un Comercio Afiliado");
        }

        businessCard.setBusiness(user);

        if(!businessCardCrud.getBusinessCardByIdBusiness(user.getId()).isEmpty()){
            //Tarjeta Activa
            BusinessCard businessCardTemp = businessCardCrud.getBusinessCardActiveByIdBusiness(newBussinesCardDto.getIdBussines());
            if(businessCardTemp == null){
                throw new BusinessException(HttpStatus.NOT_FOUND,"El Comercio Afiliado no tiene Tarjetas activas.");
            } else if(businessCardTemp.getCard().getId().equals(newBussinesCardDto.getIdCard())){
                throw new BusinessException(HttpStatus.NOT_FOUND,"El Comercio Afiliado ya tiene la misma tarjeta activa.");
            }
            businessCardTemp.setActive(false);
            try{
                businessCardCrud.save(businessCardTemp);
                log.info("Comercio-Tarjeta con Id: "+businessCardTemp.getId()+" fue desactivado.");
            }catch (Exception exception){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar un Comercio-Tarjeta");
            }
        }

        Card card = cardService.getCardByIdCard(newBussinesCardDto.getIdCard());
        businessCard.setCard(card);
        businessCard.setLoyaltyActive(false);//Lealtad Desactivada por defecto
        businessCard.setCancellations(0);//Cancelaciones realizadas por defecto
        businessCard.setAffiliationDate(LocalDate.now());//Fecha actual
        businessCard.setActive(true);//Activación de Tarjeta

        try{
            businessCardCrud.save(businessCard);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("Comercio_Tarjeta fue creada exitosamente y Activado").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar el Comercio_Tarjeta");
        }
    }

    public ResponseSuccessfullyDto getBusinessCardById(Integer id){
        Optional<BusinessCard> optionalBusinessCard = businessCardCrud.findById(id);

        if(optionalBusinessCard.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Comercio_Tarjeta no ha sido encontrada");
        }
        BusinessCard businessCard = optionalBusinessCard.get();
        BussinesCardDto bussinesCardDto = BussinesCardDto.builder().id(businessCard.getId()).idBussines(businessCard.getBusiness().getId()).idCard(businessCard.getCard().getId()).loyaltyActive(businessCard.getLoyaltyActive()).cancellations(businessCard.getCancellations()).affiliationDate(businessCard.getAffiliationDate()).active(businessCard.getActive()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(bussinesCardDto).build();
    }


    public ResponseSuccessfullyDto getAllBusinessCardByIdBusiness(Integer id){
        List<BusinessCard> businessCardList = businessCardCrud.getBusinessCardByIdBusiness(id);
        ArrayList<BussinesCardDto> bussinesCardDtoList = new ArrayList<>();

        businessCardList.forEach(businessCard -> {
            BussinesCardDto bussinesCardDto = BussinesCardDto.builder().id(businessCard.getId()).idBussines(businessCard.getBusiness().getId()).idCard(businessCard.getCard().getId()).loyaltyActive(businessCard.getLoyaltyActive()).cancellations(businessCard.getCancellations()).affiliationDate(businessCard.getAffiliationDate()).active(businessCard.getActive()).build();
            bussinesCardDtoList.add(bussinesCardDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(bussinesCardDtoList).build();
    }

    public ResponseSuccessfullyDto updateBusinessCard(BussinesCardDto bussinesCardDto){

        Optional<BusinessCard> optionalBusinessCard = businessCardCrud.findById(bussinesCardDto.getId());

        if(optionalBusinessCard.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Comercio-Tarjeta no ha sido encontrada");
        }
        BusinessCard businessCard = optionalBusinessCard.get();
        businessCard.setBusiness(userService.getById(bussinesCardDto.getIdBussines()));
        businessCard.setCard(cardService.getCardByIdCard(bussinesCardDto.getIdCard()));
        businessCard.setLoyaltyActive(bussinesCardDto.getLoyaltyActive());
        businessCard.setCancellations(bussinesCardDto.getCancellations());
        businessCard.setAffiliationDate(bussinesCardDto.getAffiliationDate());
        businessCard.setActive(bussinesCardDto.getActive());

        try{
            businessCardCrud.save(businessCard);
            log.info("Comercio-Tarjeta actualizado...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Comercio-Tarjeta actualizado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar un Comercio-Tarjeta");
        }
    }

    public ResponseSuccessfullyDto deleteBusinessCard(Integer id){

        Optional<BusinessCard> optionalBusinessCard = businessCardCrud.findById(id);

        if(optionalBusinessCard.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Comercio-Tarjeta no ha sido encontrada");
        }

        BusinessCard businessCard = optionalBusinessCard.get();

        try{
            businessCardCrud.delete(businessCard);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Comercio-Tarjeta eliminado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al eliminar el Comercio-Tarjeta");
        }
    }

    public ResponseSuccessfullyDto validateNewCard(Integer idBusiness){
        //Tarjeta Activa
        List<Card> cardList = cardService.getAllCardList();
        BusinessCard businessCardTemp = businessCardCrud.getBusinessCardActiveByIdBusiness(idBusiness);

        if(businessCardTemp == null){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Comercio Afiliado no tiene Tarjetas activas.");
        }

        if (cardService.getCardByIdCard(businessCardTemp.getCard().getId() + 1) != null){
            Card cardTemp = cardService.getCardByIdCard(businessCardTemp.getCard().getId() + 1);//obtenemos card mayor
            Integer cantGuias = deliveryOrderCrud.getDeliveriesOrdersByIdBusiness(idBusiness).size();//obtenemos cantidad de guias
            if (cardTemp.getMinPackages()<= cantGuias){
                if (cardTemp.getMaxPackages()!=null){
                    if (cantGuias < cardTemp.getMaxPackages()){
                        NewBussinesCardDto newBussinesCardDto = NewBussinesCardDto.builder().idBussines(idBusiness).idCard(cardTemp.getId()).build();
                        return createBusinessCard(newBussinesCardDto);
                    }
                }else{
                    NewBussinesCardDto newBussinesCardDto = NewBussinesCardDto.builder().idBussines(idBusiness).idCard(cardTemp.getId()).build();
                    return createBusinessCard(newBussinesCardDto);
                }
            }
        }
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("No hay cambios en la tarjeta.").build();
    }

}
