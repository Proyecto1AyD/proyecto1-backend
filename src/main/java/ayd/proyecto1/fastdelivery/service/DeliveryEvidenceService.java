package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryEvidenceDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryEvidenceDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryEvidenceCrud;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderCrud;
import ayd.proyecto1.fastdelivery.repository.entities.Card;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryEvidence;
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
public class DeliveryEvidenceService {

    private final DeliveryEvidenceCrud deliveryEvidenceCrud;

    private final DeliveryOrderCrud deliveryOrderCrud;

    public ResponseSuccessfullyDto createDeliveryEvidence(NewDeliveryEvidenceDto newDeliveryEvidenceDto){
        DeliveryEvidence deliveryEvidence = new DeliveryEvidence();
        deliveryEvidence.setDeliveryOrder(deliveryOrderCrud.findById(newDeliveryEvidenceDto.getDeliveryOrderId()).get());
        deliveryEvidence.setPhotoUrl(newDeliveryEvidenceDto.getPhotoUrl());
        deliveryEvidence.setObservations(newDeliveryEvidenceDto.getObservations());

        try{
            deliveryEvidenceCrud.save(deliveryEvidence);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("Evidencia de Entrega creada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar la Evidencia de Entrega");
        }
    }

    public ResponseSuccessfullyDto getDeliveryEvidenceById(Integer id){
        Optional<DeliveryEvidence> deliveryEvidence = deliveryEvidenceCrud.findById(id);

        if(deliveryEvidence.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Evidencia de Entrega no ha sido encontrada");
        }
        DeliveryEvidence deliveryEvidence1 = deliveryEvidence.get();
        DeliveryEvidenceDto deliveryEvidenceDto = DeliveryEvidenceDto.builder().id(deliveryEvidence1.getId()).deliveryOrderId(deliveryEvidence1.getDeliveryOrder().getId()).photoUrl(deliveryEvidence1.getPhotoUrl()).observations(deliveryEvidence1.getObservations()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryEvidenceDto).build();
    }

    public ResponseSuccessfullyDto getDeliveryEvidenceByDeliveryOrderId(Integer id){
        List<DeliveryEvidence> deliveryEvidence = deliveryEvidenceCrud.getDeliveryEvidenceByIdDeliveryOrder(id);
        ArrayList<DeliveryEvidenceDto> deliveryEvidenceDtos = new ArrayList<>();

        deliveryEvidence.forEach(deliveryEvidence1 -> {
            DeliveryEvidenceDto deliveryEvidenceDto = DeliveryEvidenceDto.builder().id(deliveryEvidence1.getId()).deliveryOrderId(deliveryEvidence1.getDeliveryOrder().getId()).photoUrl(deliveryEvidence1.getPhotoUrl()).observations(deliveryEvidence1.getObservations()).build();
            deliveryEvidenceDtos.add(deliveryEvidenceDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryEvidenceDtos).build();
    }

    public ResponseSuccessfullyDto getAllDeliveryEvidence(){
        List<DeliveryEvidence> deliveryEvidences = deliveryEvidenceCrud.findAll();
        ArrayList<DeliveryEvidenceDto> deliveryEvidenceDtos = new ArrayList<>();

        deliveryEvidences.forEach(deliveryEvidence1 -> {
            DeliveryEvidenceDto deliveryEvidenceDto = DeliveryEvidenceDto.builder().id(deliveryEvidence1.getId()).deliveryOrderId(deliveryEvidence1.getDeliveryOrder().getId()).photoUrl(deliveryEvidence1.getPhotoUrl()).observations(deliveryEvidence1.getObservations()).build();
            deliveryEvidenceDtos.add(deliveryEvidenceDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryEvidenceDtos).build();
    }

    public ResponseSuccessfullyDto updateDeliveryEvidence(DeliveryEvidenceDto deliveryEvidenceDto){

        Optional<DeliveryEvidence> optionalDeliveryEvidence = deliveryEvidenceCrud.findById(deliveryEvidenceDto.getId());

        if(optionalDeliveryEvidence.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Evidencia de Entrega no ha sido encontrado");
        }
        DeliveryEvidence deliveryEvidence = optionalDeliveryEvidence.get();
        deliveryEvidence.setDeliveryOrder(deliveryOrderCrud.findById(deliveryEvidenceDto.getDeliveryOrderId()).get());
        deliveryEvidence.setPhotoUrl(deliveryEvidenceDto.getPhotoUrl());
        deliveryEvidence.setObservations(deliveryEvidenceDto.getObservations());

        try{
            deliveryEvidenceCrud.save(deliveryEvidence);
            log.info("Evidencia de Entrega actualizada...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Evidencia de Entrega actualizada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar una Evidencia de Entrega.");
        }
    }

    public ResponseSuccessfullyDto deleteDeliveryEvidence(Integer id){

        Optional<DeliveryEvidence> optionalDeliveryEvidence = deliveryEvidenceCrud.findById(id);

        if(optionalDeliveryEvidence.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Evidencia de Entrega no ha sido encontrada");
        }

        DeliveryEvidence deliveryEvidence = optionalDeliveryEvidence.get();
        try{
            deliveryEvidenceCrud.delete(deliveryEvidence);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Evidencia de Entrega eliminada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al eliminar la Evidencia de Entrega");
        }
    }
/*
    public Card getCardByIdCard(Integer id){

        Optional<Card> optionalCard = deliveryEvidenceCrud.findById(id);

        if(optionalCard.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Card not exists");
        }

        return optionalCard.get();
    }

    public List<Card> getAllCardList(){
        List<Card> cardList = deliveryEvidenceCrud.findAll();

        if(cardList.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Card's not exists");
        }

        return cardList;
    }*/
}
