package ayd.proyecto1.fastdelivery.controller.api;


import ayd.proyecto1.fastdelivery.dto.request.NewBranchDto;
import ayd.proyecto1.fastdelivery.dto.request.NewCardDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateEntityDto;
import ayd.proyecto1.fastdelivery.dto.response.BranchDto;
import ayd.proyecto1.fastdelivery.dto.response.CardDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/card")
public interface CardApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createCard(@RequestBody NewCardDto newCardDto, @RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllCard(@RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getCardById(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateCard(@RequestBody CardDto cardDto, @RequestHeader(value = "authorization") Integer userId);
    //cambiar
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteBranch(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

}
