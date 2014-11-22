import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
//import org.slf4j.LoggerFactory;

/**
 * @author shashank_local
 * http://en.wikipedia.org/wiki/Trie
 */

/*ToDo 
 * Exception Handling 
 * Generics 
 * Logging
 * Multithreading Support
 * Scalability 
 * Complexity Analysis for each operation
 * Add regex for auto suggestion 
 * Ranking of chars , Dynamic Trie
 * Compressed Trie , Then TST
 */
 
class Node implements Comparable<Node> , Serializable {
	
	private static final long serialVersionUID = 1L;
	private Character data;
	private HashMap<Character, Node> children; 
	// Need to modify to TreeMap as iterating over Trie in print method
	private Set<String> indexes; // used to group together all anagrams
	private Integer weight;
	private boolean isValid;
	public Node(char data){
		this.data=data;
		children=new HashMap<Character,Node>(256); //256 Ascii
		indexes= new HashSet<String>(); // No resizing 
		isValid=false;
	}
	
	public void setValid(boolean valid){
		isValid=valid;
	}
	
	public boolean checkValid(){
		return isValid;
	}
	
	public HashMap<Character, Node> getChildren(){
		return children;
	}
	
	public Character getData(){
		return data;
	}
	
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public Set<String> getIndexes() {
		return indexes;
	}

	public void setIndexes(Set<String> indexes) {
		this.indexes = indexes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
	//Used to rank words sort in non-increasing order e.g one has more 
	// hits weight is increased by 1
	@Override
	public int compareTo(Node that) {
		if(null==that) return -1;
		if(that.getWeight()<this.getWeight()) return -1;
		else if (that.getWeight()>this.getWeight()) return 1;
		else return 0;
	}
	
}

interface MyTrie {
	
	/**
	 * @param value
	 * @return
	 */
	public boolean search(String value);
	
	/**
	 * @param prefix
	 * @return
	 */
	public List<String> autoSuggest(String prefix);
	/**
	 * @param value
	 */
	public void insert(String value);
	/**
	 * @param value
	 * @param actual
	 */
	public void insert(String value,String actual); //actual is used to insert actual value of string while input might get modified as in case anagrams 
	/**
	 * @param value
	 * @return
	 */
	public String getLongestMatchingPrefix(String value);
	/**
	 * @param start
	 * @param prefix
	 * @param list
	 * @return
	 */
	public List<String> printTrieUsingDFS(Node start,final String prefix, List<String> list);
	/**
	 * @param start
	 * @param anagrams
	 * @return
	 */
	public Set<Set<String>> groupAnagrams(Node start,Set<Set<String>> anagrams);
	/**
	 * @param root
	 * @param prefix
	 * @return
	 */
	public StringBuilder printTrieBFS(Node root,String prefix);
}

class Trie implements MyTrie {
	public Node root;
	public Trie(){
		root= new Node(' ');
	}
	
	public Node getRoot(){
		return root;
	}

	/**
	 * @param root
	 * @param suffix
	 */
	void suffixSearch(Node root,String suffix){ //To Do
		
		int length=suffix.length();
		if(null==suffix || length==0)
			return ;
		Integer ar[]=new Integer[suffix.length()];
		int index=0;
		int i=0;
		Node crawl=root;
		HashMap<Character, Node> childrens=crawl.getChildren();
		for(Map.Entry<Character, Node> child:childrens.entrySet()){
			int level=0; 
			for(level=0;level<suffix.length();level++){
				Character suffixChar=suffix.charAt(level);
					if(child.getKey()!=suffixChar)
						break;
					else{
						crawl=childrens.get(suffixChar);
						if(null==crawl)
							break;
					}
				}
			if(level==suffix.length())
				ar[i++]=index;
			index++;
		}
		
		for(i=0;i<ar.length;i++){
			System.out.println(ar[i]);
		}
		
	}
	/* (non-Javadoc)
	 * @see MyTrie#search(java.lang.String)
	 */
	@Override
	public boolean search(String input) {
		// TODO Auto-generated method stub
		int length=input.length();
		if(null==input || length==0)
			return true;
		
		Node crawl=root;
		for(int level=0;level<length;level++){
			HashMap<Character, Node> childrens=crawl.getChildren();
			Character child=input.charAt(level);
			if(null!=childrens){
				if(childrens.containsKey(child)){
					crawl=childrens.get(child);
					if(null==crawl)
						return false;
				}
				else 
					return false;
			}
		}
		if(crawl.checkValid()) return true;
		return false;
	}

	
	/* (non-Javadoc)
	 * @see MyTrie#insert(java.lang.String)
	 */
	@Override
	public void insert(String input) {
		// TODO Auto-generated method stub
		int length=input.length();
		if(null==input || length==0)
			return ;
		
		Node crawl=root;
		for(int level=0;level<length;level++){
			HashMap<Character, Node> childrens=crawl.getChildren();
			Character child=input.charAt(level);
			if(null!=childrens){
				if(childrens.containsKey(child)){
					crawl=childrens.get(child);
				}
				else {
					Node childNode =new Node(child);
					childrens.put(child, childNode);
					crawl=childNode;
				}
			}
		}
		crawl.setValid(true);
	}
	
