package ayd.proyecto1.fastdelivery.controller.api;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderAssignmentDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/deliveryOrderAssignment")
public interface DeliveryOrderAssignmentApi {
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createDeliveryOrderAssignment(@RequestBody NewDeliveryOrderAssignmentDto newDeliveryOrderAssignmentDto);

    @PostMapping("/restricted")
    ResponseEntity<ResponseSuccessfullyDto> createDeliveryOrderAssignmentRestricted(@RequestBody NewDeliveryOrderAssignmentDto newDeliveryOrderAssignmentDto);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderAssignmentById(@PathVariable Integer id);

    @GetMapping("/deliveryOrder/{idDeliveryOrder}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderAssignmentByIdDeliveryOrder(@PathVariable Integer idDeliveryOrder);

    @GetMapping("/deliveryPerson/{idDeliveryPerson}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderAssignmentByIdDeliveryPerson(@PathVariable Integer idDeliveryPerson);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryOrderAssignments();

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateDeliveryOrderAssignment(@RequestBody DeliveryOrderAssignmentDto deliveryOrderAssignmentDto);
}
