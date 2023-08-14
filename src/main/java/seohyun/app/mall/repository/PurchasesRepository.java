package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Purchases;

import java.util.List;

@Repository
public interface PurchasesRepository extends JpaRepository<Purchases, String> {
    List<Purchases> findByUserId(String userId);

    Purchases findByUserIdAndProductId(String userId, String productId);

    Purchases findOneById(String id);
}
