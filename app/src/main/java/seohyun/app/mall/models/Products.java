package seohyun.app.mall.models;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "products")
public class Products extends DateEntity {
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
