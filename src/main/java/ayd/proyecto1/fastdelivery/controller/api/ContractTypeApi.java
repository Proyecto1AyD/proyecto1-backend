package ayd.proyecto1.fastdelivery.controller.api;


import ayd.proyecto1.fastdelivery.dto.request.NewCardDto;
import ayd.proyecto1.fastdelivery.dto.response.CardDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/contractType")
public interface ContractTypeApi {

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllContractTypes(@RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractTypeById(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);
}