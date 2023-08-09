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
            throw new Exception();
        }
    }

    public Page<Products> getAll(Integer pageNumber, Integer pageSize) throws Exception {
        try{
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return productsRepository.findAll(pageable);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public Products get(String productName) throws Exception {
        try{
            return productsRepository.findOneByProductName(productName);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public Products productNameCheck(Products products) throws Exception{
        try{
            return productsRepository.findOneByProductName(products.getProductName());
        } catch (Exception e){
            throw new Exception();
        }
    }

    public Products getById(String id) throws Exception {
        try{
            return productsRepository.findOneById(id);
        } catch (Exception e){
            throw new Exception();
        }
    }
    @Transactional
    public void updateProduct(Products products) throws Exception {
        try{
            productsRepository.save(products);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Transactional
    public void deleteProduct(Products products) throws Exception {
        try{
            productsRepository.deleteById(products.getId());
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<Products> getByCate(String cateId) throws Exception {
        try{
            return productsRepository.findOneByCateId(cateId);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public List<Products> getByParentCate(String parentId) throws Exception {
        try{
            return productsRepository.findOneByParentId(parentId);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
