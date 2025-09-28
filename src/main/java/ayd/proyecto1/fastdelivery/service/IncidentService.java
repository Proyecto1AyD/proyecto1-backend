package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewIncidentDto;
import ayd.proyecto1.fastdelivery.dto.response.IncidentDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderCrud;
import ayd.proyecto1.fastdelivery.repository.crud.IncidentCrud;
import ayd.proyecto1.fastdelivery.repository.crud.IncidentTypeCrud;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrder;
import ayd.proyecto1.fastdelivery.repository.entities.Incident;
import ayd.proyecto1.fastdelivery.repository.entities.IncidentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class IncidentService {

    private final IncidentCrud incidentCrud;

    private final DeliveryOrderCrud deliveryOrderCrud;

    private final IncidentTypeCrud incidentTypeCrud;

    public ResponseSuccessfullyDto createIncident(NewIncidentDto newIncidentDto){
        Incident incident = new Incident();
        Optional<DeliveryOrder> deliveryOrder = deliveryOrderCrud.findById(newIncidentDto.getDeliveryOrderId());
        if (deliveryOrder.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Orden de entrega no encontrada.");
        }
        Optional<IncidentType> incidentType = incidentTypeCrud.findById(newIncidentDto.getIncidentTypeId());
        if (incidentType.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Tipo de Incidente no encontrado.");
        }
        LocalDate actualDate = LocalDate.now();
        LocalTime actualTime = LocalTime.now();
        incident.setDeliveryOrder(deliveryOrder.get());
        incident.setIncidentType(incidentType.get());
        incident.setObservations(newIncidentDto.getObservations());
        incident.setDate(actualDate);
        incident.setTime(actualTime);

        try{
            incidentCrud.save(incident);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("Incidente creado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar el Incidente");
        }
    }

    public ResponseSuccessfullyDto getIncidentById(Integer id){
        Optional<Incident> incident = incidentCrud.findById(id);

        if(incident.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El incidente no ha sido encontrado");
        }
        Incident incident1 = incident.get();
        IncidentDto incidentDto = IncidentDto.builder().id(incident1.getId()).deliveryOrderId(incident1.getDeliveryOrder().getId()).incidentTypeId(incident1.getIncidentType().getId()).observations(incident1.getObservations()).date(incident1.getDate()).time(incident1.getTime()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(incidentDto).build();
    }

    public ResponseSuccessfullyDto getIncidentByIdDeliveryOrder(Integer idDeliveryOrder){
        List<Incident> incidents = incidentCrud.getIncidentByIdDeliveryOrder(idDeliveryOrder);
        ArrayList<IncidentDto> incidentDtos = new ArrayList<>();

        incidents.forEach(incident1 -> {
            IncidentDto incidentDto = IncidentDto.builder().id(incident1.getId()).deliveryOrderId(incident1.getDeliveryOrder().getId()).incidentTypeId(incident1.getIncidentType().getId()).observations(incident1.getObservations()).date(incident1.getDate()).time(incident1.getTime()).build();
            incidentDtos.add(incidentDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(incidentDtos).build();
    }

    public ResponseSuccessfullyDto getAllIncidents(){
        List<Incident> incidents = incidentCrud.findAll();
        ArrayList<IncidentDto> incidentDtos = new ArrayList<>();

        incidents.forEach(incident1 -> {
            IncidentDto incidentDto = IncidentDto.builder().id(incident1.getId()).deliveryOrderId(incident1.getDeliveryOrder().getId()).incidentTypeId(incident1.getIncidentType().getId()).observations(incident1.getObservations()).date(incident1.getDate()).time(incident1.getTime()).build();
            incidentDtos.add(incidentDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(incidentDtos).build();
    }

    public ResponseSuccessfullyDto updateIncident(IncidentDto incidentDto){

        Optional<Incident> incident = incidentCrud.findById(incidentDto.getId());

        if(incident.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Incidente no ha sido encontrado");
        }
        Incident incident1 = incident.get();
        incident1.setDeliveryOrder(incident1.getDeliveryOrder());
        incident1.setIncidentType(incident1.getIncidentType());
        incident1.setObservations(incident1.getObservations());
        incident1.setDate(incident1.getDate());
        incident1.setTime(incident1.getTime());

        try{
            incidentCrud.save(incident1);
            log.info("Incidente actualizado...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Incidente actualizado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar un Incidente.");
        }
    }

    public ResponseSuccessfullyDto deleteIncident(Integer cardId){

        Optional<Incident> optionalIncident = incidentCrud.findById(cardId);

        if(optionalIncident.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"El Incidente no ha sido encontrado");
        }

        Incident incident = optionalIncident.get();

        try{
            incidentCrud.delete(incident);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Incidente eliminado exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al eliminar el Incidente");
        }
    }

}
