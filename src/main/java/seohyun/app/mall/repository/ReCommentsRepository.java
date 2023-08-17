package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.ReComments;

@Repository
public interface ReCommentsRepository extends JpaRepository<ReComments, String> {

    ReComments findOneByIdAndUserId(String id, String userId);

    ReComments findOneByCommentsId(String commentsId);

}
