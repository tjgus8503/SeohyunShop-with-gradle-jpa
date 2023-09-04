package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Reviews;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, String> {

    @Modifying
    @Query(value = "delete from reviews where product_id = :id", nativeQuery = true)
    void deleteAllByProductId(@Param("id") String id);

    Reviews findOneByIdAndUserId(String id, String userId);


}
