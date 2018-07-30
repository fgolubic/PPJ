
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SintaksniAnalizator {

	public static Stack<Node<String>> stog = new Stack<>();
	public static String vrhStoga;
	public static String dnoStoga;
	public static String line;
	public static Node<String> tree =new Node<>("<program>"); 
	public static List<String> provjera=new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
		
		initializeList();

		dnoStoga="#";
		stog.push(new Node<>(dnoStoga));
		stog.push(tree);


		syntaxAnalyzer(reader);

		reader.close();
	}




	private static void syntaxAnalyzer(BufferedReader reader) throws IOException {
		line=reader.readLine();
		String ulaz;
		boolean krivo=false;
		boolean tocno=false;

		do{
			if(line==null){
				ulaz="!";
			}
			else{
				ulaz=line.split(" ")[0];
			}

			if(stog.peek().getData().equals("<program>")){
				switch(ulaz){
				case("IDN"):
					naredba1();
				break;

				case("KR_ZA"):
					naredba1();
				break;
				case("!"):
					naredba1();
				break;	
				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<lista_naredbi>")){
				switch(ulaz){
				case("IDN"):
					naredba2();
				break;

				case("KR_ZA"):
					naredba2();
				break;
				case("!"):
					naredba3();
				break;
				case("KR_AZ"):
					naredba3();
				break;
				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<naredba>")){
				switch(ulaz){
				case("IDN"):
					naredba4();
				break;

				case("KR_ZA"):
					naredba5();
				break;
				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<naredba_pridruzivanja>")){
				switch(ulaz){
				case("IDN"):
					naredba6(reader);
				break;

				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<za_petlja>")){
				switch(ulaz){

				case("KR_ZA"):
					naredba7(reader);
				break;

				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<E>")){
				switch(ulaz){
				case("IDN"):
					naredba8();
				break;
				case("BROJ"):
					naredba8();
				break;
				case("OP_PLUS"):
					naredba8();
				break;
				case("OP_MINUS"):
					naredba8();
				break;
				case("L_ZAGRADA"):
					naredba8();
				break;
				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<E_lista>")){
				switch(ulaz){
				case("IDN"):
					naredba3();
				break;
				case("KR_ZA"):
					naredba3();
				break;
				case("OP_PLUS"):
					naredba9(reader);
				break;
				case("OP_MINUS"):
					naredba9(reader);
				break;
				case("D_ZAGRADA"):
					naredba3();
				break;
				case("KR_DO"):
					naredba3();
				break;
				case("KR_AZ"):
					naredba3();
				break;
				case("!"):
					naredba3();
				break;
				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<T>")){
				switch(ulaz){
				case("IDN"):
					naredba10();
				break;
				case("BROJ"):
					naredba10();
				break;
				case("OP_PLUS"):
					naredba10();
				break;
				case("OP_MINUS"):
					naredba10();
				break;
				case("L_ZAGRADA"):
					naredba10();
				break;
				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<T_lista>")){
				switch(ulaz){
				case("IDN"):
					naredba3();
				break;
				case("KR_ZA"):
					naredba3();
				break;
				case("OP_PLUS"):
					naredba3();
				break;
				case("OP_MINUS"):
					naredba3();
				break;
				case("D_ZAGRADA"):
					naredba3();
				break;
				case("KR_DO"):
					naredba3();
				break;
				case("KR_AZ"):
					naredba3();
				break;
				case("!"):
					naredba3();
				break;
				case("OP_PUTA"):
					naredba11(reader);
				break;
				case("OP_DIJELI"):
					naredba11(reader);
				break;
				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("<P>")){
				switch(ulaz){
				case("IDN"):
					naredba14(reader);
				break;
				case("BROJ"):
					naredba14(reader);
				break;
				case("OP_PLUS"):
					naredba12(reader);
				break;
				case("OP_MINUS"):
					naredba12(reader);
				break;
				case("L_ZAGRADA"):
					naredba13(reader);
				break;
				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("IDN")){
				switch(ulaz){
				case("IDN"):
					naredba14(reader);
				break;

				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("OP_PRIDRUZI")){
				switch(ulaz){
				case("OP_PRIDRUZI"):
					naredba14(reader);
				break;

				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("KR_OD")){
				switch(ulaz){
				case("KR_OD"):
					naredba14(reader);
				break;

				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("KR_DO")){
				switch(ulaz){
				case("KR_DO"):
					naredba14(reader);
				break;

				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("KR_AZ")){
				switch(ulaz){
				case("KR_AZ"):
					naredba14(reader);
				break;

				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("D_ZAGRADA")){
				switch(ulaz){
				case("D_ZAGRADA"):
					naredba14(reader);
				break;

				default:
					krivo=true;
					break;
				}
			}

			else if(stog.peek().getData().equals("#")){
				switch(ulaz){
				case("!"):
					tocno=true;
				break;

				default:
					krivo=true;
					break;
				}
			}

		}while(!krivo && !tocno);

		if(krivo){
			if(line==null){
				System.out.println("err kraj");
			}
			else{
				System.out.println("err " + line);
			}
		}
		else{
			printTree(tree,0);
		}
	}




	private static void naredba1(){
		zamijeni("<lista_naredbi>");

	}

	private static void naredba2(){
		Node<String> temp =zamijeni("<lista_naredbi>");
		stavi("<naredba>", temp);

	}

	private static void naredba3(){
		izvuci();

	}

	private static void naredba4(){
		zamijeni("<naredba_pridruzivanja>");

	}

	private static void naredba5(){
		zamijeni("<za_petlja>");

	}

	private static void naredba6(BufferedReader reader) throws IOException{
		
		Node<String> temp =zamijeni("<E>");
		stavi("OP_PRIDRUZI", temp);
		pomakni(reader,temp);

	}

	private static void naredba7(BufferedReader reader) throws IOException{
		
		Node<String> temp =zamijeni("KR_AZ");
		stavi("<lista_naredbi>", temp);
		stavi("<E>", temp);
		stavi("KR_DO", temp);
		stavi("<E>", temp);
		stavi("KR_OD", temp);
		stavi("IDN", temp);
		pomakni(reader,temp);

	}

	private static void naredba8(){
		Node<String> temp =zamijeni("<E_lista>");
		stavi("<T>", temp);

	}

	private static void naredba9(BufferedReader reader) throws IOException{
		
		Node<String>temp=zamijeni("<E>");
		pomakni(reader, temp);

	}

	private static void naredba10(){
		Node<String> temp =zamijeni("<T_lista>");
		stavi("<P>", temp);

	}

	private static void naredba11(BufferedReader reader) throws IOException{
		
		Node<String>temp=zamijeni("<T>");
		pomakni(reader, temp);

	}

	private static void naredba12(BufferedReader reader) throws IOException{
		
		Node<String>temp=zamijeni("<P>");
		pomakni(reader,temp);

	}

	private static void naredba13(BufferedReader reader) throws IOException{
		
		Node<String> temp = zamijeni("D_ZAGRADA");
		stavi("<E>", temp);
		pomakni(reader,temp);

	}

	private static void naredba14(BufferedReader reader) throws IOException{
		Node<String>temp=stog.peek();
		izvuci();
		pomakni(reader,temp);
		for(int i=0;i<temp.getChildren().size();i++){
			if(temp.getChildren().get(i).getData().equals("$")){
				temp.getChildren().remove(i);
				break;
			}
		}

	}

	private static void pomakni(BufferedReader reader, Node<String> temp3) throws IOException {
		Node<String> temp2= new Node<>(line);
		if(provjera.contains(temp3.getData())){
			temp3.setData(line);
		}
		else{
		temp2.setParent(temp3);
		temp3.addChild(temp2);
		}
		
		line=reader.readLine();

	}



	private static Node<String> zamijeni(String znak){
		Node<String> temp=stog.pop();
		Node<String>temp2=new Node<>(znak);
		temp2.setParent(temp);
		temp.addChild(temp2);
		stog.push(temp2);
		return temp;
	}

	private static void stavi(String znak,  Node<String> parent){
		Node<String> temp= new Node<>(znak);
		temp.setParent(parent);
		parent.addChild(temp);
		stog.push(temp);


	}


	private static void izvuci() {
		Node<String> temp =stog.pop();
		Node<String> temp2 =new Node<>("$");
		if(!provjera.contains(temp.getData().split(" ")[0]) && temp.getChildren().isEmpty()){
			temp2.setParent(temp);
			temp.addChild(temp2);
		}
	}

	
	


	public static  void printTree(Node<String> root, int depth){

		if(root==null)
			return;

		for(int i=0;i<depth;i++){
			System.out.print(" ");
		}

		System.out.println(root.getData());

		for(Node<String> temp:root.getChildren()){
			printTree(temp, depth+1);
		}
	}

	public static void initializeList(){
		provjera.add("IDN");
		provjera.add("OP_PRIDRUZI");
		provjera.add("KR_OD");
		provjera.add("KR_DO");
		provjera.add("KR_AZ");
		provjera.add("D_ZAGRADA");

	}


}


class Node<T> {


	private  List<Node<T>> children = new LinkedList<Node<T>>();
	private  Node<T> parent = null;
	private T data = null;

	public Node(T data) {
		this.data = data;
	}

	public Node(T data, Node<T> parent) {
		this.data = data;
		this.parent = parent;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public void addChild(T data) {
		Node<T> child = new Node<T>(data);
		child.setParent(this);
		this.children.add(0,child);
	}

	public void addChild(Node<T> child) {
		child.setParent(this);
		 this.children.add(0,child);
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isRoot() {
		return (this.parent == null);
	}

	public boolean isLeaf() {
		if(this.children.size() == 0) 
			return true;
		else 
			return false;
	}

	public void removeParent() {
		this.parent = null;
	}


}
