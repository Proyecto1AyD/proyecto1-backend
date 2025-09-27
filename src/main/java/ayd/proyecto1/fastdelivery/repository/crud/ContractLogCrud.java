package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.ContractLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractLogCrud extends JpaRepository<ContractLog, Integer> {
    @Query(value = "select * from contract_log where id_contract = ?", nativeQuery = true)
    List<ContractLog> getContractLogsByIdContract(Integer id_contract);
}
