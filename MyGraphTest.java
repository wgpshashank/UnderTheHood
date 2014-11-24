import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * @author shashank_local
 * 
 */
class MyVertex implements Comparable<MyVertex> {
	  final private String id;
	  final private String name;
	  private boolean visited;
	  public MyVertex(String id, String name,boolean visited) {
	    this.id = id;
	    this.name = name;
	    this.visited=visited;
	  }
	  public String getId() {
	    return id;
	  }
	 
	  public String getName() {
	    return name;
	  }
	 
	  public boolean isVisited(){
		  return visited;
	  }
	  
	  public void setVisited(boolean visited){
		  this.visited=visited;
	  }
	  
	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	    MyVertex other = (MyVertex) obj;
	    if (id == null) {
	      if (other.id != null)
	        return false;
	    } else if (!id.equals(other.id))
	      return false;
	    return true;
	  }
	  
	  @Override
	  public int compareTo(MyVertex that){
		  	if(null==that) return -1;
			if(this.getId().compareTo(that.getId())<0) return -1;
			else if (this.getId().compareTo(that.getId())>0) return 1;
			else return 0;
	  }
	 
	  @Override
	  public String toString() {
	    return name;
	  }
	 
} 

class Edge  {
      private final String id; 
      private final MyVertex source; 
      private final MyVertex destination;
      private final int weight; 
     
      public Edge(String id, MyVertex source, MyVertex destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
      }
     
      public String getId() {
        return id;
      }
      public MyVertex getDestination() {
        return destination;
      }
     
      public MyVertex getSource() {
        return source;
      }
      public int getWeight() {
        return weight;
      }
     
      @Override
      public String toString() {
        return source + " " + destination;
      }
 
}
 
interface MyGraphInterface {
	public List<MyVertex> dfs(MyVertex vertex);
	public List<MyVertex> bfs(MyVertex vertex);
	
}
class MyGraph implements MyGraphInterface,Serializable
{
	private static final long serialVersionUID = 1L;
	/* Makes use of Map collection to store the adjacency list for each MyVertex.*/
    private  Map<MyVertex, LinkedList<MyVertex>> Adjacency_List;	
    final private int number_of_vertices;
    /**
     * @param number_of_vertices
     */
    public MyGraph(int number_of_vertices) {
        ////choose TreeMpa since it has O(logn) search , add , remove 
        Adjacency_List = new HashMap<MyVertex, LinkedList<MyVertex>>(number_of_vertices); 
        this.number_of_vertices=number_of_vertices;
    }
 
    /* Adds nodes in the Adjacency list for the corresponding MyVertex */
    /**
     * @param source
     * @param destination
     */
    public void setEdge(MyVertex source , MyVertex destination) {
    	LinkedList<MyVertex> slist = Adjacency_List.get(source);
    	if(null==slist){
    		Adjacency_List.put(source, slist= new LinkedList<MyVertex>());
    	}
    	slist.add(destination);
        //LinkedList<MyVertex> dlist = Adjacency_List.get(destination);
        //dlist.add(source);
   }
   
   /* Returns the List containing the MyVertex joining the source MyVertex */		
    /**
     * @param source
     * @return
     */
    public List<MyVertex> getEdge(MyVertex source){
        return Adjacency_List.get(source);
    }
    
    /* (non-Javadoc)
     * @see MyGrapInterface#bfs(MyVertex)
     */
    public List<MyVertex> bfs(MyVertex node){
    	List<MyVertex> MyVertexs= new ArrayList<MyVertex>(); 
        //BFS uses Queue data structure
        Queue<MyVertex> q=new LinkedList<MyVertex>();
        q.add(node);
        MyVertexs.add(node);
        node.setVisited(true);
        while(!q.isEmpty())
        {
            MyVertex n=q.poll();
            LinkedList<MyVertex> childs=Adjacency_List.get(n);
            if(null!=childs){
	            Iterator<MyVertex> iterator= childs.iterator();
	            while(iterator.hasNext())
	            {
	                MyVertex child=iterator.next();
	                if(!child.isVisited()){
		                MyVertexs.add(child);
		                child.setVisited(true);
		                q.add(child);
	                }
	            }
            }
            //MyVertexs.add(null); // New line 
            
        }
		return MyVertexs;
    }

    //DFS traversal of a tree is performed by the dfs() function
    /* (non-Javadoc)
     * @see MyGrapInterface#dfs(MyVertex)
     */
    public List<MyVertex> dfs(MyVertex MyVertex){
    	List<MyVertex> MyVertexs= new ArrayList<MyVertex>(); 
    	//DFS uses Stack data structure
        Stack<MyVertex> s=new Stack<MyVertex>();
        s.push(MyVertex);
        MyVertex.setVisited(true);
        MyVertexs.add(MyVertex);
        while(!s.isEmpty())
        {
            MyVertex  n=s.peek();
            LinkedList<MyVertex> childs=Adjacency_List.get(n);
            if(null!=childs){
	            java.util.Iterator<MyVertex> iterator= childs.iterator();
	            if(iterator.hasNext())
	            {
	            	MyVertex c=iterator.next();
	            	if(!c.isVisited())
	            	{
	            		MyVertexs.add(c);
	            		c.setVisited(true);
	            		s.push(c);
	            	}
	           }
	           else
	           {
	             s.pop();
	           }
           }
       }
		return MyVertexs;
   }
    
