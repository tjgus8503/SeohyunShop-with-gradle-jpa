package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Purchases;
import seohyun.app.mall.models.Reviews;
import seohyun.app.mall.service.*;
import seohyun.app.mall.utils.Jwt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/reviews")
public class ReviewsController {
    private final ReviewsService reviewsService;
    private final PurchasesService purchasesService;
    private final Jwt jwt;


    // 상품 후기 등록
    @PostMapping("/createreview")
    public ResponseEntity<Object> createReview(
            @RequestHeader String xauth, @RequestBody Reviews reviews
            ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            Purchases checkProduct = purchasesService.checkProduct(decoded, reviews.getProductId());
            if (checkProduct != null) {
                UUID uuid = UUID.randomUUID();
                reviews.setId(uuid.toString());
                reviews.setUserId(decoded);
                reviewsService.createReview(reviews);
                map.put("result", "success 등록이 완료되었습니다.");
            } else {
                map.put("result", "failed 등록 권한이 없습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 후기 수정
    @PostMapping("/updatereview")
    public ResponseEntity<Object> updateReview(
            @RequestHeader String xauth, @RequestBody Reviews reviews
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);
            Reviews getById = reviewsService.getById(reviews.getId());
            if (getById.getUserId().equals(decoded)) {
                reviews.setUserId(decoded);
                reviewsService.updateReview(reviews);
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

    // 상품 후기 삭제
    @PostMapping("/deletereview")
    public ResponseEntity<Object> deleteReview(
            @RequestHeader String xauth, @RequestBody Map<String, String> req
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();
            String decoded =  jwt.VerifyToken(xauth);
            reviewsService.deleteReview(req.get("id"), decoded);
            map.put("result", "success 삭제가 완료되었습니다.");

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }


}
