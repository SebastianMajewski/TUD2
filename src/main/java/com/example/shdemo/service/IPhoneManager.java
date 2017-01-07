package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Part;
import com.example.shdemo.domain.Phone;

public interface IPhoneManager 
{
	void addPhone(Phone phone);
	void deletePhone(Phone phone);
	void sellPhone(long phoneId);
	boolean editPhone(Phone phone);
	List<Phone> getAllPhones();
	Phone findPhoneById(long id);
	List<Phone> getPhoneByModel(String model);
	
	List<Part> getAllParts();
	List<Part> getPartByPhone(Phone phone);
	void disposePart(Phone phone, Part part);
	Part findPartById(long id);
	Long addNewPart(Part part);
	void deletePart(Part part);
	boolean editPart(Part part);

}
