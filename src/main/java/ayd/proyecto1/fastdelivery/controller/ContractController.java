package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.ContractApi;
import ayd.proyecto1.fastdelivery.dto.request.NewContractDto;
import ayd.proyecto1.fastdelivery.dto.response.ContractDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.ContractService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContractController implements ContractApi {

    private final ContractService contractService;

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createContract(NewContractDto newContractDto, Integer userId) {
        log.info("POST /contract");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractService.createContract(newContractDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractById(Integer id, Integer userId) {
        log.info("GET /contract/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractService.getContractById(id);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateContract(ContractDto contractDto, Integer userId) {
        log.info("PUT /contract");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractService.updateContract(contractDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteContract(Integer id, Integer userId) {
        log.info("DELETE /contract/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = contractService.deleteContract(id);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }
}