	/* (non-Javadoc)
	 * @see MyTrie#insert(java.lang.String)
	 */
	@Override
	public void insert(String input,String actual) {
		// TODO Auto-generated method stub
		int length=input.length();
		if(null==input || length==0)
			return ;
		
		Node crawl=root;
		for(int level=0;level<length;level++){
			HashMap<Character, Node> childrens=crawl.getChildren();
			Character child=input.charAt(level);
			if(null!=childrens){
				if(childrens.containsKey(child)){
					crawl=childrens.get(child);
				}
				else {
					Node childNode =new Node(child);
					childrens.put(child, childNode);
					crawl=childNode;
				}
			}
		}
		crawl.setValid(true);
		Set<String> leafs=  (Set<String>) crawl.getIndexes();
		leafs.add(actual); // Avoid tail traversal 
		
	}
	
    /* (non-Javadoc)
     * @see MyTrie#getMatchingPrefix(java.lang.String)
     */
    public String getLongestMatchingPrefix(String input)  { 
        String result = ""; 
        int length = input.length();  
        Node crawl = root;   
          
        // Iterate through all characters of input string 'str' and traverse 
        // down the Trie
        int level, prevMatch = 0; 
        for( level = 0 ; level < length; level++ )
        {    
            char ch = input.charAt(level);    
            HashMap<Character,Node> child = crawl.getChildren();                        
            if(child.containsKey(ch))
            {
               result += ch;          //Update result
               crawl = child.get(ch); //Update crawl to move down in Trie
               // If this is end of a word, then update prevMatch
               if( crawl.checkValid() ) 
                    prevMatch = level + 1;
            }            
            else  break;
        }
        // If the last processed character did not match end of a word, 
        // return the previously matching prefix
        if( !crawl.checkValid())
                return result.substring(0, prevMatch);        
         
        else return result;
    }
    
    /* (non-Javadoc)
     * @see MyTrie#groupAnagrams(Node, java.lang.String, java.util.List)
     */
    @Override
	public Set<Set<String>> groupAnagrams(Node start,Set<Set<String>> anagrams) {
    	if(start.checkValid()){
			anagrams.add((HashSet<String>) start.getIndexes());// Add all list anagrams to big list
			if(start.getChildren().size() == 0)
			{
				return anagrams;
			}
    	}
    	
    	HashMap<Character,Node> childrens=start.getChildren();
		for(Map.Entry<Character,Node> child:childrens.entrySet()){
			groupAnagrams(child.getValue(),anagrams);
		}
		return anagrams;
	}
    
    /**
     * @param start
     * @param prefix
     * @param list
     * @return
     */
    public List<String> printTrieUsingDFS(Node start,final String prefix, List<String> list){
    	if(start.checkValid()){
			list.add(prefix);
			if(start.getChildren().size() == 0){
				return list;
			}
    	}
    	
    	HashMap<Character,Node> childrens=start.getChildren();
		for(Map.Entry<Character,Node> child:childrens.entrySet()){
			printTrieUsingDFS(child.getValue(), prefix+child.getValue().getData(),list);
		}
		return list;
	}
    
    /* (non-Javadoc)
	 * @see MyTrie#printTrieBFS(Node, java.lang.String)
	 */
	@Override
	public StringBuilder printTrieBFS(Node root, String prefix) {
		// TODO Auto-generated method stub
		Node crawl=root;
		//StringBuilder> allWords=new ArrayList<StringBuilder>();
		StringBuilder sb= new StringBuilder();;
		Queue<Node> queue=new LinkedList<Node>();
		queue.offer(crawl);
		while(!queue.isEmpty()){
			Node temp=queue.poll();
			sb.append(temp.getData()+ " ");
			if(null!=temp){
				HashMap<Character, Node> childrens=temp.getChildren();
				for(Node node:childrens.values()){
					queue.offer(node);
				}
			}
		}
		return sb;
	}

