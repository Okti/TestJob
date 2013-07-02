
/*
 * 
 * Programa veikia labai paprastu princpu.
 * Naudodami "openfile" metoda, atidarome faila nurodyta "fName" argumenete. 
 * Nuskaitome faila lygiai po viena eilute arba linija. Kiekviena linija suskirstome, i penkias dalys
 * naudodami "StringTokenizer" klase.
 * Galiausiai kiekvienos dalies reiksme sulyginame su anksciau nustatytais Regularias isreiskimais
 * (angl. "Regular expressions") 
 * Gautus atsakymus surasau  i tekstini faila.
 */

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WMApacheLogai {
  
	private static final String IPREGEX = "([0-9]{1,3}\\.){3}[0-9]{1,3}"; // Regularius isreiskimas IP adresui atpazinti

		
	static void openfile(String fName) { 
		
		StringTokenizer str = null;
		
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		
		try( FileInputStream fis = new FileInputStream(fName)) {  
			
			String one = null;
			String two = null;
			String four = null;
			String seven = null;
			String eight = null;
			
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			
			String line = null;
						
			while ((line = bfr.readLine()) != null) {
				str = new StringTokenizer(line, " - + ");
				one = str.nextToken();
				two = "Uzklausos laikas: "+str.nextToken()+"\n"+"\n"; 
				two.replace('[', ' ');
				String tree = str.nextToken(); // del man nesuprantamu priezasciu,	
				four = str.nextToken();        // negaliu istrinti "tree", "five" ir "six" kintamuju
				String five = str.nextToken(); // kurie siuo atveju yra visiskai nereikalingi
				String six = str.nextToken();
				seven = str.nextToken();
				eight = str.nextToken();
				
				Pattern p1 = Pattern.compile("[POSTGETHEADOPTIONPUTDELETETRACECONNECTPATCH]+");
				Matcher m1 = p1.matcher(four);
				
				String kodas = m1.find() ? "HTTP Uzklausos kodas: "+m1.group()+"\n" : null;
				
				
				Pattern p2 = Pattern.compile("[0-600]+");
				Matcher m2 = p2.matcher(seven);
				
				String atsakymokodas = m2.find() ? "Http atsakymo kodas "+m2.group()+"\n" : null;
				
				Pattern p3 = Pattern.compile("[1-900]+");
				Matcher m3 = p3.matcher(eight);
				
				String dydis = m3.find() ? "Atsakymo dydis: "+m3.group()+"\n" : null;
				
				Pattern p4 = Pattern.compile(IPREGEX);
				Matcher m4 = p4.matcher(one);
				
				String ipadresas = m4.find() ? "IP adresas: "+m4.group()+"\n" : null;
				
				bos.write(ipadresas.getBytes());
				bos.write(kodas.getBytes());
				bos.write(atsakymokodas.getBytes());
				bos.write(dydis.getBytes());
				bos.write(two.getBytes());
				
			}
			
			try { 
				
				bfr.close();
				
			} catch (IOException e) { 
				System.out.println("Klaida uzdarant BufferedReader Streama: "+e);
			}
			
		} catch (IOException e) { 
			System.out.println("Klaida: "+e); }
		
		try (FileOutputStream fos = new FileOutputStream("wm_ataskaita.txt")) { 
			bos.writeTo(fos);
		} catch (IOException e) { 
			System.out.println("Klaida: "+e); }
		
	}
	
		
	public static void main(String[] args) { 
		
		WMApacheLogai.openfile("/home/okti/Downloads/logas");
	}
}
