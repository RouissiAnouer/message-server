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
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "comment_table")
public class CommentEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7254340041853541285L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "comment")
	private String comment;
	@Column(name = "idOwner")
	private Long idOwner;
	@Column(name = "timestamp")
	private String timestamp;
	@Column(name = "post_id")
	private Long postId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Long getIdOwner() {
		return idOwner;
	}
	public void setIdOwner(Long idOwner) {
		this.idOwner = idOwner;
	}

}
