package ch.m1m.web.search.searchandfind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LinkStore {

    private static String FILE_PATH = "./my_links.txt";

    private static Logger log = LoggerFactory.getLogger(LinkStore.class);

    private List<LinkEntry> allLinks = new ArrayList<>();

    private ObjectMapper jsonMapper = new ObjectMapper();


    public LinkStore() {

        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        load();
    }

    public void load() {

        List<LinkEntry> newLinks = null;

        try {
            newLinks = jsonMapper.readValue(new File(FILE_PATH),
                    jsonMapper.getTypeFactory().constructCollectionType(List.class, LinkEntry.class));
            allLinks = newLinks;


        } catch (IOException e) {
            log.error("JSON readValue failed for file {}", FILE_PATH, e);
        }
    }

    public void save() {
        try {
            // in one step
            //
            // mapper.writeValue(new File("c:\\file.json"), obj);

            // in two steps
            //
            String jsonLinksAsString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allLinks);
            Files.write(Paths.get(FILE_PATH), jsonLinksAsString.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);

        } catch (JsonProcessingException e) {
            log.error("failed to convert to JSON", e);
            throw new RuntimeException(e);

        } catch (IOException e) {
            log.error("failed to write file {}", FILE_PATH, e);
            throw new RuntimeException(e);
        }
    }

    public void preventNullOrEmpty(LinkEntry inLinkEntry) {

        String errorMessage = "null or empty value not allowed";

        String val = inLinkEntry.getUrl();
        if (val == null || val.equals("")) {
            throw new RuntimeException(errorMessage);
        }
        val = inLinkEntry.getDescription();
        if (val == null || val.equals("")) {
            throw new RuntimeException(errorMessage);
        }
        val = inLinkEntry.getKeywords();
        if (val == null || val.equals("")) {
            throw new RuntimeException(errorMessage);
        }
    }

    public StatusResponse add(LinkEntry inLinkEntry) {

        preventNullOrEmpty(inLinkEntry);

        //if (true) throw new RuntimeException("this is not a real error");

        String entryKey = inLinkEntry.getId();
        if (entryKey == null) {
            entryKey = UUID.randomUUID().toString();
            inLinkEntry.setId(entryKey);
            log.info("generated new uuid for new entry id=", entryKey);
        }

        // convert keywords to lowercase
        //
        String keywords = inLinkEntry.getKeywords();
        inLinkEntry.setKeywords(keywords.toLowerCase());

        allLinks.add(inLinkEntry);
        save();

        return new StatusResponse("created", entryKey);
    }

    public List<LinkEntry> getTestData() {
        List<LinkEntry> result = new ArrayList<>();

        LinkEntry link = new LinkEntry(null, "http://www.ibm.com", "IBM", "mainframe");
        result.add(link);

        link = new LinkEntry(null, "http://www.microsoft.com", "Windows and friends (word excel)", "office excel");
        result.add(link);

        return result;
    }

    public List<LinkEntry> findMatches(String inSearchKeywords) {
        List<LinkEntry> result = new ArrayList<>();

        String regExp = buildRegExpFromInput(inSearchKeywords);
        log.info("regex pattern is: {}", regExp);

        for(LinkEntry linkEntry : allLinks) {
            if (linkEntry.getKeywords().matches(regExp)) {
                result.add(linkEntry);
            }
        }

        return result;
    }

    /* return something like that: ^(?=.*CAT)(?=.*ELEPHANT)(?=.*DOG).*
     *
     */

    public String buildRegExpFromInput(String inKeywords) {

        String retPattern = "^";;
        String[] keywords = inKeywords.toLowerCase().split(" ");

        if (keywords.length >= 1) {
            for (String keyword : keywords) {
                retPattern += "(?=.*" + keyword + ")";
            }
            retPattern += ".*";

        } else {
            // match ALL
            retPattern += ".*";
        }

        return retPattern;
    }
}
