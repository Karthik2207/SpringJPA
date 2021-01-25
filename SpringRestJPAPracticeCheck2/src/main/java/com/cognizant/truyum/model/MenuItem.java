package com.cognizant.truyum.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="menu_item")
@Getter @Setter
public class MenuItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "me_id")
	private int id;
	@Column(name = "me_name")
	private String name;
	@Column(name = "me_price")
	private BigDecimal price;
	@Column(name = "me_active")
	private String active;
	@Column(name="me_date_of_launch")
	@Temporal(TemporalType.DATE)
	private Date dateOfLaunch;
	@Column(name = "me_category")
	private String category;
	@Column(name = "me_free_delivery")
	private String freeDelivery;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "menuItemSet" )
	private Set<User> userSet;
	
	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", name=" + name + ", price=" + price + ", active=" + active + ", dateOfLaunch="
				+ dateOfLaunch + ", category=" + category + ", freeDelivery=" + freeDelivery + "]";
	}
	
	
	
}
