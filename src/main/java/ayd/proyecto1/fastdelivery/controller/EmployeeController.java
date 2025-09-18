package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.EmployeeApi;
import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
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
}
