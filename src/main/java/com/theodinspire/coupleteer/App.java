package com.theodinspire.coupleteer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App 
{
    public static void main( String[] args ) {
        Coupleteer coupleteer = Coupleteer.getInstance();
        
        int coupletsToPrint = -1;
        
        if (args.length > 0) {
            Integer toPrint = Integer.parseInt(args[0]);
            if (toPrint != null) coupletsToPrint = toPrint;
        }
        
        if (coupletsToPrint > 0) {
            for (int i = 0; i < coupletsToPrint; ++i) {
                Couplet couplet = coupleteer.getCouplet();
                System.out.println(couplet);
            }
        } else {
            try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Press \"Enter\" to continue, type \"Quit\" to exit");
                System.out.println();
    
                do {
                    System.out.println(coupleteer.getCouplet());
                    System.out.println();
                } while (console.readLine().equalsIgnoreCase(""));
            } catch (Exception e) {
                //  Fail mostly gracefully
                System.out.println("Could not get user input");
                System.exit(-1);
            }
        }
    }
}
