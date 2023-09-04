package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Notices;
import seohyun.app.mall.models.Users;
import seohyun.app.mall.service.NoticesService;
import seohyun.app.mall.service.UsersService;
import seohyun.app.mall.utils.Jwt;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/notices")
public class NoticesController {
    private final NoticesService noticesService;
    private final UsersService usersService;
    private final Jwt jwt;

    // 공지사항 등록
    // 관리자만 등록 가능. (role = 3)
    @PostMapping("/createnotice")
    public ResponseEntity<Object> createNotice(
            @RequestHeader String authorization, @RequestBody Notices notices) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Users findUserId = usersService.findUserId(decoded);
            if (findUserId.getRole() != 3) {
                map.put("result", "failed 등록 권한이 없습니다.");
            } else {
                noticesService.createNotice(notices);
                map.put("result", "success 등록이 완료되었습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 공지사항 전체 조회 (default: page = 0, limit = 10)
    // 권한 제약 없음.
    @GetMapping("/getallnotices")
    public ResponseEntity<Object> getAllNotices(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") Integer pageSize
    ) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            Page<Notices> noticesPage = noticesService.getAllNotices(pageNumber, pageSize);
            return new ResponseEntity<>(noticesPage, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 공지사항 조회(id)
    @GetMapping("/getnotice")
    public ResponseEntity<Object> getNotice(@RequestParam Long id) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            Notices notice = noticesService.getNotice(id);
            return new ResponseEntity<>(notice, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
