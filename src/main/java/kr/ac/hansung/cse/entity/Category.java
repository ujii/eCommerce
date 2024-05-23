package kr.ac.hansung.cse.entity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * Category와 childCategories 관계는 1:n으로 매핑
 * Product와 Category는 N:N으로 매핑
*/

@Getter
@Setter
@ToString

@Entity
@Table(name="app_category")
public class Category extends AbstractEntity {

	@Column(name = "name", nullable = false)
	private String name;	
	
    @ManyToMany( fetch = FetchType.EAGER, mappedBy = "categories")
	private Set<Product> products;
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private Set<Category> childCategories;

    @ManyToOne
    @JoinColumn(name = "parentid")
	@JsonIgnore
    private Category parent;
}