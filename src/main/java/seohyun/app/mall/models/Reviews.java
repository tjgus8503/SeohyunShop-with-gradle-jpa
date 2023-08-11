package seohyun.app.mall.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "reviews")
@EntityListeners(AuditingEntityListener.class)
public class Reviews extends DateEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    // 주문한 상품
    @Column(name = "product_id")
    private String productId;
    private String content;
    @Column(name = "image_url")
    private String imageUrl;
}
