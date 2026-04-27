package codeviber.ds;

public class LinkedList {

   int value;
   LinkedList next;
   public LinkedList(int value) {
       this.value = value;
   }
   public void setValue(int value) {
       this.value = value;
   }

    public LinkedList getNext() {
        return next;
    }
    public void setNext(LinkedList next) {
       this.next = next;
    }
    public int getValue() {
        return value;
    }
}
