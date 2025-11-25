package tds.tpv.negocio.modelo.impl;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import tds.tpv.negocio.modelo.Producto;


@JsonIdentityInfo(
	    generator = ObjectIdGenerators.PropertyGenerator.class,
	    property = "codigo"
	)
public class ProductoImpl implements Producto {
	
	@JsonProperty("codigo")
	private String codigo;
	@JsonProperty("descripcion")
	private String descripcion;
	@JsonProperty("cantidad")
	private int cantidad;
	@JsonProperty("precio")
	private double precio;

	public ProductoImpl() {
		this(null, null, 0, 0);	
	}
	
	public ProductoImpl(String codigo, String descripcion, int cantidad, double precio) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	@Override
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidad, codigo, descripcion, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductoImpl other = (ProductoImpl) obj;
		return codigo.equals(other.getCodigo());
	}

}
