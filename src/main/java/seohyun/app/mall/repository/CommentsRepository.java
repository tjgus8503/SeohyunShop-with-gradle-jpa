package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, String> {

    Comments findOneByIdAndUserId(String id, String userId);

    Comments findOneByProductInquiriesId(String productInquiriesId);
}
