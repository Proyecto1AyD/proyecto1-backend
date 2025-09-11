package ayd.proyecto1.fastdelivery.repository.crud;

import ayd.proyecto1.fastdelivery.repository.entities.ChatCoordinatorDeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatCoordinatorDeliveryPersonCrud extends JpaRepository<ChatCoordinatorDeliveryPerson, Integer> {
}
