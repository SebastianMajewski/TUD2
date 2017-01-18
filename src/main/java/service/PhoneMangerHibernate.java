package service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Phone;
import domain.Part;

@Component
@Transactional
public class PhoneMangerHibernate implements IPhoneManager
{
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() 
	{
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) 
	{
		this.sessionFactory = sessionFactory;
	}

	public void addPhone(Phone phone) 
	{
		sessionFactory.getCurrentSession().persist(phone);
	}	

	public void deletePhone(Phone phone) 
	{
		phone = (Phone) sessionFactory.getCurrentSession().get(Phone.class,phone.getId());
		for (Part part : phone.getParts()) 
		{
			sessionFactory.getCurrentSession().delete(part);
		}
		sessionFactory.getCurrentSession().delete(phone);
	}

	@SuppressWarnings("unchecked")
	public List<Phone> getAllPhones() 
	{
		return sessionFactory.getCurrentSession().getNamedQuery("phone.all").list();
	}
	
	public boolean editPhone(Phone phone)
	{
		try
		{
			sessionFactory.getCurrentSession().update(phone);
		} 
		catch(Exception e) 
		{
		 return false;
		}		
		return true;
	}
	
	public void sellPhone(long phoneId) 
	{
		Phone phone = (Phone) sessionFactory.getCurrentSession().get(Phone.class, phoneId);
		phone.setSold(true);
	}

	public Phone findPhoneById(long id) 
	{
		return (Phone) sessionFactory.getCurrentSession().get(Phone.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Phone> getPhoneByModel(String model)
	{
		return sessionFactory.getCurrentSession().getNamedQuery("findByModel").setString("model", model).list();
	}

	@SuppressWarnings("unchecked")
	public List<Part> getAllParts() 
	{
		return sessionFactory.getCurrentSession().getNamedQuery("part.all").list();
	}

	public Long addNewPart(Part part) 
	{
		Phone phone = part.getPhone();
		phone.addPart(part);
		sessionFactory.getCurrentSession().save(phone);
		return (Long) sessionFactory.getCurrentSession().save(part);
	}
	
	public boolean editPart(Part part)
	{
		try
		{
			sessionFactory.getCurrentSession().update(part);
			sessionFactory.getCurrentSession().update(part.getPhone());
		} 
		catch(Exception e) 
		{
		 return false;
		}
		
		return true;
	}

	public void deletePart(Part part)
	{
		Part _part = (Part) sessionFactory.getCurrentSession().get(Part.class, part.getId());
		sessionFactory.getCurrentSession().delete(_part);
	}
	
	public List<Part> getPartByPhone(Phone phone) 
	{
		 Phone _phone = (Phone) sessionFactory.getCurrentSession().get(Phone.class,phone.getId());		 
		 return _phone.getParts();
	}

	public void disposePart(Phone phone, Part part) 
	{
		Part _part = (Part) sessionFactory.getCurrentSession().get(Part.class,part.getId());
		phone.getParts().remove(part);
		sessionFactory.getCurrentSession().delete(_part);
	}

	public Part findPartById(long id) 
	{
		return (Part)sessionFactory.getCurrentSession().get(Part.class, id);
	}
}
