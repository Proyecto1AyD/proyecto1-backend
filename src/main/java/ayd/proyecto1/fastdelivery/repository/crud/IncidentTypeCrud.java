package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.ContractType;
import ayd.proyecto1.fastdelivery.repository.entities.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentTypeCrud extends JpaRepository<IncidentType, Integer> {
    @Query(value = "select * from incident_type where is_customer = ? ", nativeQuery = true)
    List<IncidentType> getIncidentTypeByIsCustomer(Boolean is_customer);
}
