package ayd.proyecto1.fastdelivery.controller;


import ayd.proyecto1.fastdelivery.controller.api.RoleApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RoleController implements RoleApi {


    private final RoleService roleService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllRoles() {
        log.info("GET role/all");
        ResponseSuccessfullyDto responseSuccessfullyDto = roleService.getAllRoles();
        return ResponseEntity.ok(responseSuccessfullyDto);
    }
}
