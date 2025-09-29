package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderAssignmentDto;
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
public class DeliveryOrderAssignmentService {

    private final DeliveryOrderCrud deliveryOrderCrud;

    private final DeliveryPersonCrud deliveryPersonCrud;

    private final ContractCrud contractCrud;

    private final ContractStatusCrud contractStatusCrud;

    private final DeliveryOrderStatusCrud deliveryOrderStatusCrud;

    private final DeliveryOrderAssignmentCrud deliveryOrderAssignmentCrud;

    private static final Integer ORDER_CREATED_STATUS = 1;

    private static final Integer ORDER_ASSIGNED_STATUS = 2;

    private static final Integer CONTRACT_STATUS_ACTIVE = 1;

    private static final Integer CONTRACT_STATUS_DEFEATED = 2;

    public ResponseSuccessfullyDto createDeliveryOrderAssignment(NewDeliveryOrderAssignmentDto newDeliveryOrderAssignmentDto, Boolean isRestricted){
        DeliveryOrderAssignment deliveryOrderAssignment = new DeliveryOrderAssignment();
        Optional<DeliveryOrder> deliveryOrder = deliveryOrderCrud.findById(newDeliveryOrderAssignmentDto.getIdDeliveryOrder());
        Optional<DeliveryPerson> deliveryPerson = deliveryPersonCrud.findById(newDeliveryOrderAssignmentDto.getIdDeliveryPerson());
        //Verificamos Valores Nulos
        verifyIsEmpty(deliveryOrder, "Orden de Entrega");
        verifyIsEmpty(deliveryPerson, "Repartidor");

        DeliveryPerson deliveryPerson1 = deliveryPerson.get();
        DeliveryOrder deliveryOrder1 = deliveryOrder.get();

        //Validaciones del Repartidor
        verifyDeliveryPerson(deliveryPerson1);
        //Validaciones de la Orden de Entrega
        verifyDeliveryOrder(deliveryOrder1, deliveryPerson1, isRestricted);

        try{
            //Actualizar estado del Repartidor
            deliveryPerson1.setAvailable(false);
            deliveryPersonCrud.save(deliveryPerson1);
            //Actualizar estado de la Orden Entrega
            deliveryOrder1.setDeliveryOrderStatus(deliveryOrderStatusCrud.findById(ORDER_ASSIGNED_STATUS).get());
            deliveryOrderCrud.save(deliveryOrder1);
            //Set Datos de Asignación
            deliveryOrderAssignment.setDeliveryOrder(deliveryOrder1);
            deliveryOrderAssignment.setDeliveryPerson(deliveryPerson1);
            deliveryOrderAssignment.setActive(true);
            deliveryOrderAssignmentCrud.save(deliveryOrderAssignment);
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

    private boolean isTodayWithinRange(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDate hoy = LocalDate.now();

        return (hoy.isEqual(fechaInicio) || hoy.isAfter(fechaInicio)) &&
                (hoy.isEqual(fechaFin) || hoy.isBefore(fechaFin));
    }

    private void verifyIsEmpty(Optional optional, String objeto) {
        if(optional.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El registro de "+objeto+" no ha sido encontrado.");
        }
    }

    private void verifyDeliveryPerson(DeliveryPerson deliveryPerson) {
        //Verificar Contrato
        Optional<Contract> optionalContract = contractCrud.findById(deliveryPerson.getContract().getId());
        verifyIsEmpty(optionalContract, "Contrato");
        Contract contract = optionalContract.get();
        //Verificar Disponibilidad del Repartidor
        if(!deliveryPerson.getAvailable()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no está disponible.");
        }
        //Verificar End Date.
        if (contract.getEndDate()!= null){
            LocalDate fechaInicio = contract.getStartDate();
            LocalDate fechaFin = contract.getEndDate();
            if (!isTodayWithinRange(fechaInicio,fechaFin)){
                contract.setStatus(contractStatusCrud.findById(CONTRACT_STATUS_DEFEATED).get());
                try{
                    contractCrud.save(contract);
                    log.info("Contrato actualizado a Vencido por verificación...");
                }catch (Exception exception){
                    throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar un Contrato.");
                }
                throw new BusinessException(HttpStatus.NOT_FOUND,"Error al actualizar el estado del contrato.");
            }
        }
        //Verificar estado del contrato
        if(!contract.getStatus().getId().equals(CONTRACT_STATUS_ACTIVE)){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no tiene un Contrato Activo.");
        }
    }

    private void verifyDeliveryOrder(DeliveryOrder deliveryOrder, DeliveryPerson deliveryPerson, Boolean isRestricted) {
        //Verificar Estado de la Orden de Entrega
        Integer idDeliveryOrderStatus = deliveryOrder.getDeliveryOrderStatus().getId();
        if(!idDeliveryOrderStatus.equals(ORDER_CREATED_STATUS) && !idDeliveryOrderStatus.equals(ORDER_ASSIGNED_STATUS)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"No es posible actualizar la asignación a la orden de entrega, ya está en movimiento.");
        }

        //Verificar Asignaciones de la Orden de Entrega.
        List<DeliveryOrderAssignment> deliveryOrderAssignments = deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryOrder(deliveryOrder.getId());
        if(!deliveryOrderAssignments.isEmpty()){
            if (!isRestricted){
                //Verifica que no se repita el repartidor
                if(deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryOrderIdDeliveryPerson(deliveryOrder.getId(), deliveryPerson.getId()) != null){
                    throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor ya ha sido asignado a la orden.");
                }
                //si hay un activo, desactivarlo.
                DeliveryOrderAssignment activeAssignment = deliveryOrderAssignmentCrud.getDeliveryOrderAssignmentByIdDeliveryOrderActive(deliveryOrder.getId(), true);

                if (activeAssignment != null) {
                    DeliveryPerson prevDeliveryPerson = deliveryPersonCrud.findById(activeAssignment.getDeliveryPerson().getId()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,"El repartidor de la asignación activa no existe."));
                    prevDeliveryPerson.setAvailable(true);
                    activeAssignment.setActive(false);
                    try {
                        deliveryOrderAssignmentCrud.save(activeAssignment);
                        DeliveryPerson deliveryPerson1 = deliveryPersonCrud.save(prevDeliveryPerson);
                        autoAssignmentDeliveryPerson(deliveryPerson1);
                        log.info("Repartidor con ID: " + prevDeliveryPerson.getId() + " fue desocupado.");
                        log.info("Asignacion Orden de Entrega con Id: " + activeAssignment.getId() + " fue desactivado.");
                    } catch (Exception exception) {
                        throw new BusinessException(HttpStatus.BAD_REQUEST, "Error al actualizar un Asignacion Orden de Entrega");
                    }
                }
            }else{
                throw new BusinessException(HttpStatus.NOT_FOUND,"La Orden de Entrega ya fue asignada a otro repartidor.");
            }
        }
    }

