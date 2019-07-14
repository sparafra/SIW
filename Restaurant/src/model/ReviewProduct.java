package model;

import java.util.Date;

public class ReviewProduct extends Review {

	Long idProduct;
	
	public ReviewProduct(Long idProduct, String NumeroTelefono, int Voto, Date DataOra)
	{
		super(NumeroTelefono, Voto, DataOra);
		this.idProduct = idProduct;
	}
	public ReviewProduct()
	{
		super();
	}
	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	
}
