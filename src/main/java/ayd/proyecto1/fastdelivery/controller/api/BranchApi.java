package ayd.proyecto1.fastdelivery.controller.api;


import ayd.proyecto1.fastdelivery.dto.request.NewBranchDto;
import ayd.proyecto1.fastdelivery.dto.response.BranchDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/branch")
public interface BranchApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createBranch(@RequestBody NewBranchDto newBranchDto,
                                                @RequestHeader(value = "authorization") Integer userId);


    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllBranches(@RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getBranchById(@PathVariable Integer id,
                                                          @RequestHeader(value = "authorization") Integer userId);


    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateBranch(@RequestBody BranchDto branchDto,
                                                         @RequestHeader(value = "authorization") Integer userId);


    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteBranch(@PathVariable Integer id,
                                                         @RequestHeader(value = "authorization") Integer userId);


}
