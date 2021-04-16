package com.monedas.compraventa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name="dni")
	private String dni;
	
	@NotNull
	@Column(name="nombre")
	private String nombre;
	
	@NotNull
	@Column(name="apellido")
	private String apellido;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USUARIO_CUENTA", joinColumns = {
			@JoinColumn(name = "USUARIO_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "CUENTA_ID") })
	private List<Cuenta> cuentas;
}
