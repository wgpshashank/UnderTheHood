Problem Statement : Print the objects of an array in the decreasing frequency ,Please make sure order of object should remain same .

package test;
 
//import com.google.common.collect.Maps;
//import com.google.common.collect.Ordering;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
class SortByFrequency {
  
    class FreqIndexPairs {
        public Integer Frequency  ;
            public Integer FirstIndex ;
           
            public FreqIndexPairs() {
                        
              Frequency=0;
                FirstIndex=0;
                }
        }
 
        // Define inside comparator how you wanna sort
        public static Comparator<FreqIndexPairs> myComparator = new Comparator<FreqIndexPairs>() {
 
        public int compare(FreqIndexPairs f1, FreqIndexPairs f2) {
                if(f1.Frequency.equals(f2.Frequency)){
                        
                        
                        return f1.FirstIndex.compareTo(f2.FirstIndex);
                }
                
        return f2.Frequency.compareTo(f1.Frequency);
 
      }
 
    };
        
    public static void main(String args[]) {
  
        SortByFrequency mapSortingExample= new SortByFrequency();      
        Integer ar[]=new Integer[]{1,6,5,2,2,8,5,6,8,8};
        Map<Integer, FreqIndexPairs> sort = new HashMap<Integer, FreqIndexPairs>(); // O(n) Space
        final Integer one=new Integer(1);
        int k=0;
        
        for(int i=0;i<ar.length;i++) //O(n) time
        {
            FreqIndexPairs freqIndexPairs;
            if (!sort.containsKey(ar[i]))
            {
                freqIndexPairs=mapSortingExample.new FreqIndexPairs();
                freqIndexPairs.FirstIndex=i; freqIndexPairs.Frequency=one;
                sort.put(ar[i], freqIndexPairs); 
            }
            else 
            {   
                freqIndexPairs=sort.get(ar[i]);
                freqIndexPairs.Frequency=freqIndexPairs.Frequency+one;
                sort.put(ar[i], freqIndexPairs);
             }
                
                //System.out.println("******** " + ar[i] + " ******");
        }
        /* 0 2    3 3
           1 2    0 2
           3 3    1 2
           5 1    5 1 */
        /***********  Print HashMap Content
        System.out.println("##################"); 
        Set<Integer> list=sort.keySet();
        Iterator<Integer> iterator=list.iterator();
        while(iterator.hasNext())
        {       Integer key=iterator.next();
            FreqIndexPairs pairs=sort.get(key);
                System.out.println(key + " : " + pairs.FirstIndex + " ** " + pairs.Freq); 
        }
        System.out.println("##################");  */
 
        List<FreqIndexPairs> list = new ArrayList<FreqIndexPairs>(sort.values());// O(n) Auxallery Space
        Collections.sort(list, myComparator); // O(nlon) Quick Sort/Heap Sort
 
        int[] newArray = new int[ar.length];
        int counter = 0;
        for (int j = 0; j < list.size(); j++) // Overall O(n)  , worst case when all elements are same 
        {   								  // outer loop will execute 1 time
            for (k = 0; k < list.get(j).Frequency;k++) //then it will execute n times , so overall complexity O(n)
            {
                newArray[counter] = ar[list.get(j).FirstIndex];
                counter++;
            }
        }
        
        for (int i = 0; i < newArray.length; i++)
        {
            ar[i] = newArray[i];
        }
 
        for (int i = 0; i < ar.length; i++)
        {
            System.out.println(ar[i]);
        }
    }
}

/********************************************************
Time Complexity :  O(n)+O(nlogn)+O(n)=O(nlogn)
Space Complexity : Auxallery Space O(2n)= O(n)
*********************************************************/
