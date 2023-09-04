package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.ReComments;

@Repository
public interface ReCommentsRepository extends JpaRepository<ReComments, String> {

    ReComments findOneByIdAndUserId(String id, String userId);

    ReComments findOneByCommentsId(String commentsId);

    @Modifying
    @Query(value = "delete from recomments where product_id = :id", nativeQuery = true)
    void deleteAllByProductId(@Param("id") String id);

}
