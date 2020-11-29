package com.app.config.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.app.model.request.SignUpRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_table")
@SuperBuilder
@NoArgsConstructor
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 585437878616229547L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Column(name = "first_name")
	@Setter
	private String firstName;

	@Column(name = "last_name")
	@Setter
	private String lastName;

	@Column(name = "email")
	@Setter
	private String email;

	@Column(name = "userName")
	@Setter
	private String userName;

	@Column(name = "password")
	@Setter
	private String password;

	@Column(name = "connected")
	@Setter
	private Integer connected;

	@Column(name = "chats")
	@OneToMany(targetEntity = ChatEntity.class, mappedBy = "idSender", fetch = FetchType.EAGER)
	private Set<ChatEntity> chats;

	@Column(name = "received")
	@OneToMany(targetEntity = ChatEntity.class, mappedBy = "idReceiver", fetch = FetchType.EAGER)
	@Setter
	@Getter
	private Set<ChatEntity> received;

	@ManyToMany(targetEntity = RoleEntity.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Getter
	@Setter
	private Set<RoleEntity> roles;

	@Lob
	@Column(name = "avatar")
	private Blob image;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_profile_table_id", referencedColumnName = "id")
	@Getter
	@Setter
	private CoverProfileEntity cover;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fcm_device_token_id", referencedColumnName = "id")
	@Getter
	@Setter
	private FCMDeviceTokenEntity deviceToken;

	@JoinColumn(name = "idSender")
	public Set<ChatEntity> getChats() {
		return chats;
	}

	public void setChats(Set<ChatEntity> chats) {
		this.chats = chats;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public Integer getConnected() {
		return connected;
	}

	public static UserEntity of(SignUpRequest request) {
		UserEntity entity = UserEntity.builder().email(request.getEmail()).userName(request.getEmail())
				.firstName(request.getFirstname()).lastName(request.getLastname()).password(request.getPassword())
				.build();
		return entity;
	}

	@JsonIgnore
	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	@JsonProperty("photoData")
	public String getPhotoBase64() throws SQLException {
		if (image != null) {
			int blobLength = (int) image.length();
			return "data:image/jpeg;base64," + new String(Base64.getEncoder().encode(image.getBytes(1, blobLength)));
		} else {
			return null;
		}
	}

}
