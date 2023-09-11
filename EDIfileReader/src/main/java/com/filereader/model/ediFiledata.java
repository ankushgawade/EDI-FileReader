package com.filereader.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ediFiledata {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String senderQualifier;
    private String senderId;
    private String receiverQualifier;
    private String receiverId;
    private String functionalCode;
    private String transactionCode;
    
	public ediFiledata() {
		super();
	}

	public ediFiledata(Long id, String fileName, String fileType, String senderQualifier, String senderId,
			String receiverQualifier, String receiverId, String functionalCode, String transactionCode) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileType = fileType;
		this.senderQualifier = senderQualifier;
		this.senderId = senderId;
		this.receiverQualifier = receiverQualifier;
		this.receiverId = receiverId;
		this.functionalCode = functionalCode;
		this.transactionCode = transactionCode;
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

	public String getSenderQualifier() {
		return senderQualifier;
	}

	public String setSenderQualifier(String senderQualifier) {
		return this.senderQualifier = senderQualifier;
	}

	public String getSenderId() {
		return senderId;
	}

	public String setSenderId(String senderId) {
		 return this.senderId = senderId;
	}

	public String getReceiverQualifier() {
		return receiverQualifier;
	}

	public String setReceiverQualifier(String receiverQualifier) {
		return this.receiverQualifier = receiverQualifier;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public String setReceiverId(String receiverId) {
		return this.receiverId = receiverId;
	}

	public String getFunctionalCode() {
		return functionalCode;
	}

	public void setFunctionalCode(String functionalCode) {
		this.functionalCode = functionalCode;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	@Override
	public String toString() {
		return "ediFiledata [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", senderQualifier="
				+ senderQualifier + ", senderId=" + senderId + ", receiverQualifier=" + receiverQualifier
				+ ", receiverId=" + receiverId + ", functionalCode=" + functionalCode + ", transactionCode="
				+ transactionCode + "]";
	}
    
    
    
}
