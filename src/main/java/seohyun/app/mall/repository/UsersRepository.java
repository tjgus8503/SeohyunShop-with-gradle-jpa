package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seohyun.app.mall.models.Users;

public interface UsersRepository extends JpaRepository<Users, String> {

    Boolean existsByUserId(String userId);
    Users findOneByUserId(String userId);
}
