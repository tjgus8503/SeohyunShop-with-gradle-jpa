package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.ProductInquiries;

import java.util.List;

@Repository
public interface ProductQRepository extends JpaRepository<ProductInquiries, String> {

    @Modifying
    @Query(value = "delete from product_inquiries where product_id = :id", nativeQuery = true)
    void deleteAllByProductId(@Param("id") String id);

    ProductInquiries findOneById(String id);

    ProductInquiries findOneByIdAndUserId(String id, String userId);
}