package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Carts;
import seohyun.app.mall.models.Products;
import seohyun.app.mall.service.CartsService;
import seohyun.app.mall.service.ProductsService;
import seohyun.app.mall.utils.Jwt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/carts")
public class CartsController {
    private final CartsService cartsService;
    private final ProductsService productsService;
    private final Jwt jwt;

    // 장바구니 등록
    // 상품 재고가 0개 or 상품의 재고보다 장바구니에 담으려는 상품 수가 많으면 장바구니 등록 불가.
    @PostMapping("/createcart")
    public ResponseEntity<Object> createCart(
            @RequestHeader String authorization, @RequestBody Carts carts) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Products getById = productsService.getById(carts.getProductId());
            if (getById.getStock() == 0 || getById.getStock() < carts.getCount()) {
                map.put("result", "failed 상품 재고가 없습니다.");
            } else {
                UUID uuid = UUID.randomUUID();
                carts.setId(uuid.toString());

                carts.setUserId(decoded);

                cartsService.creatCart(carts);
                map.put("result", "success 장바구니에 상품이 등록되었습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 마이페이지 - 장바구니 조회
    @GetMapping("/getallcarts")
    public ResponseEntity<Object> getAllCarts(
            @RequestHeader String authorization,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") Integer pageSize
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Page<Carts> getByUserId = cartsService.getByUserId(decoded, pageNumber, pageSize);
            return new ResponseEntity<>(getByUserId, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }


    // 장바구니 수정
    @PostMapping("/updatecart")
    public ResponseEntity<Object> updateCart(
            @RequestHeader String authorization, @RequestBody Carts carts) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Products getById = productsService.getById(carts.getProductId());
            if (getById.getStock() == 0 || getById.getStock() < carts.getCount()) {
                map.put("result", "failed 상품 재고가 없습니다.");
            } else {
                carts.setUserId(decoded);
                cartsService.updateCart(carts);
                map.put("result", "success 수정이 완료되었습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 장바구니 삭제
    @PostMapping("/deletecart")
    public ResponseEntity<Object> deleteCart(
            @RequestHeader String authorization, @RequestBody Map<String, String> req) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            cartsService.deleteCart(req.get("id"), decoded);
            map.put("result", "success 삭제가 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

}
