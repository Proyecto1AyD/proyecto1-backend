package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.LoginDto;
import ayd.proyecto1.fastdelivery.dto.request.NewUserDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateAuthStatusDto;
import ayd.proyecto1.fastdelivery.dto.request.ValidateCodeDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.dto.response.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/contractStatus")
public interface ContractStatusApi {

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllContractStatuses(@RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractStatusById(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);
}