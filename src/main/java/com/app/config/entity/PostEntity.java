package com.app.config.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "post_table")
@NoArgsConstructor
@SuperBuilder
public class PostEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6191708069642770427L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "post")
	private String post;
	
	@Column(name = "owner")
	private Long idOwner;
	
	@Column(name = "timestamp")
	private String timestamp;
	
	@Column(name = "positive")
	private int positive;
	
	@Column(name = "negative")
	private int negative;
	
	
	@Column(name = "comments")
	@OneToMany(targetEntity = CommentEntity.class, mappedBy = "postId", fetch = FetchType.EAGER)
	private Set<CommentEntity> comments;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPost() {
		return post;
	}


	public void setPost(String post) {
		this.post = post;
	}


	public Long getIdOwner() {
		return idOwner;
	}


	public void setIdOwner(Long idOwner) {
		this.idOwner = idOwner;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Set<CommentEntity> getComments() {
		return comments;
	}


	public void setComments(Set<CommentEntity> comments) {
		this.comments = comments;
	}


	public int getPositive() {
		return positive;
	}


	public void setPositive(int positive) {
		this.positive = positive;
	}


	public int getNegative() {
		return negative;
	}


	public void setNegative(int negative) {
		this.negative = negative;
	}
	
	

}
