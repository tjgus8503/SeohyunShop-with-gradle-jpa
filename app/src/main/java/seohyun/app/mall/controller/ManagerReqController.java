package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.ManagerRequests;
import seohyun.app.mall.models.Users;
import seohyun.app.mall.service.ManagerReqService;
import seohyun.app.mall.service.UsersService;
import seohyun.app.mall.utils.Jwt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/managerreq")
public class ManagerReqController {
    private final ManagerReqService managerReqService;
    private final UsersService usersService;
    private final Jwt jwt;

    // 상품 관리자 권한 신청
    // 일반 유저만 신청 가능 (role = 1)
    @PostMapping("/createmanagerreq")
    public ResponseEntity<Object> createManagerReq(
            @RequestHeader String authorization) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);
            ManagerRequests managerRequests = new ManagerRequests();
            Users findUserId = usersService.findUserId(decoded);
            if (findUserId.getRole() != 1) {
                map.put("result", "failed 등록 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            UUID uuid = UUID.randomUUID();
            managerRequests.setId(uuid.toString());
            managerRequests.setUserId(decoded);
            managerReqService.createManagerReq(managerRequests);
            map.put("result", "success 등록이 완료되었습니다.");

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품관리자로 승인
    // 관리자만 수락 가능 (role = 3)
    // 수락된 유저는 신청 목록에서 삭제.
    @PostMapping("/acceptmanagerreq")
    public ResponseEntity<Object> acceptManagerReq(
            @RequestHeader String authorization,  @RequestBody ManagerRequests managerRequests) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Users findUserId = usersService.findUserId(decoded);
            if (findUserId.getRole() != 3) {
                map.put("result", "failed 수정 권한이 없습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            Integer result = usersService.updateRole(managerRequests.getUserId());
            if (result == 0) {
                map.put("result", "failed 승인에 실패하였습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            managerReqService.acceptManagerReq(managerRequests);
            map.put("result", "success 승인이 완료되었습니다.");

            // 승인이 완료되면 승인 요청 테이블에서 삭제.
            managerReqService.deleteManagerReq(managerRequests.getId(), managerRequests.getUserId());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 상품관리자 신청자 리스트 조회
    // 관리자만 조회 가능 (role = 3)
    @GetMapping("/getallmanagerreq")
    public ResponseEntity<Object> getAllManagerReq(
            @RequestHeader String authorization,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") Integer pageSize) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(authorization);

            Users findUserId = usersService.findUserId(decoded);
            if (findUserId.getRole() != 3) {
                map.put("result", "failed 조회 권한이 없습니다.");
            }
            Page<ManagerRequests> requestsList = managerReqService.getAllManagerReq(pageNumber, pageSize);
            return new ResponseEntity<>(requestsList, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

}
