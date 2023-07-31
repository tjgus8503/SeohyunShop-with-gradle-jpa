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
public class Products {
    @Id
    private String id;
    @Column(name = "product_name")
    private String productName;
    private String title;
    private Integer price;
    private String description;
    @Column(name = "delivery_fee")
    private String deliveryFee;
    private String status;
    @Column(name = "image_url")
    private String imageUrl;
}
