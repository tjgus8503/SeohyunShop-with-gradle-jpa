package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seohyun.app.mall.models.*;
import seohyun.app.mall.service.*;
import seohyun.app.mall.utils.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductsService productsService;
    private final UsersService usersService;
    private final ProductQService productQService;
    private final ReviewsService reviewsService;
    private final CommentsService commentsService;
    private final RecommentsService recommentsService;
    private final Jwt jwt;
    private final ImageRegister imageRegister;

    // 상품 등록
    // role = 2 만 상품 등록 가능.
    @PostMapping("/createproduct")
    public ResponseEntity<Object> createProduct(
            @RequestHeader String authorization, @ModelAttribute Products products,
            @RequestPart(required = false) MultipartFile[] image
    ) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            String decoded = jwt.VerifyToken(authorization);

            Users findUserId = usersService.findUserId(decoded);
            if (findUserId.getRole() != 2) {
                map.put("result", "failed 등록 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

                UUID uuid = UUID.randomUUID();
                products.setId(uuid.toString());
                products.setUserId(decoded);

                if (image != null) {
                    List<String> result = imageRegister.CreateImages(image);

                    String multiImages = String.join(",", result);
                    products.setImageUrl(multiImages);

                } else {
                    products.setImageUrl(null);
                }
                productsService.createProduct(products);
                map.put("result", "success 등록이 완료되었습니다.");

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }


    // 상품 전체 조회
    // 페이지 단위 조회. 기본값(page = 0, limit = 10) 설정.
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") Integer pageSize
    ) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            Page<Products> productsList = productsService.getAll(pageNumber, pageSize);
            return new ResponseEntity<>(productsList, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 단일 조회
    @GetMapping("/detailpage")
    public ResponseEntity<Object> detailPage(
            @RequestParam String id
    ) throws Exception {
        try {

            Map<String, Object> product = productsService.getProductWithCount(id);
            return new ResponseEntity<>(product, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 수정
    // 상품을 등록한 사람 or 관리자 (role = 3)만 수정 가능.
    @PostMapping("/updateproduct")
    public ResponseEntity<Object> updateProduct(
            @RequestHeader String authorization, @ModelAttribute Products products,
            @RequestPart(required = false) MultipartFile[] image) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            String decoded = jwt.VerifyToken(authorization);
            Users findUserId = usersService.findUserId(decoded);
            Products getById = productsService.getById(products.getId());

            if (!(decoded.equals(getById.getUserId())|| findUserId.getRole() == 3)) {
                map.put("result", "failed 수정 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

            products.setUserId(decoded);
            if (image != null) {
                List<String> result = imageRegister.CreateImages(image);
                String multiImages = String.join(",", result);
                products.setImageUrl(multiImages);
            }
            productsService.updateProduct(products);
            map.put("result", "success 수정이 완료되었습니다.");
            // 기존 파일은 삭제
            new Thread() {
                public void run() {
                    try{
                        imageRegister.DeleteFile(getById.getImageUrl());
                    } catch (Exception e){
                        // 에러의 발생근원지를 찾아서 단계별로 에러 출력.
                        e.printStackTrace();
                    }
                }
            }.start();
            return new ResponseEntity<>(map, HttpStatus.OK);
            } catch(Exception e){
                Map<String, String> map = new HashMap<>();
                map.put("error", e.toString());
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }

    // 상품 삭제 (상품 등록 시 이미지도 함께 올렸을 경우 상품이 삭제되면서 이미지도 서버에서 삭제.)
    // 상품을 등록한 사람 or 관리자 (role = 3)만 삭제 가능.
    @PostMapping("/deleteproduct")
    public ResponseEntity<Object> deleteProduct(
            @RequestHeader String authorization, @RequestBody Map<String, String> deleteReq) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Users findUserId = usersService.findUserId(decoded);
            Products getById = productsService.getById(deleteReq.get("id"));
            if (!(findUserId.getRole() == 3 || getById.getUserId().equals(decoded))) {
                map.put("result", "failed 삭제 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            productsService.deleteProduct(deleteReq.get("id"));
            map.put("result", "success 삭제가 완료되었습니다.");

            new Thread() {
                public void run() {
                    try{
                        imageRegister.DeleteFile(getById.getImageUrl());
                    } catch (Exception e){
                        // 에러의 발생근원지를 찾아서 단계별로 에러 출력.
                        e.printStackTrace();
                    }
                }
            }.start();

            // 해당상품 관련 문의글 삭제(+문의글의 답변+답변의 댓글)
            productQService.deleteProductQByProductId(deleteReq.get("id"));
            commentsService.deleteCommentByProductId(deleteReq.get("id"));
            recommentsService.deleteReCommentByProductId(deleteReq.get("id"));


            // 해당상품 관련 후기글 삭제(+후기글의 답변)
            reviewsService.deleteReviewByProductId(deleteReq.get("id"));
            reviewsService.deleteReviewCommentByProductId(deleteReq.get("id"));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 조회 (카테고리 별)
    @GetMapping("/getproductsbycate")
    public ResponseEntity<Object> getProductsByCate(
            @RequestParam Integer cateId,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") Integer pageSize
            ) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();

            List<Products> getProductsByCate = productsService.getProductsByCate(cateId, pageNumber, pageSize);
            return new ResponseEntity<>(getProductsByCate, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }



    // 상품 이미지 삭제
    @PostMapping("/deleteimage")
    public ResponseEntity<Object> deleteImage(
            @RequestHeader String authorization, @ModelAttribute Products products,
            @RequestPart(required = false) MultipartFile image
    ) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            String decoded = jwt.VerifyToken(authorization);

            Users findUserId = usersService.findUserId(decoded);
            Products getById = productsService.getById(products.getId());
            if (findUserId.getRole() == 3 || getById.getUserId().equals(decoded)) {
                products.setUserId(decoded);

                Products findById = productsService.getById(products.getId());
                if (findById.getImageUrl() != null) {

                    Path filePath = Paths.get(findById.getImageUrl());
                    Files.delete(filePath);

                    productsService.updateProduct(products);
                    map.put("result", "success 수정이 완료되었습니다.");
                } else {
                    map.put("result", "failed 삭제할 이미지가 없습니다.");
                }
            } else {
                map.put("result", "failed 수정 권한이 없습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
            } catch(Exception e){
                Map<String, String> map = new HashMap<>();
                map.put("error", e.toString());
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        }

    // 상품 이미지 여러장 삭제
    @PostMapping("/deleteimages")
    public ResponseEntity<Object> deleteProduct2(
            @RequestHeader String authorization, @RequestBody Map<String, String> req
    ) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Users findUserId = usersService.findUserId(decoded);
            Products getById = productsService.getById(req.get("id"));

            if (findUserId.getRole() == 3 || getById.getUserId().equals(decoded)) {

                Products findById = productsService.getById(req.get("id"));
                if (findById.getImageUrl() != null) {

                    List<String> test = List.of(findById.getImageUrl().split(","));
                    for (String file : test) {
                        Files.delete(Path.of(file));
                    }
                    findById.setImageUrl(null);
                    productsService.updateProduct(findById);
                    map.put("result", "삭제가 완료되었습니다.");
                } else {
                    map.put("result", "failed 삭제할 이미지가 없습니다.");
                }
            } else {
                map.put("result", "failed 삭제 권한이 없습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 본인이 올린 상품들 조회
    // 페이지 단위로 조회. (기본값 page = 0, limit = 10 설정)
    @GetMapping("/getproductsbyuser")
    public ResponseEntity<Object> getProductsByUser(
            @RequestHeader String authorization,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") Integer pageSize
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);
            List<Products> productsList = productsService.getByUserId(decoded, pageNumber, pageSize);
            return new ResponseEntity<>(productsList, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }


}