package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    List<Product> listProducts;
    public Cart(){
        listProducts = new ArrayList<>();
    }
    
    public void addProduct(Product P )
    {
        listProducts.add(P);
    }
    public float getTotalCost()
    {
        float Total =0;

        for(int k=0; k<listProducts.size(); k++)
        {
            Total += listProducts.get(k).getPrezzo();
        }

        return Total;
    }


    public List<Product> getListProducts() {
        return listProducts;
    }
    
    
    public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}

	public void clear()
    {
        listProducts.clear();
    }
    public boolean remove(Product P)
    {
        try {
            listProducts.remove(P);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public int size(){return listProducts.size();}
}
