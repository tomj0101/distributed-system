package com.github.tomj0101.ebankv1.ordersystem.repository;

import com.github.tomj0101.ebankv1.ordersystem.domain.OrderMasterV1;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderMasterV1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMasterV1, Long> {
    @Query("select orderMaster from OrderMasterV1 orderMaster where orderMaster.user.login = ?#{principal.username}")
    List<OrderMasterV1> findByUserIsCurrentUser();
}
