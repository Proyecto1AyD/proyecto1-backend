package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.response.ContractLogDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderLogDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.ContractLogCrud;
import ayd.proyecto1.fastdelivery.repository.entities.ContractLog;
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
public class ContractLogService {

    private final ContractLogCrud contractLogCrud;

    public ResponseSuccessfullyDto getContractLogById(Integer id){
        Optional<ContractLog> contractLog = contractLogCrud.findById(id);

        if(contractLog.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La Bitácora del Contrato no ha sido encontrada");
        }
        ContractLog contractLog1 = contractLog.get();
        ContractLogDto logDto = ContractLogDto.builder().id(contractLog1.getId()).idContract(contractLog1.getContract().getId()).date(contractLog1.getDate()).time(contractLog1.getTime()).action(contractLog1.getAction()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(logDto).build();
    }

    public ResponseSuccessfullyDto getAllContractLogs(){
        List<ContractLog> contractLogs = contractLogCrud.findAll();
        ArrayList<ContractLogDto> contractLogDtos = new ArrayList<>();

        contractLogs.forEach(contractLog1 -> {
            ContractLogDto logDto = ContractLogDto.builder().id(contractLog1.getId()).idContract(contractLog1.getContract().getId()).date(contractLog1.getDate()).time(contractLog1.getTime()).action(contractLog1.getAction()).build();
            contractLogDtos.add(logDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(contractLogDtos).build();
    }

}
