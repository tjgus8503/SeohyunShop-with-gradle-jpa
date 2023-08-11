package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, String> {

    Reviews findOneById(String id);

    void deleteByIdAndUserId(String id, String userId);

}
