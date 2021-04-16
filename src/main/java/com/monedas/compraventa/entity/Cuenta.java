package com.monedas.compraventa.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name="cbu")
	private int cbu;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CUENTA_TIPO_CAJA", joinColumns = {
			@JoinColumn(name = "CUENTA_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "CAJA_ID") })
	private List<Caja> caja;
	
	@NotNull
	@Column(name="idUsuario")
	private Long idUsuario;
}
