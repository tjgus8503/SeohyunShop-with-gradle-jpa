package seohyun.app.mall.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "parentcates")
public class ParentCategories {
    @Id
    private String id;
    @Column(name = "cate_name")
    private String cateName;
}
