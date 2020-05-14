package kusty_data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Recipe test = new Recipe("test",10,1);
		//test.addPeriode(2);
		//System.out.println(Arrays.toString(test.getPeriodes()));
		Map<Integer,String> test = new HashMap<Integer,String>();
		test.put(1, "1");
		test.put(2, "2");
		test.put(3, "3");
		String l[] = new String[test.size()];
		int i = 0;
		for(String s : test.values()) {
		System.out.println(s);
		}
	}

}


