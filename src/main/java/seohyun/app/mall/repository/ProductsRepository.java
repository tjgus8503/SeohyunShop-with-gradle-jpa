package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Products;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {
    Products findOneByProductName(String productName);
    Boolean existsByProductName(String productName);
    void deleteByProductName(String productName);

    List<Products> findOneByCateId(String cateId);

    // 네이티브 sql 쿼리문
    @Query(value = "select p.* from products p " +
            "join categories c on p.cate_id = c.id where c.parent_id = :parentId", nativeQuery = true)
    List<Products> findOneByParentId(@Param("parentId") String parentId);
}
