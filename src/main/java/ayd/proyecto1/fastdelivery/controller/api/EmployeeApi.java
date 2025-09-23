package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.CardDto;
import ayd.proyecto1.fastdelivery.dto.response.CoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee")
public interface EmployeeApi {

    @PostMapping("/coordinator")
    ResponseEntity<ResponseSuccessfullyDto> createCoordinatorEmployee(@RequestBody NewCoordinatorEmployeeDto newCoordinatorEmployeeDto,
                                                                      @RequestHeader(value = "authorization") Integer userId);
    @PostMapping("/delivery")
    ResponseEntity<ResponseSuccessfullyDto> createDeliveryEmployee(@RequestBody NewDeliveryPersonDto newDeliveryPersonDto,
                                                                      @RequestHeader(value = "authorization") Integer userId);
    @GetMapping("/delivery/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryPersonById(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/delivery/idUser/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryPersonsByIdUser(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/coordinator/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getCoordinatorById(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/delivery/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryPerson(@RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/coordinator/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllCoordinator(@RequestHeader(value = "authorization") Integer userId);

    @PutMapping("/delivery")
    ResponseEntity<ResponseSuccessfullyDto> updateDeliveryPerson(@RequestBody DeliveryPersonDto deliveryPersonDto, @RequestHeader(value = "authorization") Integer userId);

    @PutMapping("/coordinator")
    ResponseEntity<ResponseSuccessfullyDto> updateCoordinator(@RequestBody CoordinatorEmployeeDto coordinatorEmployeeDto, @RequestHeader(value = "authorization") Integer userId);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteDeliveryPerson(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @DeleteMapping("/coordinator/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteCoordinator(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);
}
