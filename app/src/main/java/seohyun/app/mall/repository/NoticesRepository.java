package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Notices;

@Repository
public interface NoticesRepository extends JpaRepository<Notices, Long> {
    Notices findOneById(Long id);
}
