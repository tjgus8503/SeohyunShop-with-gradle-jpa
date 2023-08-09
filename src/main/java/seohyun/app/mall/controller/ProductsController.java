package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Products;
import seohyun.app.mall.models.Users;
import seohyun.app.mall.service.ProductsService;
import seohyun.app.mall.service.UsersService;
import seohyun.app.mall.utils.Bcrypt;
import seohyun.app.mall.utils.Jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductsService productsService;
    private final UsersService usersService;
    private final Jwt jwt;

    // 상품 등록
    // role = 2 만 상품 등록 가능.
    @PostMapping("/createproduct")
    public ResponseEntity<Object> createProduct(
            @RequestHeader String xauth, @RequestBody Products products) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            Users findUserId = usersService.findUserId(decoded);
            if (findUserId.getRole() != 2) {
                map.put("result", "failed 등록 권한이 없습니다.");
            } else {
                UUID uuid = UUID.randomUUID();
                products.setId(uuid.toString());

                productsService.createProduct(products);
                map.put("result", "success 등록이 완료되었습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // TODO 상품 카테고리별 조회, 단일 조회
    // TODO 상품 재고 조회

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

    @GetMapping("/get")
    public ResponseEntity<Object> get(
            @RequestParam String id
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

                Products product  = productsService.getById(id);
                return new ResponseEntity<>(product, HttpStatus.OK);

        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 수정
    // 상품을 등록한 사람 or 관리자 (role = 3)만 수정 가능.
    @PostMapping("/updateproduct")
    public ResponseEntity<Object> updateProduct(
            @RequestHeader String xauth, @RequestBody Products products) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            Users findUserId = usersService.findUserId(decoded);
            Products getById = productsService.getById(products.getId());
            if (findUserId.getRole() == 3 || getById.getUserId().equals(decoded)) {
                products.setUserId(getById.getUserId());
                productsService.updateProduct(products);
                map.put("result", "success 수정이 완료되었습니다.");
            } else {
                map.put("result", "failed 수정 권한이 없습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 삭제
    // 상품을 등록한 사람 or 관리자 (role = 3)만 삭제 가능.
    @PostMapping("/deleteproduct")
    public ResponseEntity<Object> deleteProduct(
            @RequestHeader String xauth, @RequestBody Products products) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            Users findUserId = usersService.findUserId(decoded);
            Products getById = productsService.getById(products.getId());
            if (findUserId.getRole() == 3 || getById.getUserId().equals(decoded)) {
                productsService.deleteProduct(products);
                map.put("result", "success 삭제가 완료되었습니다.");
            } else {
                map.put("result", "failed 삭제 권한이 없습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

//    // 상품 카테고리 별 조회(소분류)
//    @GetMapping("/getbycate")
//    public ResponseEntity<Object> getByCate(@RequestParam String cateId) throws Exception {
//        try{
//            Map<String, String> map = new HashMap<>();
//
//            List<Products> productsList = productsService.getByCate(cateId);
//            return new ResponseEntity<>(productsList, HttpStatus.OK);
//        } catch (Exception e){
//            Map<String, String> map = new HashMap<>();
//            map.put("error", e.toString());
//            return new ResponseEntity<>(map, HttpStatus.OK);
//        }
//    }
//
//    // 상품 카테고리 별 조회(중분류)
//    @GetMapping("/getbyparentcate")
//    public ResponseEntity<Object> getByParentCate(@RequestParam String parentId) throws Exception {
//        try{
//            Map<String, String> map = new HashMap<>();
//
//            List<Products> productsList = productsService.getByParentCate(parentId);
//            return new ResponseEntity<>(productsList, HttpStatus.OK);
//        } catch (Exception e){
//            Map<String, String> map = new HashMap<>();
//            map.put("error", e.toString());
//            return new ResponseEntity<>(map, HttpStatus.OK);
//        }
//    }
}
