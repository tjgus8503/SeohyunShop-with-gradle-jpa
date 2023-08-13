package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
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

    // 상품 페이지에서 바로 주문 ver
    // 상품 주문
    // TODO 받는사람 정보, 결제 정보
    // 상품 주문은 모든 유저 가능.
    @PostMapping("/createpurchase")
    public ResponseEntity<Object> createPurchase(
            @RequestHeader String xauth, @RequestBody Purchases purchases) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            Products getById = productsService.getById(purchases.getProductId());
            if (getById.getStock() == 0 || getById.getStock() < purchases.getCount()) {
                map.put("result", "failed 상품 재고가 없습니다.");
            } else {
                UUID uuid = UUID.randomUUID();
                purchases.setId(uuid.toString());
                purchases.setUserId(decoded);
                purchasesService.createPurchase(purchases);
                map.put("result", "success 주문이 완료되었습니다.");
            }
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

            jwt.VerifyToken(xauth);

            purchasesService.deletePurchase(req.get("id"));
            map.put("result", "success 취소가 완료되었습니다.");

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
            @RequestHeader String xauth
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);
            List<Purchases> getByUserId = purchasesService.getByUserId(decoded);
            return new ResponseEntity<>(getByUserId, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품페이지 통한 주문은 리스트 1개면 되고, 장바구니 통한 주문은 리스트 여러개이면 된다.
    // TODO 상품 재고 조건까지 추가해서 createPurchase 와 합치기.
    @PostMapping("/createpurchases")
    public ResponseEntity<Object> createPurchases(
            @RequestHeader String xauth, @RequestBody List<Purchases> purchasesList) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            List<Carts> getByUserId = cartsService.getByUserId(decoded);
            if (getByUserId != null) {
                purchasesService.createPurchases(purchasesList, decoded);
                map.put("result", "success 주문이 완료되었습니다.");
            } else {
                map.put("result", "failed 주문 상품이 장바구니에 없습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
