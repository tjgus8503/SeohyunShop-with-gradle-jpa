package seohyun.app.mall.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Boolean existsByUserId(String userId);
    Users findOneByUserId(String userId);
    Users findByUserIdAndPassword(String userId, String password);

    @Modifying(clearAutomatically = true)
    @Query(value = "update users set role = 2 where user_id = :user_id and role = 1", nativeQuery = true)
    Integer updateRole(@Param("user_id") String userId);
}
