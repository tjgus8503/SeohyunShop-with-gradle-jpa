package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.ReviewComments;

@Repository
public interface ReviewCommentsRepository extends JpaRepository<ReviewComments, String> {
    ReviewComments findOneByIdAndUserId(String id, String userId);

    void deleteByIdAndUserId(String id, String userId);

    ReviewComments findOneByReviewsId(String reviewsId);
}
