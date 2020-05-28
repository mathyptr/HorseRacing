package HorseRacing;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUnitHorse {
	MessagesBundle msgB= new MessagesBundle();
	
	@BeforeEach
	void setUp() throws Exception {
//		private java.util.Vector <horse> h =new java.util.Vector(1,1);	 
		msgB.SetLanguage("it", "IT");
	}

	@Test
	void testMove() {
		int vel,step,nStep,endHorse;
		movement  mov=new movement();
		horse h =new horse(1,1000,mov);
			
		nStep=10;//numero passi
		h.move();
		vel=h.getPosX();
        System.out.println("Vel: "+	vel);
        //endHorse=(step-1)*vel+end%vel;
				
		for(int i=1;i<nStep;i++) {
			h.move();
		System.out.println("Pos: "+	h.getPosX());
		}
		System.out.println("End horse: "+	nStep*vel);
		assertTrue(h.getPosX()==nStep*vel);		
	//		fail("Not yet implemented");
	
			
	}

	@Test
	void testHorseName() {

		movement  mov=new movement();
		horse h =new horse(1,100,mov);
			
		assertTrue(msgB.GetResourceValue("name_horse1").equals("Eragon"));			
		assertTrue(msgB.GetResourceValue("name_horse2").equals("Charlotte"));						
		assertTrue(msgB.GetResourceValue("name_horse3").equals("Ice"));			
		assertTrue(msgB.GetResourceValue("name_horse4").equals("Koral"));
		assertTrue(msgB.GetResourceValue("name_horse5").equals("Zoe"));			
		assertTrue(msgB.GetResourceValue("name_horse6").equals("Luna"));
		assertTrue(msgB.GetResourceValue("name_horse7").equals("Oxygen"));			
		assertTrue(msgB.GetResourceValue("name_horse8").equals("Salazar"));
		assertTrue(msgB.GetResourceValue("name_horse9").equals("Thor"));			
		assertTrue(msgB.GetResourceValue("name_horse10").equals("Kyros")); 				
			
	}

	
	@Test
	void testLanguage() {
		String label_numehorse,label_atmo;
		
		assertTrue(msgB.GetResourceValue("label_numehorse").equals("Numero di cavalli"));
		assertTrue(msgB.GetResourceValue("label_atmo").equals("Condizioni atmosferiche"));
		assertTrue(msgB.GetResourceValue("error_on_num_horse").equals("Devi inserire un numero intero!!!"));
	}
	
	@Test
	void testChangeLanguage() {
		msgB.SetLanguage("en", "US");
		
		assertTrue(msgB.GetResourceValue("label_numehorse").equals("Number of horses"));
		assertTrue(msgB.GetResourceValue("label_atmo").equals("weather"));
		assertTrue(msgB.GetResourceValue("error_on_num_horse").equals("You must insert an integer!!!"));

	}
	
	@Test
	void testRace() {
		java.util.Vector <horse> h =new java.util.Vector(1,1);
		int vel,endHorse,numhorse;
		int INITIAL_X,INITIAL_Y,corsia;
		INITIAL_X=10;
		INITIAL_Y=10;
		corsia=50;
		movement  step=new movement();
	
		endHorse=100;
		numhorse=5;
		
		for (int i=0;i<numhorse;i++) {
			h.addElement(new horse(i+1,endHorse,step));
			h.get(i).setPosX(INITIAL_X);
			h.get(i).setPosY(INITIAL_Y+i*corsia);
		}
		
		h.get(0).setspeed(60);	
		
		for (int i=0;i<numhorse;i++) {
			h.get(i).start();
		}
			
		boolean esci=false;
		for (int i=0;i<=100;i++) {
			step.put();
			for (int j=0;j<numhorse;j++) 
				if(h.get(j).getPosX()>=endHorse)
				{
					esci=true;
					break;      		
				}
			if(esci)
				break;
		}
		assertTrue(h.get(0).getPosX()>=endHorse);
		

	}
	
	


}
