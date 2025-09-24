package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.NewContractDto;
import ayd.proyecto1.fastdelivery.dto.response.ContractDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/contract")
public interface ContractApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createContract(@RequestBody NewContractDto newContractDto, @RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractById(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateContract(@RequestBody ContractDto contractDto, @RequestHeader(value = "authorization") Integer userId);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteContract(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);
}
