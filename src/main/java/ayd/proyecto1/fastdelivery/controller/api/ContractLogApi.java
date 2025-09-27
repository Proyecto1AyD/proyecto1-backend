package ayd.proyecto1.fastdelivery.controller.api;


import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/contractLog")
public interface ContractLogApi {

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllContractLogs();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractLogById(@PathVariable Integer id);
}