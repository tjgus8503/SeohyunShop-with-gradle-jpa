package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seohyun.app.mall.models.Users;
import seohyun.app.mall.service.UsersService;
import seohyun.app.mall.utils.Bcrypt;
import seohyun.app.mall.utils.Jwt;

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
    private final Jwt jwt;


    // 회원가입
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
                String xauth = jwt.CreateToken(users.getUserId());
                map.put("xauth", xauth);
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
    // TODO 토큰이 만료되었거나 없는 아이디 입니다. 에러 핸들링 추가? 고민.
    @PostMapping("/updateuser")
    public ResponseEntity<Object> updateUser(
            @RequestHeader String xauth, @RequestBody Users users) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded  = jwt.VerifyToken(xauth);
            Users findUserId = usersService.findUserId(decoded);
            users.setId(findUserId.getId());
            users.setUserId(findUserId.getUserId());
            users.setPassword(findUserId.getPassword());
            usersService.updateUser(users);
            map.put("result", "success 수정이 완료되었습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    // 회원 탈퇴
    // 비밀번호 확인
    @PostMapping("/unregister")
    public ResponseEntity<Object> unRegister(
            @RequestHeader String xauth, @RequestBody Users users) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);
            Users findUserId = usersService.findUserId(decoded);
            Boolean comparePassword = bcrypt.CompareHash(users.getPassword(), findUserId.getPassword());
            if (comparePassword == true) {
                usersService.unRegister(findUserId);
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

    // 비밀번호 수정
    @PostMapping("/updatepassword")
    public ResponseEntity<Object> updatePassword(
            @RequestHeader String xauth, @RequestBody Map<String, String> req) throws Exception {
        try{
            Map<String, String> map = new HashMap<>();

            String decoded = jwt.VerifyToken(xauth);
            Users findUserId = usersService.findUserId(decoded);
            if (findUserId == null) {
                map.put("result", "failed 토큰이 만료되었거나 없는 아이디 입니다.");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

            Boolean comparePassword = bcrypt.CompareHash(req.get("password"), findUserId.getPassword());
                if (comparePassword == true) {
                    String hash = bcrypt.HashPassword(req.get("newPassword"));
                    findUserId.setPassword(hash);
                    usersService.updateUser(findUserId);
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

    // 마이페이지 조회
    // TODO 마이페이지에 들어갈 정보 생각하기.
    @GetMapping("/getuser")
    public ResponseEntity<Object> getUser() throws Exception {
        try{
            Map<String, String> map = new HashMap<>();


            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("error", e.toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
