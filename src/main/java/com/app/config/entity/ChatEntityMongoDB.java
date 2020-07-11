package com.app.config.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public class ChatEntityMongoDB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2077015601356325827L;

	@Id
	private String id;
	
	private Long idReceiver;
	
	public Long idSender;
	
	public String message;
	
	public String fileMessage;
	
	@Setter public Long timestamp;
	
	@Setter public Integer status;
	
	@Getter @Setter private String fileType;
	
	@Getter @Setter private String type;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getIdSender() {
		return idSender;
	}

	public void setIdSender(Long idSender) {
		this.idSender = idSender;
	}
	
	public Long getIdReceiver() {
		return idReceiver;
	}

	public void setIdReceiver(Long idReceiver) {
		this.idReceiver = idReceiver;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getFileMessage() {
		return fileMessage;
	}

	public void setFileMessage(String fileMessage) {
		this.fileMessage = fileMessage;
	}
	
//	@JsonProperty("fileMessageConvert")
//	public String getFileMessageBase64() throws SQLException {
//		if (type != null) {
//			int blobLength = (int) fileMessage.length();	
//			String imageToReturn = new String(Base64.getDecoder().decode(fileMessage.getBytes(1, blobLength)));
//			return imageToReturn;
//		} else {
//			return null;
//		}
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
