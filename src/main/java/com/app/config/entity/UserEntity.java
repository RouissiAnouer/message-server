package com.app.config.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
public class UserEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 585437878616229547L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;
     
    @Column(name="first_name")
    @Setter private String firstName;
    
    @Column(name="last_name")
    @Setter private String lastName;
    
    @Column(name="email")
    @Setter private String email;

    @Column(name="userName")
    @Setter private String userName;

	@Column(name="password")
    @Setter private String password;  
	
	@Column(name = "chats")
	@OneToMany(targetEntity=ChatEntity.class, mappedBy="idSender", fetch=FetchType.EAGER)
	private Set<ChatEntity> chats;
	
	@Column(name = "received")
	@OneToMany(targetEntity=ChatEntity.class, mappedBy="idReceiver", fetch=FetchType.EAGER)
	@Setter @Getter private Set<ChatEntity> received;
  
//	
	
	@JoinColumn(name="idSender")
    public Set<ChatEntity> getChats() {
		return chats;
	}

	public void setChats(Set<ChatEntity> chats) {
		this.chats = chats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

}
