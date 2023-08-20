package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "recomments")
public class ReComments extends DateEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "comments_id")
    private String commentsId;
    @Column(name = "product_id")
    private String productId;
    private String content;
}
