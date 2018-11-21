package test.d;

import java.util.Arrays;

public class ArrayCopyTest {

	public static void main(String[] args) {

		byte[] ary = new byte[1024];

		byte[] ary2 = new byte[625];

		Arrays.fill(ary2, (byte) 1);

		byte[] ary3 = new byte[399];

		Arrays.fill(ary3, (byte) 2);

		System.arraycopy(ary2, 0, ary, 0, ary2.length);

		System.arraycopy(ary3, 0, ary, ary2.length, ary3.length);

		int i = 0;
		for (byte b : ary) {
			System.out.println((i + 1) + "\t" + b + "\t" + (i) + "\t" + b);
			i++;
		}
	}

}
