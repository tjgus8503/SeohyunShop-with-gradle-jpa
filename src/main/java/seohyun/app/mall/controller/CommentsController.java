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
    private final RecommentsService recommentsService;
    private final Jwt jwt;

    // 상품문의 답변 등록
    // 문의한 해당 상품의 판매자만 답변 등록 가능
    @PostMapping("/createcomment")
    public ResponseEntity<Object> createComment(
            @RequestHeader String authorization, @RequestBody Map<String, String> req) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Products getById = productsService.getById(req.get("productId"));
            if (!(getById.getUserId().equals(decoded))) {
                map.put("result", "failed 등록 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            UUID uuid = UUID.randomUUID();
            // 디비에 저장될 comments
            Comments comments = new Comments();
            comments.setId(uuid.toString());
            comments.setUserId(decoded);
            comments.setProductInquiriesId(req.get("productInquiriesId"));
            comments.setContent(req.get("content"));

            commentsService.createComment(comments);
            map.put("result", "success 등록이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 문의 답변 수정
    // 본인(답변 단 판매자)만 수정 가능
    @PostMapping("/updatecomment")
    public ResponseEntity<Object> updateComment(
            @RequestHeader String authorization, @RequestBody Comments comments) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Comments getByIdAndUserId = commentsService.getByIdAndUserId(comments.getId(), decoded);
            if (getByIdAndUserId == null) {
                map.put("result", "failed 해당 답변이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            comments.setUserId(decoded);
            commentsService.updateComment(comments);
            map.put("result", "success 수정이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품 문의 답변 삭제
    // 본인(답변 단 판매자)만 삭제 가능
    @PostMapping("/deletecomment")
    public ResponseEntity<Object> deleteComment(
            @RequestHeader String authorization, @RequestBody Map<String, String> req) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Comments getByIdAndUserId = commentsService.getByIdAndUserId(req.get("id"), decoded);
            if (getByIdAndUserId == null) {
                map.put("result", "failed 해당 답변이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            commentsService.deleteComment(req.get("id"));
            map.put("result", "success 삭제가 완료되었습니다.");

            // 해당답변의 댓글(대댓글) 삭제.
            ReComments reComment = recommentsService.getByCommentsId(req.get("id"));
            if (reComment != null) {
                recommentsService.deleteReComment(reComment.getId());
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품문의 답변의 댓글(대댓글) 등록
    @PostMapping("/createrecomment")
    public ResponseEntity<Object> createReComment(
            @RequestHeader String authorization, @RequestBody Map<String, String> req
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            ProductInquiries getById = productQService.getById(req.get("productInquiriesId"));
            if (!getById.getUserId().equals(decoded)) {
                map.put("result", "failed 등록 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            UUID uuid = UUID.randomUUID();
            ReComments reComments = new ReComments();
            reComments.setId(uuid.toString());
            reComments.setUserId(decoded);
            reComments.setCommentsId(req.get("commentsId"));
            reComments.setContent(req.get("content"));
            recommentsService.createReComment(reComments);
            map.put("result", "success 등록이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 대댓글 수정
    // 대댓글 올린 본인만 가능.
    @PostMapping("/updaterecomment")
    public ResponseEntity<Object> updateReComment(
            @RequestHeader String authorization, @RequestBody ReComments reComments
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            ReComments getByIdAndUserId = recommentsService.getByIdAndUserId(reComments.getId(), decoded);
            if (getByIdAndUserId == null) {
                map.put("result", "failed 해당 댓글이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            reComments.setUserId(decoded);
            recommentsService.updateReComment(reComments);
            map.put("result", "success 수정이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 대댓글 삭제.
    // 대댓글 올린 본인만 가능.
    @PostMapping("/deleterecomment")
    public ResponseEntity<Object> deleteReComment(
            @RequestHeader String authorization, @RequestBody Map<String, String> req
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            ReComments getByIdAndUserId = recommentsService.getByIdAndUserId(req.get("id"), decoded);
            if (getByIdAndUserId == null) {
                map.put("result", "failed 해당 댓글이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            recommentsService.deleteReComment(req.get("id"));
            map.put("result", "success 삭제가 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 대댓글 조회
    @GetMapping("/getrecomment")
    public ResponseEntity<Object> getReComment(@RequestParam String commentsId) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            ReComments getReComment = recommentsService.getReComment(commentsId);
            return new ResponseEntity<>(getReComment, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
