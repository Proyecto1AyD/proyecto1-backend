package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.EmployeeApi;
import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.*;
import ayd.proyecto1.fastdelivery.service.EmployeeService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class EmployeeController implements EmployeeApi {

    private final UserService userService;

    private final EmployeeService employeeService;


    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createCoordinatorEmployee(NewCoordinatorEmployeeDto newCoordinatorEmployeeDto, Integer token) {
        log.info("POST /employee/coordinator");
        userService.validateAuthorizationHeader(token);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.createCoordinator(newCoordinatorEmployeeDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createDeliveryEmployee(NewDeliveryPersonDto newDeliveryPersonDto, Integer userId) {
        log.info("POST /employee/delivery");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.createDeliveryPerson(newDeliveryPersonDto);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryPersonById(Integer id, Integer userId) {
        log.info("GET /employee/delivery/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getEmployeById(id, true);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryPersonsByIdUser(Integer id, Integer userId) {
        log.info("GET /employee/delivery/idUser/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getDeliveryPersonsByIdUser(id);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getCoordinatorById(Integer id, Integer userId) {
        log.info("GET /employee/coordinator/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getEmployeById(id, false);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryPerson( Integer userId) {
        log.info("GET /employee/delivery/all");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getAllEmployees(true);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllCoordinator( Integer userId) {
        log.info("GET /employee/coordinator/all");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getAllEmployees(false);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }


    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateDeliveryPerson(DeliveryPersonDto deliveryPersonDto, Integer userId) {
        log.info("PUT employee/delivery");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.updateDeliveryPerson(deliveryPersonDto);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateCoordinator(CoordinatorEmployeeDto coordinatorEmployeeDto, Integer userId) {
        log.info("PUT employee/coordinator");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.updateCoordinator(coordinatorEmployeeDto);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteDeliveryPerson(Integer id, Integer userId) {
        log.info("DELETE employee/delivery");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.deleteEmployee(id,true);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteCoordinator(Integer id, Integer userId) {
        log.info("DELETE employee/coordinator");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.deleteEmployee(id, false);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

}
