package com.app.config.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "chat_table")
@NoArgsConstructor
@SuperBuilder
public class ChatEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2077015601356325827L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "idReceiver")
	private Long idReceiver;
	
	@Column(name = "idSender")
	public Long idSender;
	
	@Column(name = "message")
	public String message;
	
	@Column(name = "timestamp")
	@Setter public String timestamp;
	
	@Column(name = "status")
	@Setter public Integer status;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@ManyToOne
	public Long getIdSender() {
		return idSender;
	}

	public void setIdSender(Long idSender) {
		this.idSender = idSender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	@ManyToOne
	public Long getIdReceiver() {
		return idReceiver;
	}

	public void setIdReceiver(Long idReceiver) {
		this.idReceiver = idReceiver;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public Integer getStatus() {
		return this.status;
	}

}
