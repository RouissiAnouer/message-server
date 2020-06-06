package com.app.config.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cover_profile_table")
@SuperBuilder
@NoArgsConstructor
public class CoverProfileEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 585437878616229547L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Lob
	@Column(name = "cover")
	private Blob cover;
	
	@Getter @Setter
	@OneToOne(mappedBy = "cover")
	private UserEntity user;
	
	
	@JsonIgnore
	public Blob getCover() {
		return cover;
	}

	public void setCover(Blob cover) {
		this.cover = cover;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("coverString")
	public String getPhotoBase64() throws SQLException {
		if (cover != null) {
			int blobLength = (int) cover.length();
			return "data:image/jpeg;base64," + new String(Base64.getEncoder().encode(cover.getBytes(1, blobLength)));
		} else {
			return null;
		}
	}

}
