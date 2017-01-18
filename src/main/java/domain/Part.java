package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ManyToOne;

@Entity
@NamedQueries({
	@NamedQuery(name = "part.all", query = "Select p from Part p")
})
public class Part 
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
	
	private String name;
	public String getName() 
	{
		return this.name;
	}
	public void setName(String name) 
	{
		this.name=name;
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
	
	@ManyToOne()
	private Phone phone;
	public Phone getPhone() 
	{
		return this.phone;
	}	
	public void setPhone(Phone owner) 
	{
		this.phone = owner;
	}
}
