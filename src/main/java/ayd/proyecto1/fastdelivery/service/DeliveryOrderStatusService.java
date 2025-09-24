package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.response.ContractStatusDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderStatusDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderStatusCrud;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderStatus;
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
public class DeliveryOrderStatusService {

    private final DeliveryOrderStatusCrud deliveryOrderStatusCrud;

    public ResponseSuccessfullyDto getDeliveryOrderStatusById(Integer id){
        Optional<DeliveryOrderStatus> deliveryOrderStatus = deliveryOrderStatusCrud.findById(id);

        if(deliveryOrderStatus.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Estado de la Orden no ha sido encontrado");
        }
        DeliveryOrderStatus deliveryOrderStatus1 = deliveryOrderStatus.get();
        DeliveryOrderStatusDto deliveryOrderStatusDto = DeliveryOrderStatusDto.builder().id(deliveryOrderStatus1.getId()).status(deliveryOrderStatus1.getStatus()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderStatusDto).build();
    }

    public ResponseSuccessfullyDto getAllDeliveryOrderStatus(){
        List<DeliveryOrderStatus> deliveryOrderStatuses = deliveryOrderStatusCrud.findAll();
        ArrayList<DeliveryOrderStatusDto> deliveryOrderStatusDtos = new ArrayList<>();

        deliveryOrderStatuses.forEach(deliveryOrderStatus -> {
            DeliveryOrderStatusDto deliveryOrderStatusDto = DeliveryOrderStatusDto.builder().id(deliveryOrderStatus.getId()).status(deliveryOrderStatus.getStatus()).build();
            deliveryOrderStatusDtos.add(deliveryOrderStatusDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderStatusDtos).build();
    }

}
