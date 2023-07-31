package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Users;
import seohyun.app.mall.service.UsersService;
import seohyun.app.mall.utils.Bcrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UsersService usersService;
    private final Bcrypt bcrypt;


    // 회원가입
    // userId 중복검사
    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody Users users) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            UUID uuid = UUID.randomUUID();
            users.setId(uuid.toString());

            String hashPassword = bcrypt.HashPassword(users.getPassword());
            users.setPassword(hashPassword);

            Boolean userIdCheck = usersService.checkUserId(users);
            if (userIdCheck == true) {
                map.put("result", "failed 해당 아이디는 이미 존재합니다. 다른 아이디를 입력해주세요.");
            } else {
                usersService.signUp(users);
                map.put("result", "success 등록이 완료되었습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody Users users) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            Users userIdCheck = usersService.findUserId(users.getUserId());
            if (userIdCheck == null) {
                map.put("result", "failed 아이디가 존재하지 않습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

            Boolean comparePassword = bcrypt.CompareHash(users.getPassword(), userIdCheck.getPassword());
            if (comparePassword == true) {
                map.put("result", "success 로그인 성공");
            } else {
                map.put("result", "failed 비밀번호가 일치하지 않습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 회원정보 수정
    // 비밀번호를 확인 해 사용자 아이디와 일치하면 수정 가능.
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Users users) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            Users userCheck = usersService.checkUserIdAndPassword(users);
            if (userCheck != null) {
                users.setId(userCheck.getId());
                usersService.update(users);
                map.put("result", "success 수정이 완료되었습니다.");
            } else {
                map.put("result", "failed 비밀번호가 일치하지 않습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 회원탈퇴
    // 비밀번호를 확인 해 사용자 아이디와 일치하면 탈퇴 가능.
    @PostMapping("/unregister")
    public ResponseEntity<Object> unRegister(@RequestBody Users users) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            Users userCheck = usersService.checkUserIdAndPassword(users);
            if (userCheck != null) {
                users.setId(userCheck.getId());
                usersService.unRegister(users);
                map.put("result", "success 탈퇴가 완료되었습니다.");
            } else {
                map.put("result", "failed 비밀번호가 일치하지 않습니다.");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // TODO 비밀번호 수정
    @PostMapping("/updatepassword")
    public ResponseEntity<Object> updatePassword(@RequestBody Map<String, String> req) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            Users findUserId = usersService.findUserId(req.get("userId"));
            if (findUserId == null) {
                map.put("result", "failed 해당 아이디는 존재하지 않습니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

            Boolean comparePassword = bcrypt.CompareHash(req.get("password"), findUserId.getPassword());
                if (comparePassword == true) {
                    String hash = bcrypt.HashPassword(req.get("newPassword"));
                    findUserId.setPassword(hash);
                    usersService.update(findUserId);
                    map.put("result", "success 수정이 완료되었습니다.");
                } else {
                    map.put("result", "failed 비밀번호가 일치하지 않습니다.");
                }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
