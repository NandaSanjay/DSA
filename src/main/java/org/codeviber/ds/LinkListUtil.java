package org.example;

import org.example.ds.LinkedList;

public class LinkListUtil {
    public static LinkedList addTwoNumbers(LinkedList list1, LinkedList list2) {
        LinkedList rev1Node = reverseLinkList(list1);
        LinkedList rev2Node = reverseLinkList(list2);

        LinkedList resultList = new LinkedList(0);
        LinkedList resultListHead = resultList;
        int posCarry = 0; int posModulo = 0; int posNumber = 0;

        while (rev1Node != null && rev2Node!= null ) {

            posNumber =  rev1Node.getValue() + rev2Node.getValue();

            posModulo = posNumber % 10;

            resultList.setNext(new LinkedList(posModulo + posCarry));
            resultList = resultList.getNext();

            posCarry = posNumber / 10;
            rev1Node = rev1Node.getNext();
            rev2Node = rev2Node.getNext();
        }
        if (rev1Node != null) {
            int localPosCarry = posCarry;
            while (rev1Node.getNext() != null ) {
                posNumber = rev1Node.getValue() + localPosCarry;

                resultList.setNext(new LinkedList(posNumber % 10));
                resultList = resultList.getNext();

                localPosCarry = posNumber / 10;
            }

        } else if (rev2Node != null) {
            int localPosCarry2 = posCarry;
            while (rev2Node.getNext() != null) {
                posNumber = rev2Node.getValue() + localPosCarry2;

                resultList.setNext(new LinkedList(posNumber % 10));
                resultList = resultList.getNext();

                localPosCarry2 = posNumber / 10;
            }
        }
        return reverseLinkList(resultListHead.getNext());


    }

    private static LinkedList reverseLinkList(LinkedList head) {
        LinkedList current = head;
        LinkedList prrev = null;
        LinkedList next = null;

        while (current != null) {

            next = current.getNext();
            current.setNext(prrev);
            prrev = current;
            current = next;

        }

        return prrev;
    }

    public static void main(String[] args) {
        LinkedList list1 = new LinkedList(1);
        list1.setNext(new LinkedList(5));
        list1.getNext().setNext(new LinkedList(4));
        LinkedList list2 = new LinkedList(2);
        list2.setNext(new LinkedList(9));

        printList(list1);
        // printList(reverseLinkList(list1));

        printList(list2);
        // printList(reverseLinkList(list2));

        LinkedList result = addTwoNumbers(list1, list2);
        printList(result);


    }

    public static void printList(LinkedList node){
        while (node != null){
            System.out.print(node.getValue() + " - > ");
            node = node.getNext();
        }
        System.out.println( " NULL ");

    }
}
