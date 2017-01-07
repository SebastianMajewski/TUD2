package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Accessory;
import com.example.shdemo.domain.Camera;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@EnableTransactionManagement 
public class eee {

	@Autowired
	SellingManager sellingManager;

	private final static String MODEL_1 = "D100";
	private final static int SHOTS_1 = 80000;
	private final static double PRICE_1 = 200;
	private final static boolean SOLD_1 = false;
	
	private final static String NAME_1 = "bag";
	private final static double PRICE_2 = 69;
	
	private final static String NEWMODEL_1 = "D200";
	private final static int NEWSHOTS_1 = 75000;
	private final static double NEWPRICE_1 = 450;
	private final static boolean NEWSOLD_1 = true;
	@Test
	public void addCameraCheck() {


		Camera camera = new Camera();
		camera.setModel(MODEL_1);
		camera.setPrice(PRICE_1);
		camera.setShots(SHOTS_1);
		camera.setSold(SOLD_1);

		sellingManager.addCamera(camera);

		Camera retrievedCamera = sellingManager.getAllCamera().get(0);

		assertEquals(NAME_1, retrievedCamera.getModel());
		assertEquals(SHOTS_1, retrievedCamera.getShots());
		// ... check other properties here
	}
}
