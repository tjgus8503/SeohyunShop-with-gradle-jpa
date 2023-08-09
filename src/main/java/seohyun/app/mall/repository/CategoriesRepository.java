package seohyun.app.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seohyun.app.mall.models.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
//    List<Categories> findByParentIsNull(); // 부모가 없는 최상위 카테고리 조회
    Categories findOneById(Long id);
}
