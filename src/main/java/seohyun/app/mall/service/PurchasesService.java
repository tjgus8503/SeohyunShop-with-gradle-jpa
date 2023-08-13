package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Carts;
import seohyun.app.mall.models.Purchases;
import seohyun.app.mall.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchasesService {
    private final PurchasesRepository purchasesRepository;
    private final CartsRepository cartsRepository;

    @Transactional
    public void createPurchase(Purchases purchases) throws Exception {
        try {
            purchasesRepository.save(purchases);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void createPurchases(List<Purchases> purchasesList, String decoded) throws Exception {
        try{
            List<Purchases> list = new ArrayList<Purchases>();

            for (Purchases purchase : purchasesList) {
                UUID uuid = UUID.randomUUID();
                Purchases value = Purchases.builder()
                        .id(uuid.toString())
                        .userId(decoded)
                        .productId(purchase.getProductId())
                        .count(purchase.getCount())
                        .build();
                list.add(value);
            }
            purchasesRepository.saveAll(list);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deletePurchase(String id) throws Exception {
        try {
            purchasesRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Purchases> getByUserId(String userId) throws Exception {
        try {
            return purchasesRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Purchases checkProduct(String userId, String productId) throws Exception {
        try {
            return purchasesRepository.findByUserIdAndProductId(userId, productId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
