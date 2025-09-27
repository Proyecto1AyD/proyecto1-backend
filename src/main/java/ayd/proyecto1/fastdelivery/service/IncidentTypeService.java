package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.response.ContractTypeDto;
import ayd.proyecto1.fastdelivery.dto.response.IncidentTypeDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.IncidentTypeCrud;
import ayd.proyecto1.fastdelivery.repository.entities.ContractType;
import ayd.proyecto1.fastdelivery.repository.entities.IncidentType;
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
public class IncidentTypeService {

    private final IncidentTypeCrud incidentTypeCrud;

    public ResponseSuccessfullyDto getIncidentTypeById(Integer id){
        Optional<IncidentType> incidentType = incidentTypeCrud.findById(id);

        if(incidentType.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Tipo de Incidente no ha sido encontrado");
        }
        IncidentType incidentType1 = incidentType.get();
        IncidentTypeDto incidentTypeDto = IncidentTypeDto.builder().id(incidentType1.getId()).description(incidentType1.getDescription()).isCostumer(incidentType1.getIsCustomer()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(incidentTypeDto).build();
    }

    public ResponseSuccessfullyDto getAllIncidentTypes(){
        List<IncidentType> incidentTypes = incidentTypeCrud.findAll();
        ArrayList<IncidentTypeDto> incidentTypeDtos = new ArrayList<>();

        incidentTypes.forEach(incidentType1 -> {
            IncidentTypeDto incidentTypeDto = IncidentTypeDto.builder().id(incidentType1.getId()).description(incidentType1.getDescription()).isCostumer(incidentType1.getIsCustomer()).build();
            incidentTypeDtos.add(incidentTypeDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(incidentTypeDtos).build();
    }

    public ResponseSuccessfullyDto getIncidentTypeByIsCustomer(Boolean isCustomer){
        List<IncidentType> incidentType = incidentTypeCrud.getIncidentTypeByIsCustomer(isCustomer);
        ArrayList<IncidentTypeDto> incidentTypeDtos = new ArrayList<>();

        incidentType.forEach(incidentType1 -> {
            IncidentTypeDto incidentTypeDto = IncidentTypeDto.builder().id(incidentType1.getId()).description(incidentType1.getDescription()).isCostumer(incidentType1.getIsCustomer()).build();
            incidentTypeDtos.add(incidentTypeDto);
        });
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(incidentTypeDtos).build();
    }
}