    public void autoAssignmentDeliveryOrder(DeliveryOrder deliveryOrder) {
        // Repartidores disponibles por prioridad
        List<DeliveryPerson> deliveryPersonList = deliveryOrderAssignmentCrud.getDeliveryPersonByPriority();
        if (!deliveryPersonList.isEmpty()){
            for (DeliveryPerson deliveryPerson : deliveryPersonList) {
                NewDeliveryOrderAssignmentDto newDeliveryOrderAssignmentDto = NewDeliveryOrderAssignmentDto.builder()
                        .idDeliveryOrder(deliveryOrder.getId())
                        .idDeliveryPerson(deliveryPerson.getId())
                        .build();

                try {
                    // Intentamos crear la asignación
                    createDeliveryOrderAssignment(newDeliveryOrderAssignmentDto, true);

                    // Si llegamos aquí, se creó correctamente → salir del bucle
                    log.info("Asignación automática creada con éxito para repartidor {}", deliveryPerson.getId());
                    break;

                } catch (Exception exception) {
                    // Si falla con este repartidor, seguimos con el siguiente
                    log.warn("Error al asignar con repartidor {}: {}", deliveryPerson.getId(), exception.getMessage());
                }
            }
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST, "No hay repartidores disponibles en este momento, asignaremos su orden lo más pronto posible.");
        }
    }

    public void autoAssignmentDeliveryPerson(DeliveryPerson deliveryPerson) {
        // Repartidores disponibles por prioridad
        List<DeliveryOrder> deliveryOrderList = deliveryOrderCrud.getDeliveriesOrdersByIdDeliveryOrderStatus(ORDER_CREATED_STATUS);
        if (!deliveryOrderList.isEmpty()){
            NewDeliveryOrderAssignmentDto newDeliveryOrderAssignmentDto = NewDeliveryOrderAssignmentDto.builder()
                    .idDeliveryOrder(deliveryOrderList.getFirst().getId())
                    .idDeliveryPerson(deliveryPerson.getId())
                    .build();
            try {
                // Intentamos crear la asignación
                createDeliveryOrderAssignment(newDeliveryOrderAssignmentDto, true);
                // Si llegamos aquí, se creó correctamente → salir del bucle
                log.info("Asignación automática creada con éxito para repartidor {}", deliveryPerson.getId());

            } catch (Exception exception) {
                // Si falla con este repartidor, seguimos con el siguiente
                log.warn("Error al asignar con repartidor {}: {}", deliveryPerson.getId(), exception.getMessage());
            }
        }else{
            throw new BusinessException(HttpStatus.NOT_FOUND, "No hay Ordenes de Entrega en Cola, asignaremos una nueva orden lo más pronto posible.");
        }
    }

}
