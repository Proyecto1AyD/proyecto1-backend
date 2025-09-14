package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.BranchApi;
import ayd.proyecto1.fastdelivery.dto.request.NewBranchDto;
import ayd.proyecto1.fastdelivery.dto.response.BranchDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.BranchService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BranchController implements BranchApi {

    private final BranchService branchService;

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createBranch(NewBranchDto newBranchDto, Integer userId) {
        log.info("POST /branch");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = branchService.createBranch(newBranchDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllBranches(Integer userId) {
        log.info("GET /branch/all");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = branchService.getAllBranch();
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getBranchById(Integer id, Integer userId) {
        log.info("GET /branch/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = branchService.getBranchById(id);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateBranch(BranchDto branchDto, Integer userId) {
        log.info("PUT /branch");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = branchService.updateBranch(branchDto);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteBranch(Integer id, Integer userId) {
        log.info("DELETE /branch/{id}");
        userService.validateAuthorizationHeader(userId);
        ResponseSuccessfullyDto responseSuccessfullyDto = branchService.deleteBranch(id);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }
}
