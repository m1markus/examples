package ch.m1m.web.repository;

import ch.m1m.web.domain.LinkItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LinkRepository {

    List<LinkItem> allLinks = new ArrayList<>();


    public LinkRepository() {
        allLinks.add(new LinkItem("http://www.ibm.com", null, null));
        allLinks.add(new LinkItem("http:/www.20min.ch", null, null));
        allLinks.add(new LinkItem("http:/www.blick.ch", null, null));
    }

    public List<LinkItem> findAll() {

        return allLinks;
    }
}
