package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "manager_requests")
public class ManagerRequests extends DateEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
}
