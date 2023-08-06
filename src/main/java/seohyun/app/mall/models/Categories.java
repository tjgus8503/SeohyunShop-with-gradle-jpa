package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "categories")
public class Categories {
    @Id
    private String id;
    @Column(name = "cate_name")
    private String cateName;
    @Column(name = "parent_id")
    private String parentId;

}
