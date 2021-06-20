package com.github.tomj0101.ebankv1.ordersystemv2.repository;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.OrderMaster;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster, Long> {
    @Query("select orderMaster from OrderMaster orderMaster where orderMaster.user.login = ?#{principal.username}")
    List<OrderMaster> findByUserIsCurrentUser();
}
