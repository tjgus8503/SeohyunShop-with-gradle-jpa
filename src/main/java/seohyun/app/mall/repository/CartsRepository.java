package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Carts;

@Repository
public interface CartsRepository extends JpaRepository<Carts, String> {

}