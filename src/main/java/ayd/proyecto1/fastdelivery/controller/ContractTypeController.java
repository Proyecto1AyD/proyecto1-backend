package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.ContractTypeApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.ContractTypeService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContractTypeController implements ContractTypeApi {

    private final ContractTypeService contractTypeService;

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllContractTypes(Integer userId) {
        log.info("GET contract-type/all");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractTypeService.getAllContractTypes();
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractTypeById(Integer id, Integer userId) {
        log.info("GET contract-type/{}", id);
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractTypeService.getContactTypeById(id);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }
}
