package tds.tpv.negocio.modelo.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import tds.tpv.negocio.modelo.CarritoCompra;
import tds.tpv.negocio.modelo.Producto;
import tds.tpv.negocio.modelo.TPV;

public class TPVImpl implements TPV {
	
	@JsonProperty("productos")
	private List<ProductoImpl> productos;
	@JsonProperty("compras")
	public List<CarritoCompraImpl> compras; 
	
	public TPVImpl() {
		this (new ArrayList<>(), new ArrayList<>());
	}
	
	public TPVImpl(List<? extends Producto> productos, List<? extends CarritoCompra> compras) {
		this.productos = (List<ProductoImpl>) productos;
		this.compras = (List<CarritoCompraImpl>) compras;
	}

	@Override
	public List<? extends ProductoImpl> getProductos() {
		return productos;
	}
	
	public void setProductos(List<ProductoImpl> productos) {
		this.productos = productos;
	}

	@Override
	public List<? extends CarritoCompraImpl> getCompras() {
		return compras;
	}

	public void setCompras(List<CarritoCompraImpl> compras) {
		this.compras = compras;
	}

}
