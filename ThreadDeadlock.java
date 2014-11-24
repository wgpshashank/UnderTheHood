A deadlock is a situation where minimum two threads are holding lock on some different resource, and both are waiting 
for other’s resource to complete its task. And, none is able to leave the lock on resource it is holding. 

package ProducerConsumerProblem;


public class ThreadDeadlock {
 
    public static void main(String[] args) throws InterruptedException {
        Object resources1 = new Object();
        Object resources2 = new Object();
        Object resources3 = new Object();
     
        Thread t1 = new Thread(new SyncThread(resources1, resources2), "t1");
        Thread t2 = new Thread(new SyncThread(resources2, resources3), "t2");
        Thread t3 = new Thread(new SyncThread(resources3, resources1), "t3");
         
        t1.start();
        Thread.sleep(5000);
        t2.start();
        Thread.sleep(5000);
        t3.start();
         
    }
 
}
 
class SyncThread implements Runnable{
    private Object obj1;
    private Object obj2;
 
    public SyncThread(Object o1, Object o2){
        this.obj1=o1;
        this.obj2=o2;
    }
    @Override
     public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " acquiring lock on "+obj1);
        synchronized (obj1) {
         System.out.println(name + " acquired lock on "+obj1);
         work();
         System.out.println(name + " acquiring lock on "+obj2);
         synchronized (obj2) {
            System.out.println(name + " acquired lock on "+obj2);
            work();
        }
         System.out.println(name + " released lock on "+obj2);
        }
        System.out.println(name + " released lock on "+obj1);
        System.out.println(name + " finished execution.");
    } 
    private void work() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

In above program SyncThread is implementing Runnable interface and it works on two Objects by acquiring lock on each 
one of them one by one using synchronized block.In main method, I have three threads running for SyncThread and there
is a shared resource between each of the threads. The threads are run in such a way that it will be able to acquire 
lock on the first object but when it’s trying to acquire lock on second object, it goes on wait state because it’s 
already locked by another thread. This forms a cyclic dependency for resource between Threads causing deadlock.When I 
execute the above program, here is the output generated but program never terminates because of deadlock.


t1 acquiring lock on java.lang.Object@6d9dd520
t1 acquired lock on java.lang.Object@6d9dd520
t2 acquiring lock on java.lang.Object@22aed3a5
t2 acquired lock on java.lang.Object@22aed3a5
t3 acquiring lock on java.lang.Object@218c2661
t3 acquired lock on java.lang.Object@218c2661
t1 acquiring lock on java.lang.Object@22aed3a5
t2 acquiring lock on java.lang.Object@218c2661
t3 acquiring lock on java.lang.Object@6d9dd520

Deadlock occusr because of nested locks , thread trying acquires a lock on object which was already used , we can Avoid 
the deadlock above if we change  the implementation of run() method without nested lock.

public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " acquiring lock on " + obj1);
        synchronized (obj1) {
            System.out.println(name + " acquired lock on " + obj1);
            work();
        }
        System.out.println(name + " released lock on " + obj1);
        System.out.println(name + " acquiring lock on " + obj2);
        synchronized (obj2) {
            System.out.println(name + " acquired lock on " + obj2);
            work();
        }
        System.out.println(name + " released lock on " + obj2);
     
        System.out.println(name + " finished execution.");
    }
    
    
Run Here http://ideone.com/f1sEkj You will get TLE here because of ideone has specific time limit , run it on your own 
system ,it will work without deadlock
