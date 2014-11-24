/*
 * The AddressCache has a max age for the elements it's storing, an add method 
 * for adding elements, a remove method for removing, a peek method which 
 * returns the most recently added element, and a take method which removes 
 * and returns the most recently added element.
 * All operations are O(1) 
 *
 */
/*Note :
Asssuming InetAddress is userDefine class and hashcode and equals are overrided in that class 
Also Doen't allow NUll Objects
MaxAge is no of address cache can hold at any time 
*/

import java.util.HashMap;

class InetAddress {
	Integer val;
	InetAddress(Integer val)
	{
		this.val=val;
	}
	Integer getValue()
	{
		return val;
	}
}
class TimeUnit
{
	
	
}
class AddressCache {
	private long maxAge=0;
	private TimeUnit unit=null;
	DoublyLinkedList dll=null;
	HashMap<InetAddress,InetAddress> cache=null;
	private int count=0;
	public AddressCache(long maxAge, TimeUnit unit) {
			cache=new HashMap<InetAddress,InetAddress>((int)maxAge);
			dll=new DoublyLinkedList();
			this.maxAge=maxAge;
			this.unit=unit;
			
	}
	/**
	 * add() method must store unique elements only (existing elements must be ignored). 
	 * This will return true if the element was successfully added. 
	 * @param address
	 * @return
	 */
	 
	public boolean add(InetAddress address) {
		if(count!=maxAge)
		{
			if(cache.containsKey(address))
			{
				cache.put(address,address);//always update with new value
				if(dll.find(address)!=null)
				{
					dll.update(address);
				}
				
			}
			else
			{
				cache.put(address,address); //add new value 
				dll.insertFirst(address); // also add this to linked list at head which make sure most recent item always added to head
			}
			count++;
			return true;
		}
		else if(count==maxAge)//if  cache full , remove 
		{
			InetAddress thisAddress = dll.deleteFirst(); // //remove from list, First always be the most recent element
			cache.remove(thisAddress);; //remove from map as well
			
			cache.put(address,address); //add new value in list and map  
			dll.insertFirst(address);
			
			return true;
		}
		
		return false;
		
	}

	/**
	 * remove() method will return true if the address was successfully removed
	 * @param address
	 * @return
	 */
	public boolean remove(InetAddress address) {
		if(cache.remove(address)!=null && dll.deleteKey(address)!=null)
		{	
			count--;
			return true;
		}
				return false;
	}

	/**
	 * The peek() method will return the most recently added element, 
	 * null if no element exists.
	 * @return
	 */
	public InetAddress peek() {
		return dll.getFirst().iData; //always most recent , because most recent is added to head always 
	}

	/**
	 * take() method retrieves and removes the most recently added element 
	 * from the cache and waits if necessary until an element becomes available.
	 * @return
	 */
	public InetAddress take() {
		InetAddress address=dll.getFirst().iData;
		dll.deleteFirst(); //remove from list, First always be the most recent element
		cache.remove(address); // remover from cache 
		count--;
		return address;
	}
	
}


class Link {
  public InetAddress iData;
  public Link next;
  public Link previous;

  public Link(InetAddress id) {
    iData = id;
  }

  public String toString() {
    return "{" + iData + "} ";
  }
}

class DoublyLinkedList {
  private Link first;
  private Link last;
  public DoublyLinkedList() {
    first = null;
    last = null;
  }

  public boolean isEmpty() {
    return first == null;
  }
  
  public Link getFirst()
  {
	return first;
  }
  
  public Link getLast()
  {
	return last;
  }

  public void insertFirst(InetAddress dd) {
    Link newLink = new Link(dd);
    if (isEmpty()){
      last = newLink;
    }else{
      first.previous = newLink;
    }
    newLink.next = first;
    first = newLink;
  }
  
  public InetAddress deleteFirst() {
    Link temp = first;
    if (first.next == null){
      last = null;
    }else{
      first.next.previous = null;
    }
    first = first.next;
    return temp.iData;
  }

  public void insertLast(InetAddress dd) {
    Link newLink = new Link(dd);
    if (isEmpty()){
      first = newLink;
    }else {
      last.next = newLink;
      newLink.previous = last;
    }
    last = newLink;
  }
  
  public InetAddress deleteLast() {
    Link temp = last;
    if (first.next == null){
      first = null;
    }else{
      last.previous.next = null;
    }
    last = last.previous;
    return temp.iData;
  }
  
  public InetAddress deleteKey(InetAddress key) {
    Link current = first;
    while (current.iData != key) {
      current = current.next;
      if (current == null)
        return null;
    }
    if (current == first){
      first = current.next;
    }else{
      current.previous.next = current.next;
    }
    
    if (current == last){
      last = current.previous;
    }else{
      current.next.previous = current.previous;
    }
    return current.iData;
  }
  
  public InetAddress find(InetAddress iData)
  {
	Link current = first;
    while (current!=null) {
      if(current.iData==iData)
	  return current.iData;
	  current=current.next;
    }
	return null; // if not found
  }

  public void update(InetAddress iData)
  {
	Link current = first;
    while (current!=null) {
      if(current.iData==iData)
		current.iData=iData;
	  current=current.next;
    }
	
  }
  public String toString() {
    String str = "List (first-->last): ";
    Link current = first;
    while (current != null) {
      str += current.toString();
      current = current.next;
    }
    return str;
  }
}

class MRUCache
{
	
	public static void main (String[] args)
	{
		AddressCache cache=new AddressCache(3,null);
		InetAddress a=new InetAddress(1);
		InetAddress b=new InetAddress(2);
		InetAddress c=new InetAddress(3);
		InetAddress d=new InetAddress(4);
		InetAddress e=new InetAddress(5);
		
		cache.add(a);
		cache.add(b);
		cache.add(c);
		cache.add(d);
		cache.add(e);
		
		System.out.println(cache.peek().getValue());
		System.out.println(cache.remove(b));
		System.out.println(cache.take().getValue());
		System.out.println(cache.peek().getValue());
		
		InetAddress d1=new InetAddress(4);
		InetAddress e1=new InetAddress(5);
		
		cache.add(b);
		c=new InetAddress(33);
		cache.add(c);
		
		System.out.println(cache.peek().getValue());
		System.out.println(cache.take().getValue());
		System.out.println(cache.peek().getValue());
	}
	
}

Run Here : http://ideone.com/cWNIRx ( Drop me to run this link)
