package com.cognizant.truyum.dao;

import java.math.BigDecimal;
import java.util.Set;

import com.cognizant.truyum.model.MenuItem;

public class CartDTO {
		
	private Set<MenuItem> menuItemSet;
	private BigDecimal total;
	public Set<MenuItem> getMenuItemSet() {
		return menuItemSet;
	}
	public void setMenuItemSet(Set<MenuItem> menuItemSet) {
		this.menuItemSet = menuItemSet;
	}
	public BigDecimal getTotal() {
		total=new BigDecimal(0);
		for(MenuItem m:menuItemSet)
			total= total.add(m.getPrice());
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public CartDTO(Set<MenuItem> menuItemSet) {
		super();
		this.menuItemSet = menuItemSet;
		total=this.getTotal();
	}
	public CartDTO() {
		super();
	}	
	
	
}
