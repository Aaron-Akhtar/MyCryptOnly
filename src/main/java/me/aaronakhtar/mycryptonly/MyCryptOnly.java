package me.aaronakhtar.mycryptonly;

/***
 * Developed by Aaron Akhtar, for Aaron Akhtar...
 */
public class MyCryptOnly {
    protected static volatile boolean running = true;

    protected static void execWelcomeMsg(){
        for (int x = 0; x < 100; x++){
            System.out.println();
        }
        System.out.println(App.name.getVal() + " Version ["+App.version.getVal()+"]");
        System.out.println("Developed by Aaron Akhtar, for Aaron Akhtar...");
        System.out.println("dec, enc, encstr, decstr");
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println();
        execWelcomeMsg();
        Input.input();
    }

}
