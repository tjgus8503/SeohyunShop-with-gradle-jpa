package seohyun.app.mall.models;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
// TODO 상품의 재고는 사용자의 구매갯수에 따라 변한다.
public class Products {
    @Id
    private String id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "cate_id")
    private Integer cateId;
    @Column(name = "user_id")
    private String userId;
    private Integer price;
    private String description;
    private BigDecimal discount;
    @Column(name = "delivery_fee")
    private Integer deliveryFee;
    @Column(name = "shipping_company")
    private String shippingCompany;
    private String status;
    private Integer stock;
    @Column(name = "image_url")
    private String imageUrl;
}
