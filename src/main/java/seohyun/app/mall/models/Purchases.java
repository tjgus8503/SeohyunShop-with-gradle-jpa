package seohyun.app.mall.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchases")
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 상품 페이지에서 바로 주문 (해당 상품만 구매 가능)
public class Purchases {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "product_id")
    private String productId;
    private Long count;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


}
