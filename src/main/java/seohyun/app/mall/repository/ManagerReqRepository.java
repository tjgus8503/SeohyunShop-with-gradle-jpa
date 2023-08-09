package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.ManagerRequests;

@Repository
public interface ManagerReqRepository extends JpaRepository<ManagerRequests, String> {
}
