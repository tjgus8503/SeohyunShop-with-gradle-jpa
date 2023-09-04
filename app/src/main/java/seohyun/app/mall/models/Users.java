package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    private String password;
    private String username;
    private String email;
    private String phone;
    @Column(name = "post_code")
    private String postCode;
    private String address;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "join_date")
    private LocalDateTime joinDate;
    // 3 = 관리자, 2 = 상품 판매자, 1 = 일반 유저(상품 구매만 가능)
    private Integer role;

}
