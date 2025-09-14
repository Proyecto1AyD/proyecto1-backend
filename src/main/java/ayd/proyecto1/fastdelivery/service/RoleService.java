package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.dto.response.RoleInfoDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.RoleCrud;
import ayd.proyecto1.fastdelivery.repository.entities.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleCrud roleCrud;

    public Role getRoleById(Integer id){

        Optional<Role> optionalRole = roleCrud.findById(id);

        if(optionalRole.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Role not exists");
        }

        return optionalRole.get();
    }


    public ResponseSuccessfullyDto getAllRoles(){

        List<Role> roles = roleCrud.findAll();
        List<RoleInfoDto> roleInfoDtoList = new ArrayList<>();

        roles.forEach(role -> {
            RoleInfoDto roleInfoDto = RoleInfoDto.builder().roleId(role.getId()).roleName(role.getRole()).build();
            roleInfoDtoList.add(roleInfoDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(roleInfoDtoList).build();

    }
}
