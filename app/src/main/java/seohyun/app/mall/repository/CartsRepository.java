package seohyun.app.mall.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Carts;


@Repository
public interface CartsRepository extends JpaRepository<Carts, String> {

    void deleteByIdAndUserId(String id, String userId);

    Page<Carts> findByUserId(String userId, Pageable pageable);
}
