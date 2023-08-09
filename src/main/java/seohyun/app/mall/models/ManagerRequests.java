package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "manager_requests")
public class ManagerRequests {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "request_role")
    private Integer requestRole;
    @Column(name = "created_at")
    private Date createdAt;
    private Boolean approved;
}
