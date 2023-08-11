package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.*;
import seohyun.app.mall.service.*;

import seohyun.app.mall.utils.Jwt;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/comments")
public class CommentsController {
    private final CommentsService commentsService;
    private final ProductQService productQService;
    private final ProductsService productsService;
    private final Jwt jwt;

    // 상품문의 답변 등록
    // 문의한 해당 상품의 판매자만 답변 등록 가능
    @PostMapping("/createcomment")
    public ResponseEntity<Object> createComment(
            @RequestHeader String xauth, @RequestBody Map<String, String> req) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

//            String id = (String) req.get("productId");
            Products getById = productsService.getById(req.get("productId"));

            if (getById.getUserId().equals(decoded)) {

                UUID uuid = UUID.randomUUID();
                // 디비에 저장될 comments
                Comments comments = new Comments();
                comments.setId(uuid.toString());
                comments.setUserId(decoded);
                comments.setProductInquiriesId(req.get("productInquiriesId"));
                comments.setContent(req.get("content"));

                commentsService.createComment(comments);
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

    // 상품 문의 답변 수정
    @PostMapping("/updatecomment")
    public ResponseEntity<Object> updateComment(
            @RequestHeader String xauth, @RequestBody Comments comments
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            Comments getById = commentsService.getById(comments.getId());
            if (getById.getUserId().equals(decoded)) {
                comments.setUserId(decoded);
                commentsService.updateComment(comments);
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

    // 상품 문의 답변 삭제
    @PostMapping("/deletecomment")
    public ResponseEntity<Object> deleteComment(
            @RequestHeader String xauth, @RequestBody Map<String, String> req) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            Comments getById = commentsService.getById(req.get("id"));
            if (getById.getUserId().equals(decoded)) {
                commentsService.deleteComment(req.get("id"));
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
}
