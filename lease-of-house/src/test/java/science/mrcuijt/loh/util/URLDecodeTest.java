/**
 * 
 */
package science.mrcuijt.loh.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * @author Administrator
 *
 */
public class URLDecodeTest {

	public static void main(String[] args) {

		URLDecodeTest test = new URLDecodeTest();
		test.getLoacation();

	}

	@Test
	public void getLoacation() {

		String str = URLDecodeTest.class.getResource("").toString();

		System.out.println(str);

		str = Logger.class.getResource("").toString();

		System.out.println(str);

		str = JSON.class.getResource("").toString();

		System.out.println(str);

	}

	@Test
	public void testURLDecode() {

		try {

			String decodeResult = URLDecoder
					.decode("name=%E5%BC%A0%E4%B8%89&age=12&sex=%E7%94%B7&age=aa&age=bb&sens=aa", "UTF-8");

			System.out.println(decodeResult);

			int i = 0;

			while (i <= 10) {

				decodeResult = URLDecoder.decode(decodeResult, "UTF-8");
				System.out.println(decodeResult);
				i++;
			}

			i = 0;

			String encodeResult = URLEncoder.encode(decodeResult, "UTF-8");

			while (i <= 10) {

				encodeResult = URLEncoder.encode(encodeResult, "UTF-8");
				System.out.println(encodeResult);
				i++;
			}

			i = 0;
			decodeResult = URLDecoder.decode(encodeResult, "UTF-8");
			System.out.println(decodeResult);
			while (i <= 10) {

				decodeResult = URLDecoder.decode(decodeResult, "UTF-8");
				System.out.println(decodeResult);
				i++;
			}

			i = 0;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
