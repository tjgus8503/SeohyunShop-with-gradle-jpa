package seohyun.app.mall.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Products;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {

    @Query(value = "select *, (select count(*) from reviews r where product_id = :id) as review_count," +
            "(select count(*) from product_inquiries pi where product_id = :id) as inquiries_count from products where id = :id", nativeQuery = true)
    Map<String, Object> getProductWithCount(@Param("id") String id);

    Products findOneById(String id);

    List<Products> findByCateId(Integer cateId, Pageable pageable);

    List<Products> findByUserId(String userId, Pageable pageable);


    @Query(value = "select * from products where id = :id and stock >= :stock", nativeQuery = true)
    Products getProductByIdAndStock(@Param("id") String id, @Param("stock") Long stock);

    @Modifying(clearAutomatically = true)
    @Query(value = "update products set stock = stock + :stock where id = :id", nativeQuery = true)
    int addStock(@Param("id") String id, @Param("stock") Long stock);

}
