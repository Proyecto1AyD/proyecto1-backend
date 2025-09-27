package ayd.proyecto1.fastdelivery.controller;


import ayd.proyecto1.fastdelivery.controller.api.ContractLogApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.ContractLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContractLogController implements ContractLogApi {

    private final ContractLogService contractLogService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllContractLogs() {
        log.info("GET contractLog/all");
        ResponseSuccessfullyDto responseSuccessfullyDto = contractLogService.getAllContractLogs();
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractLogById(Integer id) {
        log.info("GET contractLog/{}", id);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractLogService.getContractLogById(id);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }
}
