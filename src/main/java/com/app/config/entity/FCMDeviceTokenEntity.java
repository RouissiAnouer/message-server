package com.app.config.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "fcm_device_token_table")
@NoArgsConstructor
@SuperBuilder
public class FCMDeviceTokenEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5871143221246142930L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private Long id;
	
	@Column(name = "deviceToken")
	@Getter @Setter private String deviceToken;
	
	@OneToOne(mappedBy = "deviceToken")
	@Getter @Setter private UserEntity user;

}
