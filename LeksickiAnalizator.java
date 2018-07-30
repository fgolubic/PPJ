package hr.fer.ppj.LeksickiAnalizator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
//import java.util.Scanner;

public class LeksickiAnalizator {

	public static LinkedHashMap<String,String> kRijeci =new LinkedHashMap<String,String>();
	public static LinkedHashMap<Character,String> operatori=new LinkedHashMap<>();
	public static int lineNumber=0;
	public static void main(String[] args) throws IOException {
		BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
		String line;
		//Scanner scan=new Scanner(System.in);
		
		inicijaliziraj(); 
		
		line=reader.readLine();
		//line=scan.nextLine();
		while(line!=null){
			lineNumber++;
			line.trim();
			line=izbaciKomentareDka(line);
			obradiRed(line); 
			line=reader.readLine();
			
		}
		//scan.close();
		

	}

	private static String izbaciKomentareDka(String line) {
		int i=0;
		int state=0;
		for(int j=0;j<line.length();j++){
			switch (state){
				case 0:{
					if(line.charAt(j)=='/') state=1;
					i++;
					break;
				}
				case 1:{
					if(line.charAt(j)=='/'){
						state=2;
						i--;
					}
					else{
						state=0;
						i++;
					}
					break;
				}
				case 2:{
					return line.substring(0,i);
				}
		
			}
		}
		return line.substring(0, i);
	}

	private static void obradiRed(String line) {
		String[] row=line.split("[\n|\\t|\\ |\\r]+"); 
		for(int i=0;i<row.length;i++){
			if(kRijeci.containsKey(row[i])){ 
				System.out.println(kRijeci.get(row[i])+ " "+ lineNumber + " " + row[i]);
				
			}
			else{
			obradi2(row[i]); 
			}
		}
	}
	

	private static void obradi2(String string) {
		
		String temp;
		int i=0;
		while(i<string.length()){
			if(operatori.containsKey(string.charAt(i))){
				System.out.println(operatori.get(string.charAt(i))+ " " + lineNumber + " " + string.charAt(i));
				i++;
			}
			else{
				temp=string.substring(i);
				i=executeDka(temp,i);
			}
		}
		
	}
	

	private static int executeDka(String temp, int i) {     
		String trenutnaJed=" ";
		int state=0;
		for(char c: temp.toCharArray()){
			switch(state){
				case(0):{
					if(Character.isDigit(c)){
						state=1;
						trenutnaJed+=Character.toString(c);
						trenutnaJed.trim();
					}
					else{
						state=2;
						trenutnaJed+=Character.toString(c);
						trenutnaJed.trim();
					}
					break;
				}
				case(1):{
					if(Character.isDigit(c)){
						trenutnaJed+=Character.toString(c);
					}
					else{
						System.out.println("BROJ " + lineNumber + " "+ trenutnaJed);
						return i;
					}
					break;
				}
				case(2):{
					if(Character.isDigit(c)||Character.isLetter(c)){
						trenutnaJed+=Character.toString(c);
					}
					else{
						System.out.println("IDN " + lineNumber + " "+ trenutnaJed);
						return i;
					}
					break;
				}
			}
			i++;		
		}
		if(state==2){
			System.out.println("IDN " + lineNumber + " "+ trenutnaJed);

		}
		else if(state==1){
			System.out.println("BROJ " + lineNumber + " "+ trenutnaJed);

		}
		
		
		return i;
	}
	private static void inicijaliziraj() {
		operatori.put('=',"OP_PRIDRUZI");
		operatori.put('+', "OP_PLUS");
		operatori.put('-',"OP_MINUS");
		operatori.put('*',"OP_PUTA");
		operatori.put('/', "OP_DIJELI");
		operatori.put('(', "L_ZAGRADA");
		operatori.put(')',"D_ZAGRADA");
		kRijeci.put("za", "KR_ZA");
		kRijeci.put("od","KR_OD");
		kRijeci.put("do","KR_DO");
		kRijeci.put("az","KR_AZ");

	}

}
