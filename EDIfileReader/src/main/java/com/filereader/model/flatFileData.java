package com.filereader.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
/* @Table(name="CONTACT") */
public class flatFileData {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String partnerCode;
    private String status;
    private String loadNumber;
    private String shipmentId;
    
	public flatFileData() {
		super();
	}

	public flatFileData(Long id, String fileName, String fileType, String partnerCode, String status, String loadNumber,
			String shipmentId) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileType = fileType;
		this.partnerCode = partnerCode;
		this.status = status;
		this.loadNumber = loadNumber;
		this.shipmentId = shipmentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public String setPartnerCode(String partnerCode) {
		return this.partnerCode = partnerCode;
	}

	public String getStatus() {
		return status;
	}

	public String setStatus(String status) {
		return this.status = status;
	}

	public String getLoadNumber() {
		return loadNumber;
	}

	public String setLoadNumber(String loadNumber) {
		return this.loadNumber = loadNumber;
	}

	public String getShipmentId() {
		return shipmentId;
	}

	public String setShipmentId(String shipmentId) {
		return this.shipmentId = shipmentId;
	}

	@Override
	public String toString() {
		return "flatFileData [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", partnerCode="
				+ partnerCode + ", status=" + status + ", loadNumber=" + loadNumber + ", shipmentId=" + shipmentId
				+ "]";
	}

	
    
    
    
	
    

}
