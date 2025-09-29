package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderCrud;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderAssignment;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryPerson;
import ayd.proyecto1.fastdelivery.repository.entities.User;
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
public class DeliveryOrderService {

    private final DeliveryOrderCrud deliveryOrderCrud;

    private final UserService userService;

    private final DeliveryOrderStatusService deliveryOrderStatusService;

    private final DeliveryOrderAssignmentService deliveryOrderAssignmentService;

    private final BusinessCardService businessCardService;

    private static final Integer ORDER_CREATED_STATUS = 1;

    private static final Integer ORDER_ASSIGNED_STATUS = 2;

    private static final Integer ORDER_CANCELED_STATUS = 7;

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
                businessCardService.validateNewCard(deliveryOrderSaved.getBusiness().getId());
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

}
