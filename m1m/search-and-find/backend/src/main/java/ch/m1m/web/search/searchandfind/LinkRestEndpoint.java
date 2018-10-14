package ch.m1m.web.search.searchandfind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LinkRestEndpoint {

    private static Logger log = LoggerFactory.getLogger(LinkRestEndpoint.class);

    private static LinkStore linkStore = new LinkStore();

    @PostMapping("/addLink")
    public LinkEntry addLink(@RequestBody LinkEntry inEntry) {

        log.info("/addLink called with: entry={}", inEntry);

        linkStore.add(inEntry);
        linkStore.save();

        return null;
    }

    @PostMapping("/searchLinks")
    public List<LinkEntry> searchLinks(@RequestBody  SearchRequest inSearch) {

        String search = inSearch.getSearch();

        log.info("/searchLinks called with: search={}", search);

        List<LinkEntry> linkList = linkStore.findMatches(search);

        return linkList;
    }
}
