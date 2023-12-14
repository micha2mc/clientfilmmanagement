package com.zakado.zkd.clientfilmmanagement.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.List;

@Entity
public class Pelicula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


	private String titulo;


	private String sinopsis;


	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaEstreno;

	private String youtubeTrailerId;

	private String rutaPortada;


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "genero_pelicula", joinColumns = @JoinColumn(name = "id_pelicula"), inverseJoinColumns = @JoinColumn(name = "id_genero"))
	private List<Genero> generos;

	@Transient
	private MultipartFile portada;

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

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public LocalDate getFechaEstreno() {
		return fechaEstreno;
	}

	public void setFechaEstreno(LocalDate fechaEstreno) {
		this.fechaEstreno = fechaEstreno;
	}

	public String getYoutubeTrailerId() {
		return youtubeTrailerId;
	}

	public void setYoutubeTrailerId(String youtubeTrailerId) {
		this.youtubeTrailerId = youtubeTrailerId;
	}

	public String getRutaPortada() {
		return rutaPortada;
	}

	public void setRutaPortada(String rutaPortada) {
		this.rutaPortada = rutaPortada;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public MultipartFile getPortada() {
		return portada;
	}

	public void setPortada(MultipartFile portada) {
		this.portada = portada;
	}

	public Pelicula() {
		super();
	}

	public Pelicula(Integer id, String titulo,  String sinopsis, LocalDate fechaEstreno,
			String youtubeTrailerId, String rutaPortada,List<Genero> generos,
			MultipartFile portada) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.fechaEstreno = fechaEstreno;
		this.youtubeTrailerId = youtubeTrailerId;
		this.rutaPortada = rutaPortada;
		this.generos = generos;
		this.portada = portada;
	}

	public Pelicula(String titulo, String sinopsis, LocalDate fechaEstreno,
			String youtubeTrailerId, String rutaPortada, List<Genero> generos,
			MultipartFile portada) {
		super();
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.fechaEstreno = fechaEstreno;
		this.youtubeTrailerId = youtubeTrailerId;
		this.rutaPortada = rutaPortada;
		this.generos = generos;
		this.portada = portada;
	}

}
