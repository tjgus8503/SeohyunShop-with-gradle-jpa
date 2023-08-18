package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Carts;
import seohyun.app.mall.models.Products;
import seohyun.app.mall.models.Purchases;
import seohyun.app.mall.service.*;
import seohyun.app.mall.utils.Jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/purchases")
public class PurchasesController {
    private final PurchasesService purchasesService;
    private final ProductsService productsService;
    private final CartsService cartsService;
    private final Jwt jwt;

    // 상품 주문
    // TODO 받는사람 정보, 결제 정보
    // 상품 주문은 모든 유저 가능.
    @PostMapping("/createpurchase")
    public ResponseEntity<Object> createPurchase(
            @RequestHeader String xauth, @RequestBody List<Purchases> purchasesList) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);
            List<Products> result = purchasesService.updateStock(purchasesList);
            if (result == null) {
                map.put("result", "failed 상품 재고가 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            purchasesService.createPurchases(purchasesList, decoded);
            map.put("result", "success 주문이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 주문 취소
    // 일단은 배송상태 조건 빼고.
    @PostMapping("/deletepurchase")
    public ResponseEntity<Object> deletePurchase(
            @RequestHeader String xauth, @RequestBody Map<String, String> req) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);
            Purchases getByIdAndUserId = purchasesService.getByIdAndUserId(req.get("id"), decoded);
            if (getByIdAndUserId == null) {
                map.put("result", "failed 해당 주문이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            purchasesService.deletePurchase(req.get("id"));
            map.put("result", "success 취소가 완료되었습니다.");
            productsService.addStock(getByIdAndUserId.getProductId(), getByIdAndUserId.getCount());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 주문내역 조회(마이페이지)
    @GetMapping("/getallpurchases")
    public ResponseEntity<Object> getAllPurchases(
            @RequestHeader String xauth,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") Integer pageSize
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);
            List<Purchases> getByUserId = purchasesService.getByUserId(decoded, pageNumber, pageSize);
            return new ResponseEntity<>(getByUserId, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

}
