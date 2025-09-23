package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.response.ContractTypeDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.ContractTypeCrud;
import ayd.proyecto1.fastdelivery.repository.entities.ContractType;
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
public class ContractTypeService {

    private final ContractTypeCrud contractTypeCrud;

    public ResponseSuccessfullyDto getContactTypeById(Integer id){
        Optional<ContractType> contractType = contractTypeCrud.findById(id);

        if(contractType.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Tipo de Contrato no ha sido encontrado");
        }
        ContractType contractTypeTemp = contractType.get();
        ContractTypeDto contractTypeDto = ContractTypeDto.builder().id(contractTypeTemp.getId()).contractType(contractTypeTemp.getContractType()).entryTime(contractTypeTemp.getEntryTime()).exitTime(contractTypeTemp.getExitTime()).baseSalary(contractTypeTemp.getBaseSalary()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(contractTypeDto).build();
    }

    public ResponseSuccessfullyDto getAllContractTypes(){
        List<ContractType> contractTypes = contractTypeCrud.findAll();
        ArrayList<ContractTypeDto> contractTypeDtos = new ArrayList<>();

        contractTypes.forEach(contractTypeTemp -> {
            ContractTypeDto contractTypeDto = ContractTypeDto.builder().id(contractTypeTemp.getId()).contractType(contractTypeTemp.getContractType()).entryTime(contractTypeTemp.getEntryTime()).exitTime(contractTypeTemp.getExitTime()).baseSalary(contractTypeTemp.getBaseSalary()).build();
            contractTypeDtos.add(contractTypeDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(contractTypeDtos).build();
    }


}
