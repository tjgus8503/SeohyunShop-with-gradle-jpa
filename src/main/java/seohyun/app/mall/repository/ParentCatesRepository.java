package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.ParentCategories;

@Repository
public interface ParentCatesRepository extends JpaRepository<ParentCategories, String> {
}
