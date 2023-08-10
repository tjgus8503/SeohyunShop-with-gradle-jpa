package seohyun.app.mall.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "comments")
public class Comments {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "product_inquiries_id")
    private String productInquiriesId;
    private String content;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
