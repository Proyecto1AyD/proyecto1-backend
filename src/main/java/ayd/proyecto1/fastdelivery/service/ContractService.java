package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewContractDto;
import ayd.proyecto1.fastdelivery.dto.response.ContractDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.dto.response.UserDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.ContractCrud;
import ayd.proyecto1.fastdelivery.repository.crud.ContractStatusCrud;
import ayd.proyecto1.fastdelivery.repository.crud.ContractTypeCrud;
import ayd.proyecto1.fastdelivery.repository.entities.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ContractService {


    private final ContractTypeCrud contractTypeCrud;

    private final ContractStatusCrud contractStatusCrud;

    private final ContractCrud contractCrud;

    private static final Integer ACTIVE_STATUS_ID = 1;

    public ResponseSuccessfullyDto createContract(NewContractDto newContractDto){

        Optional<ContractType> optionalContractType = contractTypeCrud.findById(newContractDto.getIdContractType());
        if(optionalContractType.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El tipo de contrato es incorrecto");
        }

        Optional<ContractStatus> optionalContractStatus = contractStatusCrud.findById(ACTIVE_STATUS_ID);

        ContractType contractType = optionalContractType.get();
        ContractStatus contractStatus = optionalContractStatus.get();
        LocalDate actualDate = LocalDate.now();

        Contract contract = new Contract();
        contract.setContractType(contractType);
        contract.setStartDate(actualDate);
        contract.setStatus(contractStatus);
        contract.setCommission(newContractDto.getCommission());
        if(newContractDto.getMonths() !=0){
            contract.setEndDate(actualDate.plusMonths(newContractDto.getMonths()));
            contract.setMonths(newContractDto.getMonths());
        }

        try{
            Contract savedContract = contractCrud.save(contract);
            log.info("Contract was saved successfully.");
            Map<String, Integer> responseBody = new HashMap<>();
            responseBody.put("contractId", savedContract.getId());
            //AGREGAR A BITACORA
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("El contrato fue registrado exitosamente").body(responseBody).build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al intentar guardar un contrato");
        }
    }

    public Contract getContractByIdContract(Integer id){

        Optional<Contract> optionalContract = contractCrud.findById(id);

        if(optionalContract.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "No existe contrato con dicho ID");
        }

        return optionalContract.get();
    }

    public ResponseSuccessfullyDto getContractById(Integer id){
        Contract contract = getContractByIdContract(id);
        ContractDto contractDto = ContractDto.builder().id(contract.getId()).commission(contract.getCommission()).startDate(contract.getStartDate()).endDate(contract.getEndDate()).idContractType(contract.getContractType().getId()).idStatus(contract.getStatus().getId()).months(contract.getMonths()).build();
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(contractDto).build();
    }

    public ResponseSuccessfullyDto updateContract(ContractDto contractDto){

        Optional<Contract> optionalContract = contractCrud.findById(contractDto.getId());

        if(optionalContract.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND,"El contrato no ha sido encontrado");
        }

        Contract contract = optionalContract.get();
        contract.setCommission(contractDto.getCommission());
        contract.setStartDate(contractDto.getStartDate());
        contract.setEndDate(contractDto.getEndDate());
        contract.setContractType(contractTypeCrud.findById(contractDto.getIdContractType()).get());
        contract.setStatus(contractStatusCrud.findById(contractDto.getIdStatus()).get());
        contract.setMonths(contractDto.getMonths());

        try{
            contractCrud.save(contract);
            //BITACORA
            log.info("Contrato actualizado...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Contrato actualizado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar un Contrato");
        }
    }

    public ResponseSuccessfullyDto deleteContract(Integer id){
        Optional<Contract> contract = contractCrud.findById(id);
        if (contract.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Contrato no encontrado.");
        }
        contractCrud.deleteById(id);
        log.info("Contrato was deleted successfully.");
        return ResponseSuccessfullyDto
                .builder()
                .code(HttpStatus.OK)
                .message("El Contrato fué eliminado correctamente")
                .build();
    }

}