	public  Set<Integer> suffixTrie(Node root,int index,int count, String prefix,Set<Integer> indexes) {
//		if(root.getData()!=prefix.charAt(index))
//			  return null;
		//A word can be formed at this point, hence add it to the container.
		if(-1!=index && prefix.length()>index && root.getData()==prefix.charAt(index)) 
		{
			  if (index==prefix.length()-1) {
				  indexes.add(count);
				  	return indexes;
			  }
		}
		else 
			index--;
		  
		  //Recursively go through all the children of the node and form words.
		  for (Map.Entry<Character, Node> entry : root.getChildren().entrySet()) {
		    Node _node = entry.getValue();
		    suffixTrie(_node, index+1,count,prefix, indexes);
		    count++;
		  }
		  
		  return indexes;
		}
	/**
	 * @param node
	 * @param prefix
	 * @param words
	 * @return
	 */
	private List<String> walkDown(Node node, String prefix, List<String> words) {
		  //A word can be formed at this point, hence add it to the container.
		  if (node.checkValid()) {
		    words.add(prefix);
		     
		    //This branch of the tree ends here as there are no more children. Hence return.
		    if (node.getChildren().size() == 0) {
		      return words;
		    }
		  }
		  
		  //Recursively go through all the children of the node and form words.
		  for (Map.Entry<Character, Node> entry : node.getChildren().entrySet()) {
		    Node _node = entry.getValue();
		    walkDown(_node, prefix + _node.getData(), words);
		  }
		 
		  return words;
	}
	
	/* (non-Javadoc)
	 * @see MyTrie#autoSuggest(java.lang.String)
	 */
	@Override
	public List<String> autoSuggest(String prefix) {
		// TODO Auto-generated method stub
		  Node crawl=root;
	
		  List<String> words = new LinkedList<String>();
		 
		  char[] chars = prefix.toCharArray();
		 
		  char first = chars[0];
		 
		  //First letter is not present as a key in the root, hence there cannot be any words in this trie corresponding to the
		  //prefix.
		  HashMap<Character, Node> childrens= crawl.getChildren();
		  if (!childrens.containsKey(first)) {
		    return words;
		  }
		 
		  for (int i = 0; i < chars.length; ++i) {
		    char aChar = chars[i];
		    childrens= crawl.getChildren();
		    // if No entry for this letter, then there cannot be any words in the trie for the passed in prefix.
		    if (!childrens.containsKey(aChar)) {
		      return words;
		    }
		    crawl = childrens.get(aChar);
		  }
		 
		  //At this point, we can be sure that there are words in the trie corresponding to the passed in prefix. Now plow down 
		  //the depths of the tree and form all the words corresponding to the prefix.
		  return walkDown(crawl, prefix, words);
	}
	
	/**
	 * @param node
	 * @param prefix
	 * @param words
	 * @return
	 */
	public List<String> walk(Node node, String prefix, List<String> words) {
		  //A word can be formed at this point, hence add it to the container.
		  if (node.checkValid()) {
		    words.add(prefix);
		     
		    //This branch of the tree ends here as there are no more children. Hence return.
		    if (node.getChildren().size() == 0) {
		      return words;
		    }
		  }
		  
		  //Recursively go through all the children of the node and form words.
		  for (Map.Entry<Character, Node> entry : node.getChildren().entrySet()) {
		    Node _node = entry.getValue();
		    walkDown(entry.getValue(), prefix + _node.getData(), words);
		  }
		 
		  return words;
	}
		
	 public String alphabetize(String s) {
	        char[] a = s.toCharArray();
	        Arrays.sort(a);
	        return new String(a);
	 }
	 
	 /**
	 * @param root
	 * @return
	 */
	public int height(Node root){
			
			if(null==root)
			return -1;
			Node crawl=root;
			Queue<Node> queue=new LinkedList<Node>();
			int count=-1;
			queue.offer(crawl);
			queue.offer(null);
			count++;
			while(!queue.isEmpty()){
				Node temp=queue.poll();
				if(null==temp){
					//System.out.println("\n");
					if(!queue.isEmpty()){
						count=count+1;
						queue.offer(null);
					}
				} else {
					HashMap<Character, Node> childrens=temp.getChildren();
					for(Node node:childrens.values()){
						queue.offer(node);
					}
				}
				
			}
			
			return count;
			
	}
	
	/**
	 * @param root
	 */
	public int width(Node root){
		
		if(null==root)
		return -1;
		Node crawl=root;
		Queue<Node> q=new LinkedList<Node>();
		int cur = 0,width=-1;
		q.offer(crawl);
		q.offer(null);
		while(!q.isEmpty()){
			Node t=q.poll();
			if(null==t){
				if(width<cur)
					width=cur;
				cur=0;
				if(!q.isEmpty()){
					q.offer(null);
				}
			}
			else {	
				cur++;
				HashMap<Character, Node> childrens=t.getChildren();
				for(Node node:childrens.values()){
					q.offer(node);
				}
			}
		}
		
		return width;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		//System.out.println("Debuf" +root.getData());
		return getRoot().getChildren().toString();
	}

}
/**
 * @author shashank_local
 *
 */
public class TrieTest {

