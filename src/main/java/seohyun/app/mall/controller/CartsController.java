package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Carts;
import seohyun.app.mall.service.CartsService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/carts")
public class CartsController {
    private final CartsService cartsService;

    // 장바구니 등록
    // TODO 토큰 적용
    @PostMapping("/createcart")
    public ResponseEntity<Object> createCart(@RequestBody Carts carts) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            UUID uuid = UUID.randomUUID();
            carts.setId(uuid.toString());

            cartsService.creatCart(carts);
            map.put("result", "success 장바구니에 상품이 등록되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
