package com.i4jsfdemo.model.bean;

// default package
// Generated 10/06/2013 22:40:38 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Type generated by hbm2java
 */
@Entity
@Table(name = "type")
public class Type implements java.io.Serializable {

	private static final long serialVersionUID = 2644022136811709451L;
	
	private Long id;
	private String description;
	private Set<User> users = new HashSet<User>();

	public Type() {
	}

	public Type(Long id) {
		this.id = id;
	}

	public Type(Long id, String description, Set<User> users) {
		this.id = id;
		this.description = description;
		this.users = users;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DESCRIPTION", length = 45,unique=true,nullable=false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "type",  targetEntity=User.class)
	@Cascade({CascadeType.SAVE_UPDATE})
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Type other = (Type) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}