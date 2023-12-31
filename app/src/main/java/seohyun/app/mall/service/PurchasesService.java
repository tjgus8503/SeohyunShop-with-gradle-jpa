package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Carts;
import seohyun.app.mall.models.Products;
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
    private final ProductsRepository productsRepository;



    public List<Products> updateStock(List<Purchases> purchasesList) throws Exception {
        try{
            List<Products> list = new ArrayList<>();
            for (Purchases purchase : purchasesList) {
                Products products = productsRepository.getProductByIdAndStock(purchase.getProductId(), purchase.getCount());
                if (products == null) {
                    return null;
                }
                products.setStock((int) (products.getStock() - purchase.getCount()));
                list.add(products);

;            }
             return productsRepository.saveAll(list);
        } catch (Exception e){
            throw new Exception(e);
        }
    }


    @Transactional
    public void createPurchases(List<Purchases> purchasesList, String decoded) throws Exception {
        try{
            List<Purchases> list = new ArrayList<>();

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

    public List<Purchases> getByUserId(String userId, Integer pageNumber, Integer pageSize) throws Exception {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return purchasesRepository.findByUserId(userId, pageable);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Purchases getByIdAndUserId(String id, String userId) throws Exception {
        try{
            return purchasesRepository.findByIdAndUserId(id, userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
