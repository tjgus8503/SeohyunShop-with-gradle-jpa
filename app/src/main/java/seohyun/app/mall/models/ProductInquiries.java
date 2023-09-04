package seohyun.app.mall.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "product_inquiries")
@EntityListeners(AuditingEntityListener.class)
public class ProductInquiries extends DateEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "inquiry_type")
    private String inquiryType;
    private String content;

}
