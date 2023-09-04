package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "comments")
public class Comments extends DateEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "product_inquiries_id")
    private String productInquiriesId;
    @Column(name = "product_id")
    private String productId;
    private String content;
}
