package com.zakado.zkd.clientfilmmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Genero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String titulo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Genero(Integer id, String titulo) {
		super();
		this.id = id;
		this.titulo = titulo;
	}

	public Genero() {
		super();
	}

	public Genero(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Genero(Integer id) {
		super();
		this.id = id;
	}

}
