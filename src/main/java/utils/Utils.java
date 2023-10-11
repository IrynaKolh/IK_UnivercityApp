package utils;

import java.util.List;

public class Utils {
    public static void printExitMsg(){
        System.out.println();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Enter 'Q' for quit or exit");
        System.out.println();
    }

    public static void printList(List<String> list) {
        for (String el: list) {
            System.out.println(el);
        }
    }

}
