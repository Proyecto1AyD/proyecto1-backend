package ayd.proyecto1.fastdelivery.controller;


import ayd.proyecto1.fastdelivery.controller.api.ContractStatusApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.ContractStatusService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContractStatusController implements ContractStatusApi {

    private final ContractStatusService contractStatusService;

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllContractStatuses(Integer userId) {
        log.info("GET contractStatus/all");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractStatusService.getAllContactStatus();
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractStatusById(Integer id, Integer userId) {
        log.info("GET contractStatus/{}", id);
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractStatusService.getContactStatusById(id);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }
}
