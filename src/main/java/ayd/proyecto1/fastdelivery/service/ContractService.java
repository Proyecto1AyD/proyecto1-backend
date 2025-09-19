package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewContractDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.ContractCrud;
import ayd.proyecto1.fastdelivery.repository.crud.ContractStatusCrud;
import ayd.proyecto1.fastdelivery.repository.crud.ContractTypeCrud;
import ayd.proyecto1.fastdelivery.repository.entities.Contract;
import ayd.proyecto1.fastdelivery.repository.entities.ContractStatus;
import ayd.proyecto1.fastdelivery.repository.entities.ContractType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ContractService {


    private final ContractTypeCrud contractTypeCrud;

    private final ContractStatusCrud contractStatusCrud;

    private final ContractCrud contractCrud;


    public ResponseSuccessfullyDto createContract(NewContractDto newContractDto){

        Optional<ContractType> optionalContractType = contractTypeCrud.findById(newContractDto.getIdContractType());
        Optional<ContractStatus> optionalContractStatus = contractStatusCrud.findById(newContractDto.getIdStatus());

        if(optionalContractType.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El tipo de contrato es incorrecto");
        }

        if(optionalContractStatus.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El estado del contrato es incorrecto");
        }

        ContractType contractType = optionalContractType.get();
        ContractStatus contractStatus = optionalContractStatus.get();

        Contract contract = new Contract();
        contract.setContractType(contractType);
        contract.setStatus(contractStatus);
        contract.setCommission(newContractDto.getCommission());
        contract.setStartDate(newContractDto.getStartDate());
        contract.setEndDate(newContractDto.getFinalDate());
        contract.setMonths(newContractDto.getMonths());

        try{
            contractCrud.save(contract);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("El contrato fue registrado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al intentar guardar un contrato");
        }
    }


    public Contract getContractById(Integer id){

        Optional<Contract> optionalContract = contractCrud.findById(id);

        if(optionalContract.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Error ");
        }

        return optionalContract.get();
    }


}
