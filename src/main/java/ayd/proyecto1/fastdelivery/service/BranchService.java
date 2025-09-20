package ayd.proyecto1.fastdelivery.service;

import ayd.proyecto1.fastdelivery.dto.request.NewBranchDto;
import ayd.proyecto1.fastdelivery.dto.response.BranchDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.exception.BusinessException;
import ayd.proyecto1.fastdelivery.repository.crud.BranchCrud;
import ayd.proyecto1.fastdelivery.repository.entities.Branch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.InjectService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BranchService {

    private final BranchCrud branchCrud;

    public ResponseSuccessfullyDto createBranch(NewBranchDto newBranchDto){
        Branch branch = new Branch();
        branch.setName(newBranchDto.getName());
        branch.setAddress(newBranchDto.getAddress());
        branch.setPhone(newBranchDto.getPhone());

        try{
            branchCrud.save(branch);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("Sucursal creada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al guardar la sucursal");
        }
    }


    public ResponseSuccessfullyDto getBranchById(Integer id){
        Optional<Branch> optionalBranch = branchCrud.findById(id);

        if(optionalBranch.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La sucursal no ha sido encontrada");
        }
        Branch branch = optionalBranch.get();
        BranchDto branchDto = BranchDto.builder().id(branch.getId()).name(branch.getName()).phone(branch.getPhone()).address(branch.getAddress()).build();

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(branchDto).build();
    }


    public ResponseSuccessfullyDto getAllBranch(){
        List<Branch> branchList = branchCrud.findAll();
        ArrayList<BranchDto> branchDtoList = new ArrayList<>();

        branchList.forEach(branch -> {
            BranchDto branchDto = BranchDto.builder().id(branch.getId()).name(branch.getName()).phone(branch.getPhone()).address(branch.getAddress()).build();
            branchDtoList.add(branchDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(branchDtoList).build();
    }

    public ResponseSuccessfullyDto updateBranch(BranchDto branchDto){

        Optional<Branch> optionalBranch = branchCrud.findById(branchDto.getId());

        if(optionalBranch.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La sucursal no ha sido encontrada");
        }
        Branch branch = optionalBranch.get();
        branch.setName(branchDto.getName());
        branch.setPhone(branchDto.getPhone());
        branch.setAddress(branchDto.getAddress());

        try{
            branchCrud.save(branch);
            log.info("Sucursal actualizada...");
            return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Sucursal actualizada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al actualizar una sucursal");
        }
    }

    public ResponseSuccessfullyDto deleteBranch(Integer branchId){

        Optional<Branch> optionalBranch = branchCrud.findById(branchId);

        if(optionalBranch.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La sucursal no ha sido encontrada");
        }

        Branch branch = optionalBranch.get();

        try{
            branchCrud.delete(branch);
            return ResponseSuccessfullyDto.builder().code(HttpStatus.ACCEPTED).message("Sucursal eliminada exitosamente").build();
        }catch (Exception exception){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"Error al eliminar la sucursal");
        }
    }

    public Branch getById(Integer branchId){
        Optional<Branch> optionalBranch = branchCrud.findById(branchId);

        if(optionalBranch.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"La sucursal no ha sido encontrada");
        }

        return optionalBranch.get();
    }

}
