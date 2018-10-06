package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class GatewayControllerTest {

	@Test
	public void test() {
		System.out.println("\n------ Testing Gateway Controller... ------");
		
		assertEquals(readPythonCommand.ReadPython.readPython(), "Success");  
	}

}
