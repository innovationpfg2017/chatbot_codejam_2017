package com.principal.ind.ChatBot;

public class EmailHelper {
	
	 

	public String toEmailId;
	public String attachmentFileLocation;
	public int contractNo;
	public int getContractNo() {
		return contractNo;
	}

	public void setContractNo(int contractNo) {
		this.contractNo = contractNo;
	}
	public boolean attachmentPresent;
	
	public EmailHelper(String toEmailId, boolean attachmentPresent, String attachmentFileLocation){
		this.toEmailId = toEmailId;
		this.attachmentPresent = attachmentPresent;
		this.attachmentFileLocation = attachmentFileLocation;
	}
	
	public String getToEmailId() {
		return toEmailId;
	}
	public void setToEmailId(String toEmailId) {
		this.toEmailId = toEmailId;
	}
	public String getAttachmentFileLocation() {
		return attachmentFileLocation;
	}
	public void setAttachmentFileLocation(String attachmentFileLocation) {
		this.attachmentFileLocation = attachmentFileLocation;
	}
	public boolean isAttachmentPresent() {
		return attachmentPresent;
	}
	public void setAttachmentPresent(boolean attachmentPresent) {
		this.attachmentPresent = attachmentPresent;
	}
}
