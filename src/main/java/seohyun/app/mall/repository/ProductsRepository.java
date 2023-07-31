package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {
    Products findOneByProductName(String productName);
    Boolean existsByProductName(String productName);
    void deleteByProductName(String productName);
}
