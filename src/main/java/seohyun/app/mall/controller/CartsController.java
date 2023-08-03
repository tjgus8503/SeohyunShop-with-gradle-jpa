package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Carts;
import seohyun.app.mall.service.CartsService;
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
    private final Jwt jwt;

    // 장바구니 등록
    // 한 사용자는 여러개의 장바구니를 등록할 수 있다. 한 상품은 여러개의 장바구니에 등록될 수 있다.
    // TODO 상품 재고 상태 조건 적용(ex 품절이면 장바구니 등록 불가)
    @PostMapping("/createcart")
    public ResponseEntity<Object> createCart(
            @RequestHeader String xauth, @RequestBody Carts carts) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            UUID uuid = UUID.randomUUID();
            carts.setId(uuid.toString());

            carts.setUserId(decoded);

            cartsService.creatCart(carts);
            map.put("result", "success 장바구니에 상품이 등록되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 장바구니 조회

    // 장바구니 수정
    @PostMapping("/updatecart")
    public ResponseEntity<Object> updateCart(
            @RequestHeader String xauth, @RequestBody Carts carts) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);


            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 장바구니 삭제
}
