package seohyun.app.mall.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "product_id")
    private String productId;
    private String content;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "image_url")
    private String imageUrl;
}
