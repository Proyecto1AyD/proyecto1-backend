package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.internal.NewReceiptDto;
import ayd.proyecto1.fastdelivery.dto.response.ReceiptInfoDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderAssignmentCrud;
import ayd.proyecto1.fastdelivery.repository.crud.ReceiptCrud;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderAssignment;
import ayd.proyecto1.fastdelivery.repository.entities.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReceiptService {


    private final ReceiptCrud receiptCrud;

    private final DeliveryOrderAssignmentCrud deliveryOrderAssignmentCrud;



    public ResponseSuccessfullyDto getAllReceipt(){

        List<Receipt> receiptList = receiptCrud.findAll();
        List<ReceiptInfoDto> receiptInfoDtoList = new ArrayList<>();

        receiptList.forEach(receipt -> {
            ReceiptInfoDto receiptInfoDto = ReceiptInfoDto.builder()
                    .receiptId(receipt.getId())
                    .deliveryOrderAssignmentId(receipt.getDeliveryOrderAssignment().getId())
                    .amount(receipt.getAmount())
                    .date(receipt.getDate())
                    .build();
            receiptInfoDtoList.add(receiptInfoDto);
        });


        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(receiptInfoDtoList).build();
    }

    public ResponseSuccessfullyDto getReceiptById(Integer id){

        Optional<Receipt> receiptOptional = receiptCrud.findById(id);

        if(receiptOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Recibo no encontrado");
        }

        Receipt receipt = receiptOptional.get();
        ReceiptInfoDto receiptInfoDto = ReceiptInfoDto.builder()
                .receiptId(receipt.getId())
                .deliveryOrderAssignmentId(receipt.getDeliveryOrderAssignment().getId())
                .amount(receipt.getAmount())
                .date(receipt.getDate())
                .build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(receiptInfoDto).build();
    }


    public ResponseSuccessfullyDto getByDeliveryPersonId(Integer deliveryPersonId){

        Optional<List<Receipt>> receiptOptional = receiptCrud.getByDeliveryPersonId(deliveryPersonId);

        if(receiptOptional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Error al encontrar recibos del usuario repartidor.");
        }

        List<Receipt> receiptList = receiptOptional.get();
        List<ReceiptInfoDto> receiptInfoDtoList = new ArrayList<>();

        receiptList.forEach(receipt -> {
            ReceiptInfoDto receiptInfoDto = ReceiptInfoDto.builder()
                    .receiptId(receipt.getId())
                    .deliveryOrderAssignmentId(receipt.getDeliveryOrderAssignment().getId())
                    .amount(receipt.getAmount())
                    .date(receipt.getDate())
                    .build();
            receiptInfoDtoList.add(receiptInfoDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(receiptInfoDtoList).build();
    }


    public ResponseSuccessfullyDto getByAssignmentId(Integer assignmentId){

        Optional<List<Receipt>> optionalReceiptList = receiptCrud.getByAssignmentId(assignmentId);

        if(optionalReceiptList.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Recibos no encontrados");
        }

        List<Receipt> receiptList = optionalReceiptList.get();
        List<ReceiptInfoDto> receiptInfoDtoList = new ArrayList<>();

        receiptList.forEach(receipt -> {
            ReceiptInfoDto receiptInfoDto = ReceiptInfoDto.builder()
                    .receiptId(receipt.getId())
                    .deliveryOrderAssignmentId(receipt.getDeliveryOrderAssignment().getId())
                    .amount(receipt.getAmount())
                    .date(receipt.getDate())
                    .build();
            receiptInfoDtoList.add(receiptInfoDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(receiptInfoDtoList).build();
    }

    public void createReceipt(NewReceiptDto newReceiptDto){

        Optional<DeliveryOrderAssignment> optionalDeliveryOrderAssignment = deliveryOrderAssignmentCrud.findById(newReceiptDto.getDeliveryOrderAssignmentId());

        if(optionalDeliveryOrderAssignment.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Error al buscar la orden de entrega");
        }

        DeliveryOrderAssignment deliveryOrderAssignment = optionalDeliveryOrderAssignment.get();

        Receipt receipt = new Receipt();
        receipt.setAmount(newReceiptDto.getAmount());
        receipt.setDate(newReceiptDto.getDate());
        receipt.setDeliveryOrderAssignment(deliveryOrderAssignment);
        try{
            receiptCrud.save(receipt);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar el recibo");
        }
    }


}
