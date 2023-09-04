package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.ReviewComments;

@Repository
public interface ReviewCommentsRepository extends JpaRepository<ReviewComments, String> {
    ReviewComments findOneByIdAndUserId(String id, String userId);

    ReviewComments findOneByReviewsId(String reviewsId);

    @Modifying
    @Query(value = "delete from reviewcomments where product_id = :id", nativeQuery = true)
    void deleteAllByProductId(@Param("id") String id);
}
