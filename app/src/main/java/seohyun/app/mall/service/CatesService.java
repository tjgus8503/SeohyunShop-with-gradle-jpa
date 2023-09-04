package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Categories;
import seohyun.app.mall.repository.CategoriesRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatesService {
    private final CategoriesRepository categoriesRepository;


    @Transactional
    public void createCate(Categories categories) throws Exception {
        try{
            categoriesRepository.save(categories);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

}
