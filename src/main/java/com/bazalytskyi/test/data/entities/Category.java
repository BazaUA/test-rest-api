package com.bazalytskyi.test.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Categories")
public class Category {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Category() {
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (!this.name.equals(other.name))
			return false;
		if (!this.description.equals(other.description))
			return false;
		
		return true;
	}

}
