package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.BusinessCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessCardCrud extends JpaRepository<BusinessCard, Integer> {

    @Query(value = "select * from business_card where id_business = ?", nativeQuery = true)
    List<BusinessCard> getBusinessCardByIdBusiness(Integer id_business);

    @Query(value = "select * from business_card where id_business = ? and active = 1 limit 1", nativeQuery = true)
    BusinessCard getBusinessCardActiveByIdBusiness(Integer id_business);
}
