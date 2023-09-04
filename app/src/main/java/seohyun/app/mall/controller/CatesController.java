package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Categories;
import seohyun.app.mall.models.Users;
import seohyun.app.mall.service.CatesService;
import seohyun.app.mall.service.UsersService;
import seohyun.app.mall.utils.Jwt;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/categories")
public class CatesController {
    private final CatesService catesService;
    private final UsersService usersService;
    private final Jwt jwt;

    // 카테고리 생성
    @PostMapping("/createcate")
    public ResponseEntity<Object> createCate(
            @RequestHeader String authorization, @RequestBody Categories categories) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);
            // 유저가 role = 3(관리자)일 때, 카테고리 생성 가능
            Users findUserId = usersService.findUserId(decoded);
            if (findUserId.getRole() != 3) {
                map.put("result", "failed 등록 권한이 없습니다.");
            } else {
                catesService.createCate(categories);
                map.put("result", "success 등록이 완료되었습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }


}
