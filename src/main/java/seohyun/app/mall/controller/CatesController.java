package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Categories;
import seohyun.app.mall.models.ParentCategories;
import seohyun.app.mall.service.CatesService;
import seohyun.app.mall.utils.Jwt;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/cates")
public class CatesController {
    private final CatesService catesService;
    private final Jwt jwt;

    @PostMapping("/createcate")
    public ResponseEntity<Object> createCate(
            @RequestHeader String xauth, @RequestBody Categories categories) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            catesService.createCate(categories);
            map.put("result", "success 등록이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PostMapping("/createparentcate")
    public ResponseEntity<Object> createParentCate(
            @RequestHeader String xauth, @RequestBody ParentCategories parentCategories) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);

            catesService.createParentCate(parentCategories);
            map.put("result", "success 등록이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

}
