package domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "phone.unsold", query = "Select p from Phone p where p.sold = false"),
	@NamedQuery(name = "phone.all", query = "Select p from Phone p"),
    @NamedQuery(name = "findByModel", query = "from Phone p where p.model=:model"),
})
public class Phone 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	public long getId() 
	{
		return this.id;
	}	
	public void setId(long id) 
	{
		this.id=id;
	}
	
	private boolean sold;
	public boolean getSold() 
	{
		return this.sold;
	}	
	public void setSold(boolean sold) 
	{
		this.sold = sold;
	}
	
	private double price;
	public double getPrice() 
	{
		return this.price;
	}
	public void setPrice(double price) 
	{
		this.price = price;
	}
	
	private String model;
	public String getModel() 
	{
		return this.model;
	}	
	public void setModel(String model) 
	{
		this.model = model;
	}
	
	private int megapixels;
	public int getMegaPixels() 
	{
		return this.megapixels;
	}	
	public void setMegaPixels(int megapixels) 
	{
		this.megapixels = megapixels;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Part> parts = new ArrayList<Part>();
	public void addPart(Part part) 
	{
		this.parts.add(part);
	}
	public List<Part> getParts() 
	{
		return this.parts;
	}
	
}
