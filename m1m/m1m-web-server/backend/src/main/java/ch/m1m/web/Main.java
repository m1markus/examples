package ch.m1m.web;

import ch.m1m.web.domain.LinkItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String... args) {

        LinkItem item = new LinkItem("my", "toto", "desc toto is nothing");

        List<LinkItem> allLinks = new ArrayList<>();

        allLinks.add(item);

        /*

        allLinks.add(new LinkItem("http://www.ibm.com", null, null));
        allLinks.add(new LinkItem("http:/www.20min.ch", null, null));
        allLinks.add(new LinkItem("http:/www.blick.ch", null, null));
        */

        System.out.println("### end ###");
    }
}
