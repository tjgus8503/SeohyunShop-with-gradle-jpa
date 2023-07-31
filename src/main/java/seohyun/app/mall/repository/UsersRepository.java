package seohyun.app.mall.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Boolean existsByUserId(String userId);
    Users findOneByUserId(String userId);
    Users findByUserIdAndPassword(String userId, String password);
}
