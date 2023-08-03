package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "carts")
// 사용자가 상품, 수량 선택 후 장바구니에 담는다.
// 사용자가 장바구니에 상품 담을 때 가격 선택?x 배송비 선택?x
// 관계 설정은 나중에.
public class Carts {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "product_id")
    private String productId;
    private Integer count;
}
