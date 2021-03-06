Algorithm:

Best first search algorithm (A*)
* declare priorityQueue
* add root node to our priorityQueue
* while priorityQueue not empty do following loops:
* a. retrieve then remove first node of our openlist
* b. check status of retrieved node
*      if it is the goal node then break loop and print solution
*      if it is not goal node then:
*      – expand retrieved node
*      – evaluaate using mismatch heuristik
*      – add to priorityQueue
*      – continue loop


// Breadth First Search Usage in the common Eight Puzzle Problem.
import java.util.*;

class EightPuzzle {
    
    Queue<String> q = new LinkedList<String>();    // Use of Queue Implemented using LinkedList for Storing All the Nodes in BFS.
    Map<String,Integer> map = new HashMap<String, Integer>(); // HashMap is used to ignore repeated nodes
    
    public static void main(String args[]){
        
        
        String str="087465132";                 // Input the Board State as a String with 0 as the Blank Space 

        EightPuzzle e = new EightPuzzle();      // New Instance of the EightPuzzle
        e.add(str,0);                           // Add the Initial State
        
        while(e.q.peek()!=null){
            
            e.up(e.q.peek());                   // Move the blank space up and add new state to queue
            e.down(e.q.peek());                 // Move the blank space down 
            e.left(e.q.peek());                 // Move left
            e.right(e.q.remove());              // Move right and remove the current node from Queue
        }
        System.out.println("Solution doesn't exist");
    }
    
    //Add method to add the new string to the Map and Queue
    void add(String str,int n){
        if(!map.containsKey(str)){
            map.put(str,n);
            q.add(str);
        }
    }
    
    /* Each of the Methods below Takes the Current State of Board as String. Then the operation to move the blank space is done if possible.
          After that the new string is added to the map and queue.If it is the Goal State then the Program Terminates.
    */
    void up(String str){
        int a = str.indexOf("0");
        if(a>2){
            String s = str.substring(0,a-3)+"0"+str.substring(a-2,a)+str.charAt(a-3)+str.substring(a+1);
            add(s,map.get(str)+1);
            if(s.equals("123456780")) {
                System.out.println("Solution Exists at Level "+map.get(s)+" of the tree");
                System.exit(0);
            }
        }
    }
    void down(String str){
        int a = str.indexOf("0");
        if(a<6){
            String s = str.substring(0,a)+str.substring(a+3,a+4)+str.substring(a+1,a+3)+"0"+str.substring(a+4);
            add(s,map.get(str)+1);
            if(s.equals("123456780")) {
                System.out.println("Solution Exists at Level "+map.get(s)+" of the tree");
                System.exit(0);
            }
        }
    }
    void left(String str){
        int a = str.indexOf("0");
        if(a!=0 && a!=3 && a!=6){
            String s = str.substring(0,a-1)+"0"+str.charAt(a-1)+str.substring(a+1);
            add(s,map.get(str)+1);
            if(s.equals("123456780")) {
                System.out.println("Solution Exists at Level "+map.get(s)+" of the tree");
                System.exit(0);
            }
        }
    }
    void right(String str){
        int a = str.indexOf("0");
        if(a!=2 && a!=5 && a!=8){
            String s = str.substring(0,a)+str.charAt(a+1)+"0"+str.substring(a+2);
            add(s,map.get(str)+1);
            if(s.equals("123456780")) {
                System.out.println("Solution Exists at Level "+map.get(s)+" of the tree");
                System.exit(0);
            }
        }
    }
}

Mor Info  http://www.d.umn.edu/~jrichar4/8puz.html
http://stackoverflow.com/questions/1395513/what-can-be-the-efficient-approach-to-solve-the-8-puzzle-problem
http://www.hh.se/download/18.70cf2e49129168da0158000143591/1341267670730/aima-ch3.pdf
http://en.wikipedia.org/wiki/15_puzzle
http://en.wikipedia.org/wiki/A*_search_algorithm
Run Here https://compilr.com/lj/8-puzzle/Program.java  or http://ideone.com/bkhZ01

