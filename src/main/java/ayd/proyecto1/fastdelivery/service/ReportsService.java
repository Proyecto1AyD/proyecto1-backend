package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.response.ComissionDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderCrud;
import ayd.proyecto1.fastdelivery.repository.crud.ReceiptCrud;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ReportsService {

    private final DeliveryOrderCrud deliveryOrderCrud;

    private final ReceiptCrud receiptCrud;



    public ResponseSuccessfullyDto deliveryOrdersByStatus(Integer status){
      log.info("Report: delivery orders by status");
        Optional<List<DeliveryOrder>> optionalDeliveryOrders = deliveryOrderCrud.getByStatus(status);

        if(optionalDeliveryOrders.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Error en la consulta de entregas por su estado.");
        }

        List<DeliveryOrder> deliveryOrderList = optionalDeliveryOrders.get();
        List<DeliveryOrderDto> deliveryOrderDtoList = new ArrayList<>();

        deliveryOrderList.forEach(deliveryOrder -> {

            DeliveryOrderDto deliveryOrderDto = DeliveryOrderDto.builder()
                    .id(deliveryOrder.getId())
                    .recipient(deliveryOrder.getRecipient())
                    .address(deliveryOrder.getAddress())
                    .date(deliveryOrder.getDate())
                    .time(deliveryOrder.getTime())
                    .idDeliveryOrderStatus(deliveryOrder.getDeliveryOrderStatus().getId())
                    .description(deliveryOrder.getDescription())
                    .build();

            deliveryOrderDtoList.add(deliveryOrderDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderDtoList).build();
    }


    public ResponseSuccessfullyDto getByAssignmentIdAndDate(Integer deliveryId, Date initDate, Date finishDate){

        Optional<List<ComissionDto>> optionalComissionDtos = receiptCrud.getByAssignmentIdAndDate(deliveryId, initDate, finishDate);

        if(optionalComissionDtos.isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error en la consulta de comisiones de repartidor");
        }

        return ResponseSuccessfullyDto.builder().body(optionalComissionDtos.get()).build();
    }






}
