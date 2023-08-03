package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Products;
import seohyun.app.mall.service.ProductsService;
import seohyun.app.mall.utils.Bcrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductsService productsService;

    // 상품 등록
    @PostMapping("/createproduct")
    public ResponseEntity<Object> createProduct(@RequestBody Products products) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            UUID uuid = UUID.randomUUID();
            products.setId(uuid.toString());

            productsService.createProduct(products);
            map.put("result", "success 등록이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 페이지 별 조회. 기본값(page = 0, limit = 10) 설정.
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") Integer pageSize
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();
            Page<Products> productsList = productsService.getAll(pageNumber, pageSize);
            return new ResponseEntity<>(productsList, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 단일 조회.(상품명)
    // 해당 명의 상품이 존재하지 않으면 조회 불가.
    @GetMapping("/get")
    public ResponseEntity<Object> get(
            @RequestParam String productName
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            Boolean productCheck = productsService.productNameCheck(productName);
            if (productCheck == false) {
                map.put("result", "failed 해당 상품을 조회할 수 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            } else {
                Products product  = productsService.get(productName);
                return new ResponseEntity<>(product, HttpStatus.OK);
            }

        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 수정
    @PostMapping("/updateproduct")
    public ResponseEntity<Object> updateProduct(@RequestBody Products products) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            Products productCheck = productsService.productNameCheck(products);
            if (productCheck == null) {
                map.put("result", "failed 해당 상품을 찾을 수 없습니다.");
            } else {
                products.setId(productCheck.getId());
                productsService.updateProduct(products);
                map.put("result", "success 수정이 완료되었습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // TODO 상품 삭제
    // postmapping? getmapping? deleteby 함수도 변형 가능?
    @PostMapping("/deleteproduct")
    public ResponseEntity<Object> deleteProduct(
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            map.put("result", "success 삭제가 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
