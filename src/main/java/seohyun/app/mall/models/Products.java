package seohyun.app.mall.models;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "products")
// TODO 상품의 재고는 사용자의 구매갯수에 따라 변한다.
public class Products {
    @Id
    private String id;
    @Column(name = "product_name")
    private String productName;
    private String category;
    @Column(name = "shop_name")
    private String shopName;
    private Integer price;
    private String description;
    @Column(name = "delivery_fee")
    private String deliveryFee;
    private String status;
    private Integer count;
    @Column(name = "image_url")
    private String imageUrl;
}
