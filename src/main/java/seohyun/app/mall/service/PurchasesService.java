package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Purchases;
import seohyun.app.mall.repository.ProductsRepository;
import seohyun.app.mall.repository.PurchasesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchasesService {
    private final PurchasesRepository purchasesRepository;

    @Transactional
    public void createPurchase(Purchases purchases) throws Exception {
        try{
            purchasesRepository.save(purchases);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deletePurchase(String id) throws Exception {
        try{
            purchasesRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Purchases> getByUserId(String userId) throws Exception {
        try{
            return purchasesRepository.findByUserId(userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Purchases checkProduct(String userId, String productId) throws Exception {
        try{
            return purchasesRepository.findByUserIdAndProductId(userId, productId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
