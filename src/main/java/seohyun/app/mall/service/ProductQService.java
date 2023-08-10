package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.ProductInquiries;
import seohyun.app.mall.repository.ProductQRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQService {
    private final ProductQRepository productQRepository;

    @Transactional
    public void createProductQ(ProductInquiries productInquiries) throws Exception {
        try{
            productQRepository.save(productInquiries);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public ProductInquiries getById(String id) throws Exception {
        try{
            return productQRepository.findOneById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
    @Transactional
    public void updateProductQ(ProductInquiries productInquiries) throws Exception {
        try{
            productQRepository.save(productInquiries);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteProductQ(String id) throws Exception {
        try{
            productQRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

}
