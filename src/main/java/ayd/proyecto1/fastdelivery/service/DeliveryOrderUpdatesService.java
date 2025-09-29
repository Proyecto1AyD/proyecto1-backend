package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.internal.NewReceiptDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.*;
import ayd.proyecto1.fastdelivery.repository.entities.*;
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
public class DeliveryOrderUpdatesService {

    private final UserCrud userCrud;

    private final DeliveryOrderCrud deliveryOrderCrud;

    private final DeliveryOrderStatusCrud deliveryOrderStatusCrud;

    private final ReceiptService receiptService;

    private final DeliveryOrderAssignmentCrud deliveryOrderAssignmentCrud;

    private final DeliveryOrderAssignmentService deliveryOrderAssignmentService;

    private final BusinessCardCrud businessCardCrud;

    private final DeliveryPersonCrud deliveryPersonCrud;

    private static final Integer ORDER_DELIVERED_STATUS = 6;

    private static final Integer ORDER_PENDING_STATUS = 5;

    public ResponseSuccessfullyDto deliveredDeliveryOrder(Integer deliveryOrderId) {
        // Verificar Orden de Entrega
        Optional <DeliveryOrder> optionalDeliveryOrder = deliveryOrderCrud.findById(deliveryOrderId);
        verifyIsEmpty(optionalDeliveryOrder, "Orden de Entrega");
        DeliveryOrder deliveryOrder = optionalDeliveryOrder.get();
        //Comercio-Tarjeta
            //USER
        User user = deliveryOrder.getBusiness();
        log.info("userID:" + user.getName());
        verifyIsEmpty(user, "Usuario");
            //Comercio-Tarjeta
        BusinessCard businessCard = businessCardCrud.getBusinessCardActiveByIdBusiness(user.getId());
        verifyIsEmpty(businessCard, "Tarjeta-Comercio");
            //TARJETA
        Card card = businessCard.getCard();
        verifyIsEmpty(card, "Tarjeta");
        //Asignacion de la Orden
        DeliveryOrderAssignment deliveryOrderAssignment = deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryOrderActive(deliveryOrder.getId(), true);
        verifyIsEmpty(deliveryOrderAssignment, "Asignación de Orden de Entrega");
        // Verificar Repartidor
            //REPARTIDOR
        DeliveryPerson deliveryPerson = deliveryOrderAssignment.getDeliveryPerson();
        log.info("userID:", deliveryPerson.getId(), "wallet:", deliveryPerson.getWallet());
        verifyIsEmpty(deliveryPerson, "Repartidor");
            //CONTRATO
        Contract contract = deliveryPerson.getContract();
        verifyIsEmpty(contract, "Contrato");

        //VERIFICAR ESTADO DE LA ORDEN DE ENTREGAR
        if (deliveryOrder.getDeliveryOrderStatus().getId().equals(ORDER_PENDING_STATUS)){

            try{
                //REPARTIDOR
                log.info("Precio Envio de tarjeta "+card.getShippingPrice());
                log.info("Comision "+contract.getCommission());
                log.info("Porcentaje de Comisión "+(contract.getCommission().doubleValue() /100.0));
                Double monto = card.getShippingPrice() * (contract.getCommission().doubleValue() /100.0);
                deliveryPerson.setWallet(deliveryPerson.getWallet()+monto);
                deliveryPerson.setAvailable(true);
                DeliveryPerson deliveryPerson1 = deliveryPersonCrud.save(deliveryPerson);
                //ASIGNACION
                deliveryOrderAssignment.setActive(false);
                deliveryOrderAssignmentCrud.save(deliveryOrderAssignment);
                //ORDEN
                deliveryOrder.setDeliveryOrderStatus(deliveryOrderStatusCrud.findById(ORDER_DELIVERED_STATUS).get());
                deliveryOrderCrud.save(deliveryOrder);
                //RECIBO
                LocalDate date = LocalDate.now();
                NewReceiptDto newReceiptDto = NewReceiptDto.builder().deliveryOrderAssignmentId(deliveryOrderAssignment.getId()).amount(monto).date(date).build();
                receiptService.createReceipt(newReceiptDto);
                deliveryOrderAssignmentService.autoAssignmentDeliveryPerson(deliveryPerson1);
                return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("Entrega finalizada exitosamente.").build();
            }catch (Exception exception){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar la Asignacion Orden de Entrega");
            }
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST, "No es posible entrega el paquete, porque no esta en el estado Pendiente de Entrega");
        }
    }
