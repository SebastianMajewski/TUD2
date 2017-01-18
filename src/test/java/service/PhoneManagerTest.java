package service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import service.IPhoneManager;
import domain.Part;
import domain.Phone;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class PhoneManagerTest 
{
	@Autowired
	IPhoneManager phoneManager;

	private final static String MODEL_1 = "Microsoft 535 DualSim";
	private final static int MEGAPIXELS_1 = 8;
	private final static double PRICE_1 = 500;
	private final static boolean SOLD_1 = false;
	
	private final static String NAME_1 = "Screen";
	private final static double PRICE_2 = 120;
	private final static String NAME_2 = "Jack connector";
	private final static double PRICE_3 = 12;
	
	private final static String NEWMODEL_1 = "Microsoft 635";
	private final static int NEWMEGAPIXELS_1 = 12;
	private final static double NEWPRICE_1 = 900;
	private final static boolean NEWSOLD_1 = true;
	
	@Before
	public void add() 
	{
		Phone phone = new Phone();
		phone.setModel(MODEL_1);
		phone.setPrice(PRICE_1);
		phone.setMegaPixels(MEGAPIXELS_1);
		phone.setSold(SOLD_1);

		phoneManager.addPhone(phone);
		
		Part part = new Part();
		part.setName(NAME_1);
		part.setPrice(PRICE_2);
		part.setPhone(phone);
		
		phoneManager.addNewPart(part);
	}
	
	@After
	public void clean()
	{
		while(!phoneManager.getAllPhones().isEmpty())
		{
			phoneManager.deletePhone(phoneManager.getAllPhones().get(0));
		}
	}
	
	@Test
	public void addPhone() 
	{
		Phone _phone = (Phone)phoneManager.getPhoneByModel(MODEL_1).get(0);
		assertEquals(MODEL_1, _phone.getModel());
		assertEquals(MEGAPIXELS_1, _phone.getMegaPixels());
		assertEquals(PRICE_1, _phone.getPrice(), 0.1);
		assertEquals(SOLD_1, _phone.getSold());
	}
	
	@Test
	public void editPhone() 
	{	
		Phone phone = (Phone)phoneManager.getPhoneByModel(MODEL_1).get(0);
		phone.setModel(NEWMODEL_1);
		phone.setPrice(NEWPRICE_1);
		phone.setMegaPixels(NEWMEGAPIXELS_1);
		phone.setSold(NEWSOLD_1);
		phoneManager.editPhone(phone);
		
		Phone _phone = (Phone)phoneManager.getPhoneByModel(NEWMODEL_1).get(0);
		
		assertEquals(NEWMODEL_1, _phone.getModel());
		assertEquals(NEWMEGAPIXELS_1, _phone.getMegaPixels());
		assertEquals(NEWPRICE_1, _phone.getPrice(), 0.1);
		assertEquals(NEWSOLD_1, _phone.getSold());
	}
	
	@Test
	public void deletePhone() 
	{		
		Phone _phone = new Phone();
		_phone.setModel(NEWMODEL_1);
		_phone.setPrice(NEWPRICE_1);
		_phone.setMegaPixels(NEWMEGAPIXELS_1);
		_phone.setSold(NEWSOLD_1);

		phoneManager.addPhone(_phone);
		int count = phoneManager.getAllPhones().size();
		
		Phone phone = (Phone)phoneManager.getPhoneByModel(MODEL_1).get(0);
		phoneManager.deletePhone(phone);
		Phone __phone = (Phone)phoneManager.getPhoneByModel(NEWMODEL_1).get(0);		
		
		assertEquals(phoneManager.getAllPhones().size(), count-1);
		assertEquals(NEWMODEL_1, __phone.getModel());
		assertEquals(NEWMEGAPIXELS_1, __phone.getMegaPixels());
		assertEquals(NEWPRICE_1, __phone.getPrice(), 0.1);
		assertEquals(NEWSOLD_1, __phone.getSold());
	}
	
	@Test
	public void getPhoneById() 
	{
		Phone phone = new Phone();
		phone.setModel(MODEL_1);
		phone.setPrice(PRICE_1);
		phone.setMegaPixels(MEGAPIXELS_1);
		phone.setSold(SOLD_1);

		phoneManager.addPhone(phone);

		Phone phone1 = (Phone)phoneManager.getPhoneByModel(MODEL_1).get(0);	
		Phone byId = phoneManager.findPhoneById(phone1.getId());	
		assertEquals(phone1.getId(), byId.getId());		
	}
	
	
	@Test
	public void getPhoneByModel() 
	{
		Phone _phone = new Phone();
		_phone.setModel(NEWMODEL_1);
		_phone.setPrice(NEWPRICE_1);
		_phone.setMegaPixels(NEWMEGAPIXELS_1);
		_phone.setSold(NEWSOLD_1);

		phoneManager.addPhone(_phone);
		List<Phone> phoneList = phoneManager.getPhoneByModel(NEWMODEL_1);
		
		Phone __phone = (Phone)phoneList.get(0);
	
		assertEquals(NEWMODEL_1, __phone.getModel());
		assertEquals(NEWPRICE_1, __phone.getPrice(), 0.01);
		assertEquals(NEWMEGAPIXELS_1, __phone.getMegaPixels());
		assertEquals(NEWSOLD_1, __phone.getSold());
		phoneManager.deletePhone(_phone);	
	}
	
	@Test
	public void addPart() 
	{				
		Phone phone = phoneManager.getAllPhones().get(0);
		
		assertEquals(NAME_1, phone.getParts().get(0).getName());
		assertEquals(PRICE_2, phone.getParts().get(0).getPrice(), 0.01);
	}
	
	
	@Test
	public void editPart() 
	{
		int index = phoneManager.getPhoneByModel(MODEL_1).size();
		Phone phone = (Phone)phoneManager.getPhoneByModel(MODEL_1).get(index - 1);
		Part part = phone.getParts().get(0);	
		part.setName(NAME_2);
		part.setPrice(PRICE_3);
		
		phoneManager.editPart(part);
		
		index = phoneManager.getPhoneByModel(MODEL_1).size();
		Phone _phone = (Phone)phoneManager.getPhoneByModel(MODEL_1).get(index-1);
		Part _part = _phone.getParts().get(0);
		
		assertEquals(NAME_2, _part.getName());
		assertEquals(PRICE_3, _part.getPrice(),0.01);	
	}
	
	
	@Test
	public void disposePart() 
	{
		Phone phone = phoneManager.getAllPhones().get(0);
		Part part = phone.getParts().get(0);
		Part _part = new Part();
		_part.setName(NAME_2);
		_part.setPrice(PRICE_3);
		_part.setPhone(phone);
		
		phoneManager.addNewPart(_part);				
		phoneManager.disposePart(phone, part);
		
		assertEquals(NAME_2, phone.getParts().get(0).getName());
		assertEquals(PRICE_3, phone.getParts().get(0).getPrice(), 0.01);
				
	}
	
	@Test
	public void getPartById() 
	{	
		Part part = phoneManager.getAllParts().get(0);		
		Part byId = phoneManager.findPartById(part.getId());	
		assertEquals(part.getId(), byId.getId());	
	}
	
	@Test
	public void getAllPartFromPhone() 
	{
		Phone phone = phoneManager.getAllPhones().get(0);
		List<Part> list = phoneManager.getPartByPhone(phone);		
		
		int countByMethod = list.size();
		int countByHiberrnate = phone.getParts().size();
		
		assertEquals(countByMethod, countByHiberrnate);
	}	
}
