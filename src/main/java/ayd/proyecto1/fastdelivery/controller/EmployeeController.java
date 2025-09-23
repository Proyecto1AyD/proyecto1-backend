package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.EmployeeApi;
import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateCoordinatorDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
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
    public ResponseEntity<ResponseSuccessfullyDto> updateDeliveryPerson(UpdateDeliveryPersonDto updateDeliveryPersonDto, Integer token) {
        log.info("PUT user/delivery");
        userService.validateAuthorizationHeader(token);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.updateDeliveryPerson(updateDeliveryPersonDto);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateCoordinator(UpdateCoordinatorDto updateCoordinatorDto, Integer token) {
        log.info("PUT user/coordinator");
        userService.validateAuthorizationHeader(token);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.updateCoordinatorEmployee(updateCoordinatorDto);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getCoordinator(Integer coordinatorId, Integer token) {
        log.info("GET user/coordinator/{id}");
        userService.validateAuthorizationHeader(token);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getCoordinatorInfoById(coordinatorId);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryPerson(Integer deliveryPersonId, Integer token) {
        log.info("GET user/delivery/{id]");
        userService.validateAuthorizationHeader(token);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getDeliveryPersonInfoById(deliveryPersonId);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryPerson(Integer token) {
        log.info("GET user/delivery/all");
        userService.validateAuthorizationHeader(token);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getAllDeliveryPersonInfo();
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllCoordinator(Integer token) {
        log.info("GET user/coordinator/all");
        userService.validateAuthorizationHeader(token);
        ResponseSuccessfullyDto responseSuccessfullyDto = employeeService.getAllCoordinatorInfo();
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
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
