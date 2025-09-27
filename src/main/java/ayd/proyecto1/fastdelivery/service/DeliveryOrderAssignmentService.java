package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderAssignmentCrud;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderCrud;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderStatusCrud;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryPersonCrud;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderAssignment;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryPerson;
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
public class DeliveryOrderAssignmentService {

    private final DeliveryOrderCrud deliveryOrderCrud;

    private final DeliveryPersonCrud deliveryPersonCrud;

    private final DeliveryOrderStatusCrud deliveryOrderStatusCrud;

    private final DeliveryOrderAssignmentCrud deliveryOrderAssignmentCrud;

    public ResponseSuccessfullyDto createDeliveryOrderAssignment(NewDeliveryOrderAssignmentDto newDeliveryOrderAssignmentDto, Boolean isRestricted){
        DeliveryOrderAssignment deliveryOrderAssignment = new DeliveryOrderAssignment();
        Optional<DeliveryOrder> deliveryOrder = deliveryOrderCrud.findById(newDeliveryOrderAssignmentDto.getIdDeliveryOrder());
        if(deliveryOrder.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Orden de Entrega no ha sido encontrada.");
        }
        Optional<DeliveryPerson> deliveryPerson = deliveryPersonCrud.findById(newDeliveryOrderAssignmentDto.getIdDeliveryPerson());
        if(deliveryPerson.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no ha sido encontrado.");
        }

        if(!deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryOrder(deliveryOrder.get().getId()).isEmpty()){
            if (!isRestricted){
                //no repetir repartidor
                if(deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryOrderIdDeliveryPerson(deliveryOrder.get().getId(), deliveryPerson.get().getId()) != null){
                    throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor ya ha sido asignado a la orden.");
                }
                //si hay un activo, desactivarlo.
                DeliveryOrderAssignment deliveryOrderAssignment1 = deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryOrderActive(deliveryOrder.get().getId(), true);
                DeliveryPerson deliveryPerson1 = deliveryPersonCrud.findById(deliveryOrderAssignment1.getDeliveryPerson().getId()).get();
                deliveryPerson1.setAvailable(true);
                deliveryOrderAssignment1.setActive(false);
                try{
                    deliveryOrderAssignmentCrud.save(deliveryOrderAssignment1);
                    deliveryPersonCrud.save(deliveryPerson1);
                    log.info("Repartidor con ID: "+deliveryPerson1.getId()+" fue desocupado.");
                    log.info("Asignacion Orden de Entrega con Id: "+deliveryOrderAssignment1.getId()+" fue desactivado.");
                }catch (Exception exception){
                    throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar un Asignacion Orden de Entrega");
                }
            }else{
                throw new BusinessException(HttpStatus.NOT_FOUND,"La Orden de Entrega ya fue asignada a otro repartidor.");
            }

        }

        if(!deliveryPerson.get().getAvailable()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no está disponible.");
        }

        deliveryOrderAssignment.setDeliveryOrder(deliveryOrder.get());
        deliveryOrderAssignment.setDeliveryPerson(deliveryPerson.get());
        deliveryOrderAssignment.setActive(true);
        //Actualizar estado del Repartidor
        DeliveryPerson deliveryPerson1 = deliveryPerson.get();
        deliveryPerson1.setAvailable(false);
        //Actualizar estado de la Orden Entrega
        DeliveryOrder deliveryOrder1 = deliveryOrder.get();
        deliveryOrder1.setDeliveryOrderStatus(deliveryOrderStatusCrud.findById(2).get());
        try{
            deliveryOrderAssignmentCrud.save(deliveryOrderAssignment);
            deliveryPersonCrud.save(deliveryPerson1);
            deliveryOrderCrud.save(deliveryOrder1);
            log.info("Orden con ID: "+deliveryOrder1.getId()+", su estado fue cambiado.");
            log.info("Repartidor con ID: "+deliveryPerson1.getId()+" fue ocupado.");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("Asignacion Orden de Entrega creada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar la Asignacion Orden de Entrega");
        }
    }

    public ResponseSuccessfullyDto getDeliveryOrderAssignmentById(Integer id){
        Optional<DeliveryOrderAssignment> optionalDeliveryOrderAssignment = deliveryOrderAssignmentCrud.findById(id);

        if(optionalDeliveryOrderAssignment.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Delivery Order Assignment not exists");
        }

        DeliveryOrderAssignment deliveryOrderAssignment = optionalDeliveryOrderAssignment.get();
        DeliveryOrderAssignmentDto deliveryOrderAssignmentDto = DeliveryOrderAssignmentDto.builder().id(deliveryOrderAssignment.getId()).idDeliveryOrder(deliveryOrderAssignment.getDeliveryOrder().getId()).idDeliveryPerson(deliveryOrderAssignment.getDeliveryPerson().getId()).active(deliveryOrderAssignment.getActive()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderAssignmentDto).build();
    }

