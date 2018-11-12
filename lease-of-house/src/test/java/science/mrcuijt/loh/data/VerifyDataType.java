/**
 * 
 */
package science.mrcuijt.loh.data;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class VerifyDataType {

	@Test
	public void testRelDataType() {

		LinkedList<Object> obj = new LinkedList<>();
		
		BigDecimal bigDecimal = new BigDecimal("100");
		
		Integer integer = 1;
		
		String str = "str";
		
		int i = 2;
		
		obj.add(bigDecimal);
		obj.add(integer);
		obj.add(str);
		obj.add(i);
		
		for (Object object : obj) {
			
			System.out.println(object.getClass());
			System.out.println(object.getClass().getName());
			
		}
	}

}
