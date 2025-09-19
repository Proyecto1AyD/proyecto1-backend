package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.BranchCrud;
import ayd.proyecto1.fastdelivery.repository.crud.CoordinatorCrud;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryPersonCrud;
import ayd.proyecto1.fastdelivery.repository.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final UserService userService;

    private final BranchCrud branchCrud;

    private final CoordinatorCrud coordinatorCrud;

    private DeliveryPersonCrud deliveryPersonCrud;

    private final ContractService contractService;

    public ResponseSuccessfullyDto createCoordinator(NewCoordinatorEmployeeDto newCoordinatorEmployeeDto){

        User user = userService.getById(newCoordinatorEmployeeDto.getUserId());
        Branch branch = branchCrud.findById(newCoordinatorEmployeeDto.getBranchId()).get();

        Coordinator coordinator = new Coordinator();
        coordinator.setBranch(branch);
        coordinator.setUser(user);

        try{
            coordinatorCrud.save(coordinator);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.CREATED,"Error al intentar registrar al coordinador.");
        }


        return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("El usuario a sido registrado con éxito").build();
    }

    public ResponseSuccessfullyDto createDeliveryPerson(NewDeliveryPersonDto newDeliveryPersonDto){

        User user = userService.getById(newDeliveryPersonDto.getUserId());
        Branch branch = branchCrud.findById(newDeliveryPersonDto.getBranchId()).get();
        Contract contract = contractService.getContractById(newDeliveryPersonDto.getContractId());

        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setUser(user);
        deliveryPerson.setBranch(branch);
        deliveryPerson.setContract(contract);
        deliveryPerson.setWallet(0.00);
        deliveryPerson.setAvailable(Boolean.TRUE);

        try{
            deliveryPersonCrud.save(deliveryPerson);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al registrar el repartidor");
        }


        return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("El usuario a sido registrado con éxito").build();
    }




}
