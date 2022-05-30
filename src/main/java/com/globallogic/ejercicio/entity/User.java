package com.globallogic.ejercicio.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.sun.istack.NotNull;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id_user",columnDefinition = "uuid")
	private UUID idUser;

	@Size(min = 3, message = "Debe tener minimo 3 caracteres")
	@Column(name = "name", length = 150, nullable = true)
	private String name;

	@Size(min = 8, message = "Debe tener minimo 8 caracteres")
	@Size(max = 12, message = "Debe tener maximo 12 caracteres")
	@Pattern(regexp = "^(?=(?:\\D*\\d){2}$)(?=(?:[^a-z]*[a-z]){2})(?=[^A-Z]*[A-Z]).*", message = "El formato ingresado no es el correcto, 1 mayuscula, letras minisculas, 2 numeros.")
	@Column(name = "password", length = 100, nullable = false)
	private String password;
	
	@Column(name = "token")
	private String token;
	
	@Email(message = "Email no valido", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
	@Column(name = "email", unique = true, length = 100, nullable = false)
	private String email;

	@NotNull
	@Column(name = "created", nullable = false)
	private Date created;

	@Column(name = "modified", nullable = true)
	private Date modified;

	@Column(name = "last_login", nullable = true)
	private Date lastLogin;

	@Column(name = "isactive", nullable = true)
	private Boolean isactive;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user", referencedColumnName = "id_user")
	private Set<Phone> phones;
	
	public User() {}
	
	
	public User(@Size(min = 3, message = "Debe tener minimo 3 caracteres") String name,
			@Size(min = 8, message = "Debe tener minimo 8 caracteres") @Size(max = 12, message = "Debe tener maximo 12 caracteres") @Pattern(regexp = "^(?:[a-z]*[A-Z][a-z]+|[a-z]+[A-Z][a-z]*)(?=(.*[0-9]{2})).{4,}$", message = "El formato ingresado no es el correcto, 1 mayuscula, letras minisculas, 2 numeros.") String password,
			String token,
			@Pattern(regexp = "^([a-zA-Z0-9_.+-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$", message = "El formato ingresado no es el correcto.") String email,
			Date created, Date modified, Date lastLogin, Boolean isactive, Set<Phone> phones) {
		super();
		this.name = name;
		this.password = password;
		this.token = token;
		this.email = email;
		this.created = created;
		this.modified = modified;
		this.lastLogin = lastLogin;
		this.isactive = isactive;
		this.phones = phones;
	}



	public User(
			@Pattern(regexp = "^([a-zA-Z0-9_.+-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$", message = "El formato ingresado no es el correcto.") String email) {
		super();
		this.email = email;
	}

	public UUID getIdUser() {
		return idUser;
	}

	public void setIdUser(UUID idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}
}