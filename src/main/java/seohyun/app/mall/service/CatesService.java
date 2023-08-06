package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Categories;
import seohyun.app.mall.models.ParentCategories;
import seohyun.app.mall.models.Products;
import seohyun.app.mall.repository.CartsRepository;
import seohyun.app.mall.repository.CatesRepository;
import seohyun.app.mall.repository.ParentCatesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatesService {
    private final CatesRepository catesRepository;
    private final ParentCatesRepository parentCatesRepository;

    @Transactional
    public void createCate(Categories categories) throws Exception {
        try{
            catesRepository.save(categories);
        } catch (Exception e){
            throw new Exception();
        }
    }

    @Transactional
    public void createParentCate(ParentCategories parentCategories) throws Exception {
        try{
            parentCatesRepository.save(parentCategories);
        } catch (Exception e){
            throw new Exception();
        }
    }

}