	   /**
	 * @param vertex
	 * @param visited
	 * @param stack
	 */
    public void dfsRecursive(MyVertex vertex , boolean visited [] , Stack<MyVertex> stack){
	   visited[Integer.parseInt(vertex.getId())]=true;
	   LinkedList<MyVertex> list=Adjacency_List.get(vertex);
	   if(null!=list){
		   for(MyVertex myVertex:list){
			   if(!visited[Integer.parseInt(myVertex.getId())]){
				   dfsRecursive(myVertex, visited, stack);
			   }
		   }
	   }
	   stack.push(vertex);
   }
   
	   /**
	 * @param g
	 */
	public void toplogicalSort(MyGraph g){
	   boolean visited[]=new boolean[number_of_vertices];
	   Stack<MyVertex> stack= new Stack<MyVertex>();
	   Set<MyVertex> set=Adjacency_List.keySet();
	   for(MyVertex myVertex:set){
		   if(visited[Integer.parseInt(myVertex.getId())]==false)
			   dfsRecursive(myVertex, visited, stack);
	   }
	   
	   while(!stack.isEmpty()){
		   System.out.println(stack.pop().getName());
	   }
   }
   
   /**
    * 
    */
	public void clear(){
	   Adjacency_List.clear();
   }
   
}

public class MyGraphTest {
	
	/*
	Given a sorted dictionary (array of words) of an alien language, find order of characters in the language.
    Examples:
    Input:  words[] = {"baa", "abcd", "abca", "cab", "cad"}
    Output: Order of characters is 'b', 'd', 'a', 'c'
    Note that words are sorted and in the given language "baa" 
    comes before "abcd", therefore 'b' is before 'a' in output.
    Similarly we can find other orders.
	*/
	
	/**
	 * @param g
	 * @param words
	 * @param n
	 * @param alpha
	 */
	private static void printAlinesCharacterOrder(MyGraph g , String words[], int n, int alpha){
	       // Process all adjacent pairs of words and create a graph
	       for (int i = 0; i < n-1; i++)
	       {
	           // Take the current two words and find the first mismatching
	           // character
	           String word1 = words[i], word2 = words[i+1];
	           for (int j = 0; j < Math.min(word1.length(), word2.length()); j++)
	           {
	               // If we find a mismatching character, then add an edge
	               // from character of word1 to that of word2
	               if (word1.charAt(j)!= word2.charAt(j))
	               {	
	            	   String id1, name1;
	            	   id1=name1=new String(word1.charAt(j)-'a'+"");
	            	   String id2, name2;
	            	   id2=name2=new String(word2.charAt(j)-'a'+"");
	            	   
	            	   MyVertex src= new MyVertex(id1, name1, false);
	            	   MyVertex dest= new MyVertex(id2, name2, false);
	                   g.setEdge(src,dest);
	                   break;
	               }
	           }
	       }
	    
	       // Print topological sort of the above created graph
	      System.out.println("Topological sorting of given graph is");
	      g.toplogicalSort(g);
	   }
	
    /*
     * Main Function reads the number of vertices and edges in a graph.
     * then creates a Adjacency List for the graph and prints it  
     */
     /**
     * @param arg
     */
    public static void main(String arg[]) {
         int source , destination;
         int number_of_edges,number_of_vertices=4;
         int count = 1;
         boolean visitied[]= new boolean[number_of_vertices];
         
         MyGraph g = new MyGraph(number_of_vertices);
         
         MyVertex v0= new MyVertex("0" , "zero", false);
         MyVertex v1= new MyVertex("1" , "one", false);
         MyVertex v2= new MyVertex("2" , "two", false);
         MyVertex v3= new MyVertex("3" , "three", false);
         
         g.setEdge(v1, v0); // DAG
         g.setEdge(v3, v0);
    	 g.setEdge(v0, v2);
    	 g.setEdge(v1, v3);
    	 g.toplogicalSort(g);
    	 
    	 g.clear();

    	 String words[] = {"baa", "abcd", "abca", "cab", "cad"}; 
    	 //"caa", "aaa", "aab" // check failed test case {"aba", "bba", "aaa"};//
    	  
    	 printAlinesCharacterOrder(g, words, words.length, number_of_vertices);
    	   
        // g.setEdge(v0, v1);
        // g.setEdge(v0, v2);
        // g.setEdge(v1, v2);
        // g.setEdge(v1, v3);
        // g.setEdge(v2, v3);
        //graph.setEdge(v4, v2);
         
        // System.out.println(g.bfs(v1).toString());
        // System.out.println(g.dfs(v0).toString()); // Need to check 
     }
}
