package ayd.proyecto1.fastdelivery.controller;
import ayd.proyecto1.fastdelivery.controller.api.DeliveryOrderAssignmentApi;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.DeliveryOrderAssignmentService;
import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RequiredArgsConstructor
@RestController
public class DeliveryOrderAssignmentController implements DeliveryOrderAssignmentApi {
    private final DeliveryOrderAssignmentService deliveryOrderAssignmentService;
    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createDeliveryOrderAssignment(NewDeliveryOrderAssignmentDto newDeliveryOrderAssignmentDto) {
        log.info("POST deliveryOrderAssignment/create");
        ResponseSuccessfullyDto response = deliveryOrderAssignmentService.createDeliveryOrderAssignment(newDeliveryOrderAssignmentDto, false);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    public ResponseEntity<ResponseSuccessfullyDto> createDeliveryOrderAssignmentRestricted(NewDeliveryOrderAssignmentDto newDeliveryOrderAssignmentDto) {
        log.info("POST deliveryOrderAssignment/create/restricted");
        ResponseSuccessfullyDto response = deliveryOrderAssignmentService.createDeliveryOrderAssignment(newDeliveryOrderAssignmentDto, true);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderAssignmentById(Integer id) {
        log.info("GET deliveryOrderAssignment/{}", id);
        ResponseSuccessfullyDto response = deliveryOrderAssignmentService.getDeliveryOrderAssignmentById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderAssignmentByIdDeliveryOrder(Integer idDeliveryOrder) {
        log.info("GET deliveryOrderAssignment/deliveryOrder/{}", idDeliveryOrder);
        ResponseSuccessfullyDto response = deliveryOrderAssignmentService.getDeliveryOrderAssignmentByIdDeliveryOrder(idDeliveryOrder);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderAssignmentByIdDeliveryPerson(Integer idDeliveryPerson) {
        log.info("GET deliveryOrderAssignment/deliveryPerson/{}", idDeliveryPerson);
        ResponseSuccessfullyDto response = deliveryOrderAssignmentService.getDeliveryOrderAssignmentByIdDeliveryPerson(idDeliveryPerson);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryOrderAssignments() {
        log.info("GET deliveryOrderAssignment/all");
        ResponseSuccessfullyDto response = deliveryOrderAssignmentService.getAllDeliveryOrderAssignments();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateDeliveryOrderAssignment(DeliveryOrderAssignmentDto deliveryOrderAssignmentDto) {
        log.info("PUT deliveryOrderAssignment/update");
        ResponseSuccessfullyDto response = deliveryOrderAssignmentService.updateDeliveryOrderAssigment(deliveryOrderAssignmentDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}