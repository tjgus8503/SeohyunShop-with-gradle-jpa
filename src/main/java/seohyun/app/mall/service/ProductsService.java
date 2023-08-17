package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Products;
import seohyun.app.mall.repository.ProductsRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductsService {
    private final ProductsRepository productsRepository;

    @Transactional
    public void createProduct(Products products) throws Exception {
        try{
            productsRepository.save(products);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Page<Products> getAll(Integer pageNumber, Integer pageSize) throws Exception {
        try{
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return productsRepository.findAll(pageable);
        } catch (Exception e){
            throw new Exception(e);
        }
    }


    public Products getById(String id) throws Exception {
        try{
            return productsRepository.findOneById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Map<String, Object> getProductWithCount(String id) throws Exception {
        try{
            return productsRepository.getProductWithCount(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void updateProduct(Products products) throws Exception {
        try{
            productsRepository.save(products);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteProduct(String id) throws Exception {
        try{
            productsRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Products> getProductsByCate(Integer cateId, Integer pageNumber, Integer pageSize) throws Exception {
        try{
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return productsRepository.findByCateId(cateId, pageable);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public int subtractStock(String id, Long stock) throws Exception {
        try{
            return productsRepository.subtractStock(id, stock);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public int addStock(String id, Long stock) throws Exception {
        try{
            return productsRepository.addStock(id, stock);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Products> getByUserId(
            String userId, Integer pageNumber, Integer pageSize) throws Exception {
        try{
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return productsRepository.findByUserId(userId, pageable);
        } catch (Exception e){
            throw new Exception(e);
        }
    }


}