    public ResponseSuccessfullyDto getDeliveryOrderAssignmentByIdDeliveryOrder(Integer idDeliveryOrder){
        List<DeliveryOrderAssignment> deliveryOrderAssignments = deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryOrder(idDeliveryOrder);
        ArrayList<DeliveryOrderAssignmentDto> deliveryOrderAssignmentDtos = new ArrayList<>();

        deliveryOrderAssignments.forEach(deliveryOrderAssignment -> {
            DeliveryOrderAssignmentDto deliveryOrderAssignmentDto = DeliveryOrderAssignmentDto.builder().id(deliveryOrderAssignment.getId()).idDeliveryOrder(deliveryOrderAssignment.getDeliveryOrder().getId()).idDeliveryPerson(deliveryOrderAssignment.getDeliveryPerson().getId()).active(deliveryOrderAssignment.getActive()).build();
            deliveryOrderAssignmentDtos.add(deliveryOrderAssignmentDto);
        });
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderAssignmentDtos).build();
    }

    public ResponseSuccessfullyDto getDeliveryOrderAssignmentByIdDeliveryPerson(Integer idDeliveryPerson){
        List<DeliveryOrderAssignment> deliveryOrderAssignments = deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryPerson(idDeliveryPerson);
        ArrayList<DeliveryOrderAssignmentDto> deliveryOrderAssignmentDtos = new ArrayList<>();

        deliveryOrderAssignments.forEach(deliveryOrderAssignment -> {
            DeliveryOrderAssignmentDto deliveryOrderAssignmentDto = DeliveryOrderAssignmentDto.builder().id(deliveryOrderAssignment.getId()).idDeliveryOrder(deliveryOrderAssignment.getDeliveryOrder().getId()).idDeliveryPerson(deliveryOrderAssignment.getDeliveryPerson().getId()).active(deliveryOrderAssignment.getActive()).build();
            deliveryOrderAssignmentDtos.add(deliveryOrderAssignmentDto);
        });
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderAssignmentDtos).build();
    }

    public ResponseSuccessfullyDto getAllDeliveryOrderAssignments(){
        List<DeliveryOrderAssignment> deliveryOrderAssignments = deliveryOrderAssignmentCrud.findAll();
        ArrayList<DeliveryOrderAssignmentDto> deliveryOrderAssignmentDtos = new ArrayList<>();

        deliveryOrderAssignments.forEach(deliveryOrderAssignment -> {
            DeliveryOrderAssignmentDto deliveryOrderAssignmentDto = DeliveryOrderAssignmentDto.builder().id(deliveryOrderAssignment.getId()).idDeliveryOrder(deliveryOrderAssignment.getDeliveryOrder().getId()).idDeliveryPerson(deliveryOrderAssignment.getDeliveryPerson().getId()).active(deliveryOrderAssignment.getActive()).build();
            deliveryOrderAssignmentDtos.add(deliveryOrderAssignmentDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryOrderAssignmentDtos).build();
    }

    public ResponseSuccessfullyDto updateDeliveryOrderAssigment(DeliveryOrderAssignmentDto deliveryOrderAssignmentDto){
        Optional<DeliveryOrderAssignment> optionalDeliveryOrderAssignment = deliveryOrderAssignmentCrud.findById(deliveryOrderAssignmentDto.getId());

        if(optionalDeliveryOrderAssignment.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Asignacion Orden de Entrega no ha sido encontrada");
        }
        Optional<DeliveryOrder> deliveryOrder = deliveryOrderCrud.findById(deliveryOrderAssignmentDto.getIdDeliveryOrder());
        if(deliveryOrder.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Orden de Entrega no ha sido encontrada");
        }
        Optional<DeliveryPerson> deliveryPerson = deliveryPersonCrud.findById(deliveryOrderAssignmentDto.getIdDeliveryPerson());
        if(deliveryPerson.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no ha sido encontrado");
        }
        DeliveryOrderAssignment deliveryOrderAssignment = optionalDeliveryOrderAssignment.get();
        deliveryOrderAssignment.setDeliveryOrder(deliveryOrder.get());
        deliveryOrderAssignment.setDeliveryPerson(deliveryPerson.get());
        deliveryOrderAssignment.setActive(deliveryOrderAssignmentDto.getActive());

        try{
            deliveryOrderAssignmentCrud.save(deliveryOrderAssignment);
            log.info("Asignacion Orden de Entrega actualizada...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Asignacion Orden de Entrega actualizada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar una Asignacion Orden de Entrega.");
        }
    }

}
