package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Categories;

@Repository
public interface CatesRepository extends JpaRepository<Categories, String> {
}
