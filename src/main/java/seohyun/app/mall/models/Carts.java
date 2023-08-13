package seohyun.app.mall.models;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "carts")
@EntityListeners(AuditingEntityListener.class)
public class Carts extends DateEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "product_id")
    private String productId;
    private Integer count;
}
