package ch.m1m.ldap;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JavaTime {

    public static void main(String... args) {

        DateTimeFormatter myDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss.SSSxxx");



        Instant instant = Instant.now();
        System.out.println("GMT time: " + instant.toString());

        //ZoneId z = ZoneId.of( "America/Montreal" );
        ZoneId zoneGmt = ZoneId.of("GMT");

        ZoneId thisZone = ZoneId.systemDefault();
        ZonedDateTime zdt = instant.atZone(thisZone);
        System.out.println("xxx time: " + zdt.toString());

        String strMy1 = myDateTimeFormatter.format(zdt);
        System.out.println("strMy1:   " + strMy1);

        String strIso = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        System.out.println("ISO time: " + strIso);

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("ldt time: " + ldt.toString());


        System.out.println("now some other things...");

        //Clock clockNowUtc = Clock.systemUTC();
        //Clock clockNowLocal = Clock.systemDefaultZone();

        ZonedDateTime nowLocal = ZonedDateTime.now();
        System.out.println("local  :  " + myDateTimeFormatter.format(nowLocal));

        // convert to GMG / UTC
        ZonedDateTime nowUTC = nowLocal.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println("utc    :  " + myDateTimeFormatter.format(nowUTC));

        ZonedDateTime nowGMT = nowLocal.withZoneSameInstant(zoneGmt);
        System.out.println("gmt    :  " + myDateTimeFormatter.format(nowGMT));
    }
/*
    public static LocalDateTime toZone(final LocalDateTime time, final ZoneId fromZone, final ZoneId toZone) {
        final ZonedDateTime zonedtime = time.atZone(fromZone);
        final ZonedDateTime converted = zonedtime.withZoneSameInstant(toZone);
        return converted.toLocalDateTime();
    }
*/
}
