package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateCoordinatorDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.CoordinatorInfoDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryPersonInfoDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.BranchCrud;
import ayd.proyecto1.fastdelivery.repository.crud.CoordinatorCrud;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryPersonCrud;
import ayd.proyecto1.fastdelivery.repository.entities.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final UserService userService;

    private final BranchCrud branchCrud;

    private final BranchService branchService;

    private final CoordinatorCrud coordinatorCrud;

    private final DeliveryPersonCrud deliveryPersonCrud;

    private final ContractService contractService;

    private static final Integer COORDINATOR_ID = 2;

    private static final Integer DELIVERY_PERSON_ID = 3;


    public ResponseSuccessfullyDto createCoordinator(NewCoordinatorEmployeeDto newCoordinatorEmployeeDto){

        User user = userService.getById(newCoordinatorEmployeeDto.getUserId());
        if(coordinatorCrud.getByIdUser(user.getId())!=null){
            throw new BusinessException(HttpStatus.FOUND, "Coordinador ya está registrado");
        }

        if(!user.getRole().getId().equals(COORDINATOR_ID)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"El usuario no corresponde a un Coordinador");
        }

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
        if(deliveryPersonCrud.getByIdUser(user.getId())!=null){
            throw new BusinessException(HttpStatus.FOUND, "Repartidor ya está registrado.");
        }

        if(!user.getRole().getId().equals(DELIVERY_PERSON_ID)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"El usuario no corresponde a un Repartidor.");
        }
        Branch branch = branchCrud.findById(newDeliveryPersonDto.getBranchId()).get();
        Contract contract = contractService.getContractByIdContract(newDeliveryPersonDto.getContractId());
        if(deliveryPersonCrud.getByIdContract(contract.getId())!=null){
            throw new BusinessException(HttpStatus.FOUND, "El contrato ya está asignado a otro repartidor.");
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

    public ResponseSuccessfullyDto updateCoordinatorEmployee(UpdateCoordinatorDto updateCoordinatorDto){

        Optional<Coordinator> optionalCoordinator = coordinatorCrud.findById(updateCoordinatorDto.getId());

        if(optionalCoordinator.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND,"El usuario coordinador no ha sido encontrado");
        }

        User user = userService.getById(updateCoordinatorDto.getUserId());
        Optional<Branch> optionalBranch = branchCrud.findById(updateCoordinatorDto.getBranchId());

        if(optionalBranch.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Error al obtener la sucursal, no ha sido encontrada");
        }

        Branch branch = optionalBranch.get();

        Coordinator coordinator = optionalCoordinator.get();
        coordinator.setUser(user);
        coordinator.setBranch(branch);
        coordinatorCrud.save(coordinator);

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Informacion de Coordinador actualizada").build();
    }


    public ResponseSuccessfullyDto updateDeliveryPerson(UpdateDeliveryPersonDto updateDeliveryPersonDto){

        User user = userService.getById(updateDeliveryPersonDto.getUserId());
        Branch branch = branchService.getById(updateDeliveryPersonDto.getBranchId());
        Contract contract = contractService.getContractByIdContract(updateDeliveryPersonDto.getContractId());

        Optional<DeliveryPerson> optionalDeliveryPerson = deliveryPersonCrud.findById(updateDeliveryPersonDto.getId());

        if(optionalDeliveryPerson.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El repartidor no ha sido encontrado");
        }

        DeliveryPerson deliveryPerson = optionalDeliveryPerson.get();
        deliveryPerson.setUser(user);
        deliveryPerson.setBranch(branch);
        deliveryPerson.setContract(contract);
        deliveryPerson.setWallet(updateDeliveryPersonDto.getWallet());
        deliveryPerson.setAvailable(updateDeliveryPersonDto.getAvailable());

        try{
            deliveryPersonCrud.save(deliveryPerson);
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar información del Repartidor");
        }

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Información del repartidor actualizada correctamente").build();
    }


    public DeliveryPerson getDeliveryPersonById(Integer id){
        Optional<DeliveryPerson> optionalDeliveryPerson = deliveryPersonCrud.findById(id);

        if(optionalDeliveryPerson.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El repartidor no ha sido encontrado");
        }

        return optionalDeliveryPerson.get();
    }


    public ResponseSuccessfullyDto getDeliveryPersonInfoById(Integer id){

        DeliveryPerson deliveryPerson = getDeliveryPersonById(id);
        DeliveryPersonInfoDto deliveryPersonInfoDto = DeliveryPersonInfoDto.builder()
                .id(deliveryPerson.getId()).wallet(deliveryPerson.getWallet())
                .userId(deliveryPerson.getUser().getId()).branchId(deliveryPerson.getBranch().getId())
                .contractId(deliveryPerson.getContract().getId())
                .available(deliveryPerson.getAvailable()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Repartidor encontrado").body(deliveryPersonInfoDto).build();
    }

    public Coordinator getCoordinatorById(Integer id){

        Optional<Coordinator> optionalCoordinator = coordinatorCrud.findById(id);

        if(optionalCoordinator.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El coordinador no ha sido encontrado");
        }

        return optionalCoordinator.get();
    }


    public ResponseSuccessfullyDto getCoordinatorInfoById(Integer id){
        Coordinator coordinator = getCoordinatorById(id);

        CoordinatorInfoDto coordinatorInfoDto = CoordinatorInfoDto.builder()
                .id(coordinator.getId())
                .userId(coordinator.getUser().getId())
                .branchId(coordinator.getBranch().getId())
                .build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Coordinador encontrad").body(coordinatorInfoDto).build();
    }

    public ResponseSuccessfullyDto getAllCoordinatorInfo(){
        List<Coordinator> coordinatorList = coordinatorCrud.findAll();
        List<CoordinatorInfoDto> coordinatorInfoDtoList = new ArrayList<>();

        coordinatorList.forEach(coordinator -> {
            CoordinatorInfoDto coordinatorInfoDto = CoordinatorInfoDto.builder()
                    .id(coordinator.getId())
                    .userId(coordinator.getUser().getId())
                    .branchId(coordinator.getBranch().getId())
                    .build();

            coordinatorInfoDtoList.add(coordinatorInfoDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(coordinatorInfoDtoList).build();
    }


    public ResponseSuccessfullyDto getAllDeliveryPersonInfo(){

        List<DeliveryPerson> deliveryPersonList = deliveryPersonCrud.findAll();
        List<DeliveryPersonInfoDto> deliveryPersonInfoDtoList = new ArrayList<>();

        deliveryPersonList.forEach(deliveryPerson -> {
            DeliveryPersonInfoDto deliveryPersonInfoDto = DeliveryPersonInfoDto.builder()
                    .id(deliveryPerson.getId()).wallet(deliveryPerson.getWallet())
                    .userId(deliveryPerson.getUser().getId()).branchId(deliveryPerson.getBranch().getId())
                    .contractId(deliveryPerson.getContract().getId())
                    .available(deliveryPerson.getAvailable()).build();
            deliveryPersonInfoDtoList.add(deliveryPersonInfoDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(deliveryPersonList).build();
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
