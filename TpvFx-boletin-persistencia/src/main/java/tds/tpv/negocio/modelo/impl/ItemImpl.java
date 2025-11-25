package tds.tpv.negocio.modelo.impl;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import tds.tpv.negocio.modelo.Item;
import tds.tpv.negocio.modelo.Producto;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_item")
public class ItemImpl implements Item {

	@JsonProperty("id_item")
	private String idItem;

	@JsonProperty("producto")
	private ProductoImpl producto;

	@JsonProperty("cantidad")
	private int cantidad;

	public ItemImpl() {
		this(null, 0);
	}

	public ItemImpl(Producto producto, int cantidad) {
		this.idItem = "";
		this.producto = (ProductoImpl) producto;
		this.cantidad = cantidad;
	}

	public String getIdItem() {
		return idItem;
	}

	public void setIdItem(String id) {
		this.idItem = id;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public ProductoImpl getProducto() {
		return producto;
	}

	@Override
	public int getCantidad() {
		return cantidad;
	}

	@JsonIgnore
	@Override
	public String getDescripcion() {
		return producto.getDescripcion();
	}

	@JsonIgnore
	@Override
	public double getPrecio() {
		return producto.getPrecio();
	}

	public void incrementarCantidad(int cantidad) {
		this.cantidad += cantidad;
	}

	@JsonIgnore
	@Override
	public double getPrecioTotal() {
		return this.producto.getPrecio() * this.cantidad;
	}

}
