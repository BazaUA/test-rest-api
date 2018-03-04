package com.bazalytskyi.test.data.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Products")
public class Product {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	private double price;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_fk")
	private Category category;

	public Product(String name, String description, double price, Category category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	public Product() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (!this.name.equals(other.name))
			return false;
		if (!this.description.equals(other.description))
			return false;
		if (this.price != other.price)
			return false;
		if (!this.category.equals(other.category))
			return false;
		return true;
	}

}
