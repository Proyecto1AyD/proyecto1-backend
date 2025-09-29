package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.ReportsApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.ReportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReportsController implements ReportsApi {

    private final ReportsService reportsService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deliveryOrderByStatus(Integer status) {
        log.info("GET reports/delivery_orders/{status}");
        ResponseSuccessfullyDto responseSuccessfullyDto = reportsService.deliveryOrdersByStatus(status);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByAssignmentIdAndDate(Integer deliveryId, Date initDate, Date finishDate) {
        log.info("GET reports/commission/delivery_person/{delivery_person_id}/{init_date}/{finish_date}");
        ResponseSuccessfullyDto responseSuccessfullyDto = reportsService.getByAssignmentIdAndDate(deliveryId,initDate,finishDate);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentsByBusinessId(Integer businessId) {
        log.info("GET reports/incidents/business/{business_id}");
        ResponseSuccessfullyDto responseSuccessfullyDto = reportsService.getIncidentByBusinessId(businessId);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }


}
