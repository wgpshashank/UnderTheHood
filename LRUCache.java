Possible Approaches 

Java Implementations using inb uilt data structure scan be done by using 

1.LinkedHashMap

2. Another Data Structures can be used in multi-threading environment are below but ConcurrentLinkedQueue has slow remove operation due to it uses Singly Linked List instead
Doubly Linked List

A.ConcurrentHashMap
B.ConcurrentLinkedQueue

3.Finally Data structures used here to implement the LRU Cache are below.

1. A ConcurrentHashMap with page number as key and address of the corresponding queue node as value.
When a page is referenced, the required page may be in the memory. 
    Case 1.If page is in the memory, we need to remove the node and bring it to the front of the queue so that it become newly most accessed frame.
	Case 2.If the required page is not in the memory,then bring that in memory e.g we add a new node to the front of the queue and add the corresponding node address in the map.
In both the case we will check if the queue is full, i.e. if all the frames are there in queue what it can hold then we remove a node from the rear of queue, 
and add the new node to the front of queue.

2. A Queue using a doubly linked list( we can traverse in both direction).Least recently pages will be near at rear end and most recently used pages will be 
near at front end of the queue.The maximum size of the queue will be equal to the total number of frames available (cache size).


Key Feature:

ThreadSafe
Scalable 
Concurrency Achieved 
Efficient , All operation are O(1) e.g. Add, remove , search

This may not be perfect but this implementation is very fascinating.
Inside Every Method Proper commenting is done to make sure you understand it better.
 
 */
 
import java.util.concurrent.ConcurrentHashMap;

class Node
{
	int pageNo;
	Node prev;
	Node next;
	public Node(Integer pageNumber) {
		pageNo=pageNumber;
		// TODO Auto-generated constructor stub
		prev=next=null;
	}
	
}

class MyQueue
{
	int count;
	int numberOfFrames;
	Node front;
	Node rear;
	
	MyQueue()
	{
		count=0;
		numberOfFrames=0;
		front=rear=null;
	}
}

class MyCache {
	//Create new node 
	Node newNode(Integer pageNumber )
	{
	   Node temp =new Node(pageNumber);
	   temp.pageNo = pageNumber;
	   temp.prev = temp.next = null;
	     return temp;
	}
	//Initialize Queue 
	MyQueue createQueue(int noOfFrames )
	{
		MyQueue queue = new MyQueue();
	    queue.count = 0;
	    queue.front = queue.rear = null;
	    queue.numberOfFrames = noOfFrames;
	    return queue;
	}	
	// A  utility function to check if queue is full or not
	boolean ifQueueIfFull( MyQueue queue )
	{
	    return queue.count == queue.numberOfFrames;
	}
	 
	// A utility function to check if queue is empty or not
	boolean isQueueEmpty( MyQueue queue )
	{
	    return queue.rear == null;
	}
	
	// A utility function to delete a frame from queue
	synchronized void deQueue(MyQueue queue )
	{
	    if( isQueueEmpty( queue ) )
	        return;
	 
	    // If this is the only node in list, then change front
	    if (queue.front == queue.rear)
	        queue.front = null;
	 
	    // Change rear and remove the previous rear
	    Node temp = queue.rear; //Garbage collection , Memory Leak 
	    queue.rear = queue.rear.prev;
	 
	    if (queue.rear!=null)
	        queue.rear.next = null;
	 
	   // decrement the number of full frames by 1
	    queue.count--; 
	}
	
