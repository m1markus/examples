package ch.m1m.ldap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;

public class Prime {



    public static void main(String... args) {

        System.out.println("starting...");

        boolean isSupressOutputNoPrime = true;
        boolean isSupressOutputPrime = true;

        int stopAfterNumPrimes = 100000;
        //stopAfterNumPrimes = 1;

        int numPrimesFound = 0;
        int col = 0;

        BigInteger big1 = new BigInteger("1000000000000000000000000000000000000000000000000000");

        LocalDateTime testBegin = LocalDateTime.now();

        while(true) {

            big1 = big1.add(new BigInteger("1"));


            if(big1.isProbablePrime(100)) {
                numPrimesFound++;
                if (!isSupressOutputPrime) {
                    System.out.println("\nprime " + big1 + " ");
                }
                col = 0;
            } else {
                if (!isSupressOutputNoPrime) {
                    col++;
                    if (col < 80) {
                        System.out.print(".");
                    } else {
                        System.out.println(".");
                        col = 0;
                    }
                }
            }

            if (numPrimesFound >= stopAfterNumPrimes) {
                break;
            }
        }

        LocalDateTime testEnd = LocalDateTime.now();

        Duration duration = Duration.between(testBegin, testEnd);
        System.out.println(duration);

        System.out.println("### end ###");
    }

}

/*

		// Test Duration.between
        System.out.println("\n--- Duration.between --- ");

        LocalDateTime oldDate = LocalDateTime.of(2016, Month.AUGUST, 31, 10, 20, 55);
        LocalDateTime newDate = LocalDateTime.of(2016, Month.NOVEMBER, 9, 10, 21, 56);

        System.out.println(oldDate);
        System.out.println(newDate);

        //count seconds between dates
        Duration duration = Duration.between(oldDate, newDate);

        System.out.println(duration.getSeconds() + " seconds");

 */