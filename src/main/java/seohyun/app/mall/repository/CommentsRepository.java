package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, String> {

    Comments findOneByIdAndUserId(String id, String userId);

    Comments findOneByProductInquiriesId(String productInquiriesId);

    @Modifying
    @Query(value = "delete from comments where product_id = :id", nativeQuery = true)
    void deleteAllByProductId(@Param("id") String id);
}
