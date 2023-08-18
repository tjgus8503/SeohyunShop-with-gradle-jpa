package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Reviews;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, String> {

    List<Reviews> findByProductId(String productId);

    Reviews findOneByIdAndUserId(String id, String userId);


}
