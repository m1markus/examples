package ch.m1m.ldap;

import java.util.ArrayList;
import java.util.Vector;

public class MemLimit {

    public static void main(String... args) {
        int count = 0;

        System.out.println("running...");

        // System.out.println(String.format("|%30.5s|", "Hello World"));
        // System.exit(9);

        System.out.println("int max: " + Integer.MAX_VALUE);

        ArrayList<String> liStrings = new ArrayList<>();

        while ( true ) {

            count++;

            System.out.println("loop count: " + count);

            int stringSize = Integer.MAX_VALUE / 8;
            System.out.println("try to allocate string of size: " + stringSize);

            liStrings.add(createDataSize(stringSize));

            if (liStrings.size() > Integer.MAX_VALUE) {
                break;
            }
        }

        System.out.println("### end ###");
    }

    private static String createDataSize(int msgSize) {
        StringBuilder sb = new StringBuilder(msgSize);
        for (int i=0; i<msgSize; i++) {
            sb.append('a');
        }
        return sb.toString();
    }



}
