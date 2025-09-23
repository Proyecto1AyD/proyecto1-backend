package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.response.ContractStatusDto;
import ayd.proyecto1.fastdelivery.dto.response.ContractTypeDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.ContractStatusCrud;
import ayd.proyecto1.fastdelivery.repository.entities.ContractStatus;
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
public class ContractStatusService {

    private final ContractStatusCrud contractStatusCrud;

    public ResponseSuccessfullyDto getContactStatusById(Integer id){
        Optional<ContractStatus> contractType = contractStatusCrud.findById(id);

        if(contractType.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Estado del Contrato no ha sido encontrado");
        }
        ContractStatus contractStatus = contractType.get();
        ContractStatusDto contractStatusDto = ContractStatusDto.builder().id(contractStatus.getId()).status(contractStatus.getStatus()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(contractStatusDto).build();
    }

    public ResponseSuccessfullyDto getAllContactStatus(){
        List<ContractStatus> contractStatuses = contractStatusCrud.findAll();
        ArrayList<ContractStatusDto> contractStatusDtos = new ArrayList<>();

        contractStatuses.forEach(contractStatus -> {
            ContractStatusDto contractStatusDto = ContractStatusDto.builder().id(contractStatus.getId()).status(contractStatus.getStatus()).build();
            contractStatusDtos.add(contractStatusDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(contractStatusDtos).build();
    }


}
