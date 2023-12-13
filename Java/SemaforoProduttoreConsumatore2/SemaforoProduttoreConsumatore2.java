import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class SemaforoProduttoreConsumatore2{
  public static void main(String[] args) {
    new Producer().start();
    new Consumer("A").start();
    new Consumer("B").start();
  }
}

class Data {
  public static Object LOCK = new Object();
  public static LinkedList list = new LinkedList();
  public static Semaphore sem = new Semaphore(0);
  public static Semaphore mutex = new Semaphore(1);
}

class Consumer extends Thread {
  String name;
  public Consumer(String name) {
    this.name = name;
  }
  public void run() {
    try {
      while (true) {
        Data.sem.acquire(1);
        Data.mutex.acquire();
        System.out.println("Consumer \"" + name + "\" read: " + Data.list.removeFirst());
        Data.mutex.release();
      }
    } catch (Exception x) {
      x.printStackTrace();
    }
  }
}

class Producer extends Thread {
  public void run() {
    try {
      int N = 0;
      while (true) {
        Data.mutex.acquire();
        Data.list.add(new Integer(N++));
        Data.mutex.release();
        Data.sem.release(1);
        Thread.sleep(500);
      }
    } catch (Exception x) {
      x.printStackTrace();
    }
  }
}