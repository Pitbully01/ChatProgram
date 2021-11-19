package de.pitbully;


import java.util.Scanner;

public class warning {
    public static void main(String[] args) throws InterruptedException {
        int grenzwert = (int) userInput();
        if (getToGrenzwert(grenzwert)){
            System.out.println("der Grenzwert wurde erreicht");
        }
    }

    private static boolean getToGrenzwert(int grenzwert) throws InterruptedException {
        for (int i = 0; i <= grenzwert; i++){
            System.out.println("momentaner wert = " + i + "  Grenzwert = " + (float) grenzwert + System.lineSeparator());
            Thread.sleep(500);
        }
        return true;
    }

    private static float userInput() {
        int in;
        System.out.println("Gebe ein Grenzwert ein " + System.lineSeparator());
        Scanner sc = new Scanner(System.in);
        in = sc.nextInt();
        System.out.println("der eingegebene Grenzwert ist " + in + System.lineSeparator());
        return in;
    }
}
