package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.ProductInquiries;

@Repository
public interface ProductQRepository extends JpaRepository<ProductInquiries, String> {


}