	//private static final Logger LOG = LoggerFactory.getLogger(TrieTest.class);
	
	public static void main(String[] args)  {
		Trie dict = new Trie();
		
		Trie dict1 = new Trie();
		dict1.insert("banana");
		dict1.insert("anana");
		dict1.insert("nana");
		dict1.insert("ana");
		dict1.insert("na");
		dict1.insert("a");
		
		dict1.suffixSearch(dict1.getRoot(), "na");
		
		File f=new File("C:/Users/shashank/Desktop/Dictionary.txt");
		
		try(Scanner s = new Scanner(f))
		{
			while (s.hasNext()) {
	            String word = s.next();
	            String temp=word;// better then using indexes as integer always take 4 byte but in this depends on length of 
	            word=dict.alphabetize(word);
				dict.insert(word,temp);
	        }
		}
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		Set<Set<String>> anagrams=new  HashSet<Set<String>>();
		long start=System.currentTimeMillis();
		dict.groupAnagrams(dict.getRoot(), anagrams);
        long end=System.currentTimeMillis();
        
        // System.out.println(anagrams.toString());
        /*are aer cat tac act ted tde bet */
        
		System.out.println("Group By Anagrams , Time Taken = " + (end-start));
		for(Set<String> leafSet: anagrams){
			System.out.print("Group of size " + leafSet.size() + "[");
			for(String leaf:leafSet){
				System.out.print(leaf + " ");
			}
			System.out.println("]");
		}
        
		System.out.println("Height Of Trie " + dict.height(dict.getRoot()));
		System.out.println("Width of Trie " + dict.width(dict.getRoot())); // need to check 
		
		Set<Integer> indexes= new HashSet<Integer>();
		System.out.println("indexes" + indexes.size());
		dict1.suffixTrie(dict1.getRoot(), -1, 0, "ee", indexes);
		for(Integer value:indexes){
			System.out.println(value);
		}


        dict.insert("are");
        dict.insert("aer");
        dict.insert("arent");
        dict.insert("cat");
        dict.insert("act");
        dict.insert("tac");
        dict.insert("ted");
        dict.insert("tde");
        dict.insert("bet");      
        dict.insert("branc");
        dict.insert("beach");
        
        dict.insert("haw are you shashank , where do you stay ? i stay at PA");
        dict.insert("hbw are you vinit");
        dict.insert("hcw are you pramod");
        dict.insert("hdw are you vinit");
        dict.insert("hew are you pramod");
        dict.insert("hfw are you vinit");
        dict.insert("hgw are you pramod");
        dict.insert("hhw are you vinit");
        dict.insert("hiw are you vinit");
        dict.insert("hjw are you pramod");
        dict.insert("hkw are you vinit");
        dict.insert("hlw are you pramod");
        dict.insert("hmw are you vinit");
        dict.insert("hnw are you pramod");
        dict.insert("how are you vinit");
        dict.insert("hpw are you pramod");
        dict.insert("hqw are you vinit");
        dict.insert("hrw are you pramod");
        dict.insert("hsw are you vinit");
        dict.insert("htw are you pramod");
        dict.insert("huw are you vinit");
        dict.insert("hvw are you pramod");
        dict.insert("hww are you vinit");
        dict.insert("hxw are you pramod");
        dict.insert("hyw are you vinit");
        dict.insert("hzw are you pramod");
        dict.insert("h w are you pramod");
        dict.insert("h*w are you vinit");
        dict.insert("h)w are you pramod");
        dict.insert("h&w are you vinit");
        dict.insert("h(w are you pramod");
        
        List<String> childrens=new  LinkedList<String>();
        System.out.println("Whole Trie Using DFS " + dict.printTrieUsingDFS(dict.getRoot(), "",childrens));
        System.out.println("BFS of Trie " + dict.printTrieBFS(dict.getRoot(), " ").toString());

        String input1="how "; // regex
        System.out.println("AutoSuggestion based on : " + input1 + " " + dict.autoSuggest(input1)); 
        
        String input = "cat";
        System.out.print(input + ":   ");
        System.out.println(dict.search(input));       
       
        input = "basem";
        System.out.print(input + ":   ");
        System.out.println(dict.getLongestMatchingPrefix(input));                      
         
        input = "arex";
        System.out.print(input + ":   ");
        System.out.println(dict.getLongestMatchingPrefix(input));              
 
        input = "cate";
        System.out.print(input + ":   ");
        System.out.println(dict.getLongestMatchingPrefix(input)); 
 
	}
}