/*
    public ResponseSuccessfullyDto createDeliveryOrder(NewDeliveryOrderDto newDeliveryOrderDto){
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setBusiness(userService.getById(newDeliveryOrderDto.getIdBusiness()));
        deliveryOrder.setRecipient(newDeliveryOrderDto.getRecipient());
        deliveryOrder.setAddress(newDeliveryOrderDto.getAddress());
        deliveryOrder.setDate(newDeliveryOrderDto.getDate());
        deliveryOrder.setTime(newDeliveryOrderDto.getTime());
        deliveryOrder.setDeliveryOrderStatus(deliveryOrderStatusService.getDeliveryOrderStatusByIdDeliveryOrderStatus(ORDER_CREATED_STATUS));
        deliveryOrder.setDescription(newDeliveryOrderDto.getDescription());
        try{
            DeliveryOrder deliveryOrderSaved = deliveryOrderCrud.save(deliveryOrder);
            String messageResponse = "Orden de Entrega creada exitosamente";
            try {
                deliveryOrderAssignmentService.autoAssignmentDeliveryOrder(deliveryOrderSaved);
            } catch (Exception exception) {
                log.warn(exception.getMessage());
                messageResponse += "No hay repartidores disponibles en este momento, asignaremos su orden lo más pronto posible.";
            }
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message(messageResponse).build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar la Orden de Entrega");
        }
    }

    public ResponseSuccessfullyDto getDeliveryOrderById(Integer id){
        DeliveryOrder deliveryOrder1 = getDeliveryOrderByIdDeliveryOrder(id);
        DeliveryOrderDto deliveryOrderDto = DeliveryOrderDto.builder().id(deliveryOrder1.getId()).idBusiness((deliveryOrder1.getBusiness().getId())).recipient(deliveryOrder1.getRecipient()).address(deliveryOrder1.getAddress()).date(deliveryOrder1.getDate()).time(deliveryOrder1.getTime()).idDeliveryOrderStatus(deliveryOrder1.getDeliveryOrderStatus().getId()).description(deliveryOrder1.getDescription()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderDto).build();
    }

    public ResponseSuccessfullyDto getDeliveryOrderByIdBusiness(Integer idBusiness){
        List<DeliveryOrder> deliveryOrderList = deliveryOrderCrud.getDeliveriesOrdersByIdBusiness(idBusiness);
        ArrayList<DeliveryOrderDto> deliveryOrderDtos = new ArrayList<>();

        deliveryOrderList.forEach(deliveryOrder1 -> {
            DeliveryOrderDto deliveryOrderDto = DeliveryOrderDto.builder().id(deliveryOrder1.getId()).idBusiness((deliveryOrder1.getBusiness().getId())).recipient(deliveryOrder1.getRecipient()).address(deliveryOrder1.getAddress()).date(deliveryOrder1.getDate()).time(deliveryOrder1.getTime()).idDeliveryOrderStatus(deliveryOrder1.getDeliveryOrderStatus().getId()).description(deliveryOrder1.getDescription()).build();
            deliveryOrderDtos.add(deliveryOrderDto);
        });
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderDtos).build();
    }

    public ResponseSuccessfullyDto getDeliveryOrderByIdDeliveryOrderStatus(Integer idDeliveryOrderStatus){
        List<DeliveryOrder> deliveryOrderList = deliveryOrderCrud.getDeliveriesOrdersByIdDeliveryOrderStatus(idDeliveryOrderStatus);
        ArrayList<DeliveryOrderDto> deliveryOrderDtos = new ArrayList<>();

        deliveryOrderList.forEach(deliveryOrder1 -> {
            DeliveryOrderDto deliveryOrderDto = DeliveryOrderDto.builder().id(deliveryOrder1.getId()).idBusiness((deliveryOrder1.getBusiness().getId())).recipient(deliveryOrder1.getRecipient()).address(deliveryOrder1.getAddress()).date(deliveryOrder1.getDate()).time(deliveryOrder1.getTime()).idDeliveryOrderStatus(deliveryOrder1.getDeliveryOrderStatus().getId()).description(deliveryOrder1.getDescription()).build();
            deliveryOrderDtos.add(deliveryOrderDto);
        });
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderDtos).build();
    }

    public ResponseSuccessfullyDto getAllDeliveryOrder(){
        List<DeliveryOrder> deliveryOrderList = deliveryOrderCrud.findAll();
        ArrayList<DeliveryOrderDto> deliveryOrderDtos = new ArrayList<>();

        deliveryOrderList.forEach(deliveryOrder1 -> {
            DeliveryOrderDto deliveryOrderDto = DeliveryOrderDto.builder().id(deliveryOrder1.getId()).idBusiness((deliveryOrder1.getBusiness().getId())).recipient(deliveryOrder1.getRecipient()).address(deliveryOrder1.getAddress()).date(deliveryOrder1.getDate()).time(deliveryOrder1.getTime()).idDeliveryOrderStatus(deliveryOrder1.getDeliveryOrderStatus().getId()).description(deliveryOrder1.getDescription()).build();
            deliveryOrderDtos.add(deliveryOrderDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderDtos).build();
    }

    public ResponseSuccessfullyDto updateDeliveryOrder(DeliveryOrderDto deliveryOrderDto, Boolean isRestricted){
        if (isRestricted){
            validateStatusOrderPut(deliveryOrderDto.getId());
        }
        Optional<DeliveryOrder> optionalDeliveryOrder = deliveryOrderCrud.findById(deliveryOrderDto.getId());

        if(optionalDeliveryOrder.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Orden de Entrega no ha sido encontrada");
        }
        DeliveryOrder deliveryOrder = optionalDeliveryOrder.get();
        deliveryOrder.setBusiness(userService.getById(deliveryOrderDto.getIdBusiness()));
        deliveryOrder.setRecipient(deliveryOrderDto.getRecipient());
        deliveryOrder.setAddress(deliveryOrderDto.getAddress());
        deliveryOrder.setDate(deliveryOrderDto.getDate());
        deliveryOrder.setTime(deliveryOrderDto.getTime());
        deliveryOrder.setDeliveryOrderStatus(deliveryOrderStatusService.getDeliveryOrderStatusByIdDeliveryOrderStatus(deliveryOrderDto.getIdDeliveryOrderStatus()));
        deliveryOrder.setDescription(deliveryOrderDto.getDescription());

        try{
            deliveryOrderCrud.save(deliveryOrder);
            log.info("Orden de Entrega actualizada...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Orden de Entrega actualizada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar una Orden de Entrega.");
        }
    }

    public ResponseSuccessfullyDto deleteCard(Integer deliveryOrderId){

        Optional<DeliveryOrder> optionalDeliveryOrder = deliveryOrderCrud.findById(deliveryOrderId);

        if(optionalDeliveryOrder.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Orden de Entrega no ha sido encontrada");
        }

        DeliveryOrder deliveryOrder = optionalDeliveryOrder.get();

        try{
            deliveryOrderCrud.delete(deliveryOrder);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Orden de Entrega eliminada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al eliminar la Orden de Entrega");
        }
    }

    public DeliveryOrder getDeliveryOrderByIdDeliveryOrder(Integer id){

        Optional<DeliveryOrder> optionalDeliveryOrder = deliveryOrderCrud.findById(id);

        if(optionalDeliveryOrder.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Delivery Order not exists");
        }

        return optionalDeliveryOrder.get();
    }

    public void validateStatusOrderPut(Integer deliveryOrderId){

        DeliveryOrder deliveryOrder = getDeliveryOrderByIdDeliveryOrder(deliveryOrderId);

        if(!deliveryOrder.getDeliveryOrderStatus().getId().equals(ORDER_CREATED_STATUS) && !deliveryOrder.getDeliveryOrderStatus().getId().equals(ORDER_ASSIGNED_STATUS)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"No es posible actualizar la orden, ya está en movimiento");
        }
    }
*/
    private void verifyIsEmpty(Optional optional, String objeto) {
        if(optional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El registro de "+objeto+" no ha sido encontrado.");
        }
    }

    private void verifyIsEmpty(Object optional, String objeto) {
        if(optional == null){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El registro de "+objeto+" no ha sido encontrado.");
        }
    }
}
