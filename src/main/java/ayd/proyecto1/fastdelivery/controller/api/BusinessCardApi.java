package ayd.proyecto1.fastdelivery.controller.api;


import ayd.proyecto1.fastdelivery.dto.request.NewBussinesCardDto;
import ayd.proyecto1.fastdelivery.dto.response.BussinesCardDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/businessCard")
public interface BusinessCardApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createBusinessCard(@RequestBody NewBussinesCardDto newBussinesCardDto, @RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getBusinessCardById(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/allByIdBusiness/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getAllBusinessCardByIdBusiness(@PathVariable Integer id , @RequestHeader(value = "authorization") Integer userId);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateBusinessCard(@RequestBody BussinesCardDto bussinesCardDto, @RequestHeader(value = "authorization") Integer userId);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteBusinessCard(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @PostMapping("/validateNewCard/{idBusiness}")
    ResponseEntity<ResponseSuccessfullyDto> validateNewCard(@RequestBody Integer idBusiness, @RequestHeader(value = "authorization") Integer userId);

}
