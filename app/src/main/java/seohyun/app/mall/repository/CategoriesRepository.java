package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Categories findOneById(Long id);
}
