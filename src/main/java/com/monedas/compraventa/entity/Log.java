package com.monedas.compraventa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="log")
public class Log {
	

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	private Long id;

	@Column(name="USUARIO", nullable=false)
	private Long usuario;

	@Column(name="IP", nullable=false)
	private String ip;

	@Column(name="FECHA", nullable=false)
	private Date fecha;

	@Column(name="ACCION", nullable=false)
	private String accion;

	@Column(name="SUBSISTEMA", nullable=false)
	private String subsistema;

	@Column(name="DESCRIPCION", nullable=false)
	private String descripcion;

	@Column(name="HOSTNAME", nullable=true)
	private String hostname;

	public Log(){}

	public Log(Long usuario, String ip, Date fecha, String accion, String subsistema, String descripcion,
			String hostname) {
		super();
		this.usuario = usuario;
		this.ip = ip;
		this.fecha = fecha;
		this.accion = accion;
		this.subsistema = subsistema;
		this.descripcion = descripcion;
		this.hostname = hostname;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUsuario() {
		return usuario;
	}
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getSubsistema() {
		return subsistema;
	}
	public void setSubsistema(String subsistema) {
		this.subsistema = subsistema;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
}