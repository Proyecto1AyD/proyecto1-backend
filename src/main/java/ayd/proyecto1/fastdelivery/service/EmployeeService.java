package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.CoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.BranchCrud;
import ayd.proyecto1.fastdelivery.repository.crud.CoordinatorCrud;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryPersonCrud;
import ayd.proyecto1.fastdelivery.repository.entities.*;
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
public class EmployeeService {

    private final UserService userService;

    private final BranchCrud branchCrud;

    private final CoordinatorCrud coordinatorCrud;

    private final DeliveryPersonCrud deliveryPersonCrud;

    private final ContractService contractService;

    private static final Integer COORDINATOR_ID = 2;

    private static final Integer DELIVERY_PERSON_ID = 3;

    public ResponseSuccessfullyDto createCoordinator(NewCoordinatorEmployeeDto newCoordinatorEmployeeDto){

        User user = userService.getById(newCoordinatorEmployeeDto.getUserId());
        Branch branch = branchCrud.findById(newCoordinatorEmployeeDto.getBranchId()).get();

        if(!user.getRole().getId().equals(COORDINATOR_ID)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"El usuario no corresponde a un Coordinador");
        }

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

        if(!user.getRole().getId().equals(DELIVERY_PERSON_ID)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"El usuario no corresponde a un Repartidor");
        }

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

    public ResponseSuccessfullyDto getEmployeById(Integer id, Boolean isDeliveryPerson){
        if (isDeliveryPerson){
            Optional<DeliveryPerson> optionalDeliveryPerson = deliveryPersonCrud.findById(id);

            if(optionalDeliveryPerson.isEmpty()){
                throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no ha sido encontrado.");
            }

            DeliveryPerson deliveryPerson = optionalDeliveryPerson.get();
            DeliveryPersonDto deliveryPersonDto = DeliveryPersonDto.builder().id(deliveryPerson.getId()).userId(deliveryPerson.getUser().getId()).wallet(deliveryPerson.getWallet()).contractId(deliveryPerson.getContract().getId()).branchId(deliveryPerson.getBranch().getId()).available(deliveryPerson.getAvailable()).build();
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryPersonDto).build();
        }else{
            Optional<Coordinator> optionalCoordinator = coordinatorCrud.findById(id);

            if(optionalCoordinator.isEmpty()){
                throw new BusinessException(HttpStatus.NOT_FOUND,"El Coordinador no ha sido encontrado.");
            }

            Coordinator coordinator = optionalCoordinator.get();
            CoordinatorEmployeeDto coordinatorEmployeeDto = CoordinatorEmployeeDto.builder().id(coordinator.getId()).userId(coordinator.getUser().getId()).branchId(coordinator.getBranch().getId()).build();
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(coordinatorEmployeeDto).build();
        }
    }

    public ResponseSuccessfullyDto getAllEmployees(Boolean isDeliveryPerson){
        if (isDeliveryPerson){
            List<DeliveryPerson> deliveryPersonList = deliveryPersonCrud.findAll();
            ArrayList<DeliveryPersonDto> deliveryPersonDtos = new ArrayList<>();

            deliveryPersonList.forEach(deliveryPerson -> {
                DeliveryPersonDto deliveryPersonDto = DeliveryPersonDto.builder().id(deliveryPerson.getId()).userId(deliveryPerson.getUser().getId()).wallet(deliveryPerson.getWallet()).contractId(deliveryPerson.getContract().getId()).branchId(deliveryPerson.getBranch().getId()).available(deliveryPerson.getAvailable()).build();
                deliveryPersonDtos.add(deliveryPersonDto);
            });

            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryPersonDtos).build();
        }else{
            List<Coordinator> coordinators = coordinatorCrud.findAll();
            ArrayList<CoordinatorEmployeeDto> coordinatorEmployeeDtos = new ArrayList<>();

            coordinators.forEach(coordinator -> {
                CoordinatorEmployeeDto coordinatorEmployeeDto = CoordinatorEmployeeDto.builder().id(coordinator.getId()).userId(coordinator.getUser().getId()).branchId(coordinator.getBranch().getId()).build();
                coordinatorEmployeeDtos.add(coordinatorEmployeeDto);
            });

            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(coordinatorEmployeeDtos).build();
        }
    }

    public ResponseSuccessfullyDto getDeliveryPersonsByIdUser(Integer idUser){
        DeliveryPerson deliveryPerson = deliveryPersonCrud.getByIdUser(idUser);
        DeliveryPersonDto deliveryPersonDto = DeliveryPersonDto.builder().id(deliveryPerson.getId()).userId(deliveryPerson.getUser().getId()).wallet(deliveryPerson.getWallet()).contractId(deliveryPerson.getContract().getId()).branchId(deliveryPerson.getBranch().getId()).available(deliveryPerson.getAvailable()).build();
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryPersonDto).build();
    }

    public ResponseSuccessfullyDto updateDeliveryPerson(DeliveryPersonDto deliveryPersonDto){

        Optional<DeliveryPerson> optionalDeliveryPerson = deliveryPersonCrud.findById(deliveryPersonDto.getId());

        if(optionalDeliveryPerson.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no ha sido encontrado");
        }

        DeliveryPerson deliveryPerson = optionalDeliveryPerson.get();
        deliveryPerson.setUser(userService.getById(deliveryPersonDto.getUserId()));
        deliveryPerson.setBranch(branchCrud.findById(deliveryPersonDto.getBranchId()).get());
        deliveryPerson.setContract(contractService.getContractById(deliveryPersonDto.getContractId()));
        deliveryPerson.setWallet(deliveryPersonDto.getWallet());
        deliveryPerson.setAvailable(deliveryPersonDto.getAvailable());

        try{
            deliveryPersonCrud.save(deliveryPerson);
            log.info("Repartidor actualizado...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Repartidor actualizado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar un Repartidor");
        }
    }

    public ResponseSuccessfullyDto updateCoordinator(CoordinatorEmployeeDto coordinatorEmployeeDto){

        Optional<Coordinator> optionalCoordinator = coordinatorCrud.findById(coordinatorEmployeeDto.getId());

        if(optionalCoordinator.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no ha sido encontrado");
        }

        Coordinator coordinator = optionalCoordinator.get();
        coordinator.setBranch(branchCrud.findById(coordinatorEmployeeDto.getBranchId()).get());
        coordinator.setUser(userService.getById(coordinatorEmployeeDto.getUserId()));

        try{
            coordinatorCrud.save(coordinator);
            log.info("Coordinador actualizado...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Coordinador actualizado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar un Coordinador");
        }
    }

    public ResponseSuccessfullyDto deleteEmployee(Integer id, Boolean isDeliveryPerson){

        if (isDeliveryPerson){
            Optional<DeliveryPerson> optionalDeliveryPerson = deliveryPersonCrud.findById(id);

            if(optionalDeliveryPerson.isEmpty()){
                throw new BusinessException(HttpStatus.NOT_FOUND,"El Repartidor no ha sido encontrado");
            }

            DeliveryPerson deliveryPerson = optionalDeliveryPerson.get();

            try{
                deliveryPersonCrud.delete(deliveryPerson);
                return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Repartidor eliminado exitosamente").build();
            }catch (Exception exception){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al eliminar al Repartidor");
            }
        }else{
            Optional<Coordinator> optionalCoordinator = coordinatorCrud.findById(id);

            if(optionalCoordinator.isEmpty()){
                throw new BusinessException(HttpStatus.NOT_FOUND,"El Coordinador no ha sido encontrado");
            }

            Coordinator coordinator = optionalCoordinator.get();

            try{
                coordinatorCrud.delete(coordinator);
                return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Coordinador eliminado exitosamente").build();
            }catch (Exception exception){
                throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al eliminar al Coordinador");
            }
        }

    }

}
