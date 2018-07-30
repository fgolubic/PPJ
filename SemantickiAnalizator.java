import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SemantickiAnalizator {


	private static Stack<Node<String>> stog=new Stack<>();
	private static int loopCounter=0;
	private static boolean useFlag=false;
	private static LinkedHashMap<String,String> declarations = new LinkedHashMap<>();
	private static LinkedHashMap<String,String> loopDeclarations = new LinkedHashMap<>();
	private static Node<String> syntaxTree;

	public static void main(String[] args) throws IOException {
		BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
		String line;
		int depth;


		syntaxTree= new Node<String>(reader.readLine());

		while((line=reader.readLine())!=null){
			depth=spaceNo(line);
			createTree(line.trim(),depth-1,syntaxTree);
		}
		stog.push(new Node<String>("#"));
		analysis(syntaxTree);

	}

	private static void analysis(Node<String> node) {
		if(node==null) return;
		String data=node.getData();
		if(data.equals("<za_petlja>")){
			loopCounter++;
		}
		if((data.contains("OP_PRIDRUZI")||data.contains("KR_OD")|| data.contains("KR_DO")) && !useFlag ){
			stog.push(new Node<String>("use"));
			useFlag=true;
		}

		if(stog.peek().getData().equals("use") && data.equals("<lista_naredbi>") && useFlag){
			stog.pop();
			if(stog.peek().getData().equals("<za_petlja>")){
				useFlag=false;
			}
			else{
				stog.push(new Node<String>("use"));
			}
		}
		stog.push(node);

		analyzeNode(node, data);

		for(Node<String> temp:node.getChildren()){
			analysis(temp);
		}


		if(stog.peek().getData().equals("use") && !(data.contains("OP_PRIDRUZI")||data.contains("KR_OD")|| data.contains("KR_DO"))){
			useFlag=false;
			stog.pop();
		}

		if(data.equals("<za_petlja>")){
			LinkedList<String> list =removeConuter();
			for(String temp:list){
				loopDeclarations.remove(temp);
			}
			if(!list.isEmpty()){
				list.removeAll(list);
			}

			loopCounter--;
		}
		stog.pop();


	}

	public static LinkedList<String> removeConuter() {
		LinkedList<String> eraseList=new LinkedList<>();
		for(String temp: loopDeclarations.keySet()){
			if(temp.split("_")[1].equals(Integer.toString(loopCounter))){
				//loopDeclarations.remove(temp);
				eraseList.add(temp);

			}
		}
		return eraseList;
	}

	public static void analyzeNode(Node<String> node, String data) {
		if(node.getChildren().isEmpty()){
			if(loopCounter==0){
				if(data.contains("IDN")){
					String temp=data.split(" ")[2];
					String row=data.split(" ")[1];
					if(useFlag){
						if(declarations.containsKey(temp)){
							if(row.equals(declarations.get(temp))){
								System.out.println("err " + row + " " + temp);
								System.exit(1);
							}
							System.out.println(row + " "+ declarations.get(temp) + " " + temp);
						} else {
							System.out.println("err " + row + " " + temp);
							System.exit(1);
						}
					}
					if(!declarations.containsKey(temp)){
						declarations.put(temp, row);
					}
				}  
			}
			else if(loopCounter<0){
				System.out.println("Nekaj negdje ne stima.");
			}
			else{
				inLoopAnalysis(node,data);
			}
		}
	}

	public static void inLoopAnalysis(Node<String> node, String data) {
		if(data.contains("IDN")){
			String temp=data.split(" ")[2];
			String row=data.split(" ")[1];
			if(useFlag){
				if(!declarations.containsKey(temp) && !loopDeclarations.containsKey(temp+"_"+getRow(temp))){
					System.out.println("err "+row + " " +temp);
					System.exit(1);
				}

				else if(declarations.containsKey(temp)) {
					System.out.println( row + " " + declarations.get(temp) + " " + temp );

				}
				else if(isInLoopMap(temp)){
					if(row.equals(loopDeclarations.get(getLoopVariable(temp)))){
						System.out.println("err " + row + " " + temp);
						System.exit(1);
					}
					System.out.println(row + " " +loopDeclarations.get(getLoopVariable(temp))+ " "+ temp);
				}
			}
			else{
				if(!isInLoopMap(temp) && !declarations.containsKey(temp)){
					loopDeclarations.put(temp+"_"+loopCounter, row );
				}
			
			}
		}
	}

	private static String getRow(String data) {
		String row=null;
		for(String temp: loopDeclarations.keySet()){
			if(temp.split("_")[0].equals(data)){
				row=temp.split("_")[1];
				break;
			}
		}
		return row;
	}

	public static String getLoopVariable(String temp) {
		for(String temp2:loopDeclarations.keySet()){
			if(temp2.split("_")[0].equals(temp)){
				return temp2;
			}
		}
		return null;
	}

	private static boolean isInLoopMap(String temp) {
		for(String temp2:loopDeclarations.keySet()){
			if(temp2.split("_")[0].equals(temp))
				return true;
		}
		return false;
	}

	public static void createTree(String line, int depth, Node<String> cvor) {
		if(depth!=0){
			createTree(line, depth-1, cvor.getChildren().get(cvor.getChildren().size()-1));
		}

		else{
			cvor.addChild2(line);
		}

	}


	public static int spaceNo(String line) {
		int spaces=0;
		for(int i=0;i<line.length();i++){
			if(line.charAt(i)==' '){
				spaces++;
			}
			else break;
		}
		return spaces;
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

	public void addChild2(T data){
		Node<T> child = new Node<T>(data);
		child.setParent(this);
		this.children.add(child);
	}




}