	// A function to add a page with given 'pageNumber' to both queue
	// and hash
	synchronized void Enqueue( MyQueue queue, ConcurrentHashMap<Integer, Node> map, Integer pageNumber )
	{
	    // If all frames are full, remove the page at the rear
	    if ( ifQueueIfFull ( queue ) )
	    {
	        // remove page from hash
		  	//Node n=null;
	      //map.put(queue.rear.pageNo,n); //Still key,value exist in table (K,null)
	        map.remove(queue.rear);  //Is it O(1) ??????
			deQueue(queue);
	    }
	 
	    // Create a new node with given page number,
	    // And add the new node to the front of queue
	    Node temp = newNode( pageNumber );
	    temp.next = queue.front;
	 
	    // If queue is empty, change both front and rear pointers
	    if ( isQueueEmpty( queue ) )
	        queue.rear = queue.front = temp;
	    else  // Else change the front
	    {
	        queue.front.prev = temp;
	        queue.front = temp;
	    }
	 
	    // Add page entry to hash also
	    map.put(pageNumber,temp);
		
	    // increment number of full frames
	    queue.count++;
	}
	
	// Function is called when a page with given 'pageNumber' is referenced
	// from cache (or memory). There are two cases Case1,2 explained above in explaining data structure.
	synchronized void accessPage( MyQueue queue, ConcurrentHashMap<Integer, Node> map, Integer pageNumber )
	{
	    Node requestedPage =map.get(pageNumber);// hash->array[ pageNumber ];
	 
	    // the page is not in cache, bring it
	    if ( requestedPage == null )
	        Enqueue( queue, map, pageNumber );
	 
	    // page is there and not at front, change pointer
	    else if (requestedPage != queue.front)
	    {
	        // Unlink requested page from its current location
	        // in queue.
	        if(requestedPage.prev!=null)
				requestedPage.prev.next = requestedPage.next;
	        if (requestedPage.next!=null)
	           requestedPage.next.prev = requestedPage.prev;
	 
	        // If the requested page is rear, then change rear
	        // as this node will be moved to front
	        if (requestedPage == queue.rear)
	        {
	           queue.rear = requestedPage.prev; 
	           queue.rear.next = null;
	        }
	 
	        // Put the requested page before current front
	        requestedPage.next = queue.front;
	        requestedPage.prev = null;
	 
	        // Change prev of current front
	        requestedPage.next.prev = requestedPage;
	 
	        // Change front to the requested page
	        queue.front = requestedPage;
	    }
	}
	
		
	void printQueue(MyQueue q)
	{	
		if(q==null)
		return;
			
		for(int i=0;i<q.count;i++)
		{
			 System.out.println(q.front.pageNo);
			 q.front=q.front.next;
		}

	}
	
}

class LRUCache
{
	
	public static void main(String a[])
	{
		MyCache cache=new MyCache();
		MyQueue q = cache.createQueue(3);
	    ConcurrentHashMap<Integer,Node> map= new ConcurrentHashMap<Integer,Node>(2<<4,0.75f,2<<4); // This is a big factor because re-sizing is costly so choose properly 
	 
	    // Let us refer pages 1, 2, 3,4,1,5,5
	    cache.accessPage( q, map, 1);
	    cache.accessPage( q, map, 2);
	    cache.accessPage( q, map, 3);
	    cache.accessPage( q, map, 4);
	    cache.accessPage( q, map, 1);
	    cache.accessPage( q, map, 5);
	    cache.accessPage( q, map, 5);
		
	    /*System.out.println(q.front.pageNo);
	    System.out.println(q.front.next.pageNo);
	    System.out.println(q.front.next.next.pageNo);
	    System.out.println(q.front.next.next.next.pageNo);
		System.out.println(q.front.next.next.next.next.pageNo);*/
		
		cache.printQueue(q);
	}

}

If you have come this far then you might have liked what you've read , so lets discuss about improvemnts.

1. I'm using ConcurrentHashMap which doesn't allow null Key or Value so right now i'm using map.remove method ,which i'm sure not
O(1) how we can make it O(1) , let me know ?  One solution we can use hashMap instead on the cost synchronization #line129 

Note: All operation except this are O(1) in both Data Structure considering good hash fucntion in case of ConcurrentHashMap

2.Object Oriented Designing improvements if you've.

3.What's the best possible size one should provide in real time situation considering re-sizing is very expensive process 
#line217
