package com.filereader.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class xmlData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cNumber;

	
	
	
	public xmlData(Long id, String cNumber) {
		super();
		this.id = id;
		this.cNumber = cNumber;
	}

	public xmlData() {
	}

	public xmlData(String cNumber) {
		this.cNumber = cNumber;
	}

	public Long getId() {
		return id;
	}

	public String getcNumber() {
		return cNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setcNumber(String cNumber) {
		this.cNumber = cNumber;
	}

	@Override
	public String toString() {
		return "xmlData [id=" + id + ", cNumber=" + cNumber + "]";
	}
	
	
	
}