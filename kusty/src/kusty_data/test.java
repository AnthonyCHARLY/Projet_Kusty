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
		List<String> l = new ArrayList<String>();
		l.add("bonjour");
		l.add("ponjour");
		l.add("blnjour");
		l.add("bobjour");
		l.add("bonkour");
		l.add("bonjpur");
		l.add("bonjoyr");
		l.add("bonjoue");
		
		String search  = "bonjo";
		int cm = search.length();
		for(String s : l) {
			int count = search.length();
			for(int i = 0 ; i < search.length() ; i++) {
				if(search.charAt(i) == s.charAt(i)) {
					count--;
				}
			}
			if(count == 0) {
				System.out.println(s);
			}
			
		}
		
	}

}


