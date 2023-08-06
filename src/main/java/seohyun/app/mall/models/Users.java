package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
// role: 일반 사용자 = "1", 판매자 = "2"
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
    private LocalDate joinDate;
    private String role;

}
