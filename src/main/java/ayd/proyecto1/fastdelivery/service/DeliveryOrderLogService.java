package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.response.ContractLogDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderLogDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderLogCrud;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderLog;
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
public class DeliveryOrderLogService {

    private final DeliveryOrderLogCrud deliveryOrderLogCrud;

    public ResponseSuccessfullyDto getDeliveryOrderLogById(Integer id){
        Optional<DeliveryOrderLog> deliveryOrderLog = deliveryOrderLogCrud.findById(id);

        if(deliveryOrderLog.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Bitácora de la Orden de Entrega no ha sido encontrado");
        }
        DeliveryOrderLog deliveryOrderLog1 = deliveryOrderLog.get();
        DeliveryOrderLogDto logDto = DeliveryOrderLogDto.builder().id(deliveryOrderLog1.getId()).idDeliveryOrder(deliveryOrderLog1.getDeliveryOrder().getId()).date(deliveryOrderLog1.getDate()).time(deliveryOrderLog1.getTime()).action(deliveryOrderLog1.getAction()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(logDto).build();
    }

    public ResponseSuccessfullyDto getDeliveryOrderLogByIdDeliveryOrder(Integer id){
        List<DeliveryOrderLog> contractLog = deliveryOrderLogCrud.getDeliveryOrderLogsByIdDeliveryOrder(id);
        ArrayList<DeliveryOrderLogDto> deliveryOrderLogDtos = new ArrayList<>();

        contractLog.forEach(deliveryOrderLog1 -> {
            DeliveryOrderLogDto logDto = DeliveryOrderLogDto.builder().id(deliveryOrderLog1.getId()).idDeliveryOrder(deliveryOrderLog1.getDeliveryOrder().getId()).date(deliveryOrderLog1.getDate()).time(deliveryOrderLog1.getTime()).action(deliveryOrderLog1.getAction()).build();
            deliveryOrderLogDtos.add(logDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderLogDtos).build();
    }

    public ResponseSuccessfullyDto getAllDeliveryOrderLogs(){
        List<DeliveryOrderLog> deliveryOrderLogs = deliveryOrderLogCrud.findAll();
        ArrayList<DeliveryOrderLogDto> deliveryOrderLogDtos = new ArrayList<>();

        deliveryOrderLogs.forEach(deliveryOrderLog1 -> {
            DeliveryOrderLogDto logDto = DeliveryOrderLogDto.builder().id(deliveryOrderLog1.getId()).idDeliveryOrder(deliveryOrderLog1.getDeliveryOrder().getId()).date(deliveryOrderLog1.getDate()).time(deliveryOrderLog1.getTime()).action(deliveryOrderLog1.getAction()).build();
            deliveryOrderLogDtos.add(logDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderLogDtos).build();
    }


}
