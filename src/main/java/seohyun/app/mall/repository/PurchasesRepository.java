package seohyun.app.mall.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Purchases;

import java.util.List;

@Repository
public interface PurchasesRepository extends JpaRepository<Purchases, String> {
    List<Purchases> findByUserId(String userId, Pageable pageable);

    Purchases findByUserIdAndProductId(String userId, String productId);

    Purchases findOneById(String id);

    Purchases findByIdAndUserId(String id, String userId);
}
