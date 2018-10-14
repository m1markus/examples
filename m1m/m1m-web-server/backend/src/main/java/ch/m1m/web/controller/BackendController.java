package ch.m1m.web.controller;

import ch.m1m.web.domain.LinkItem;
import ch.m1m.web.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger log = LoggerFactory.getLogger(BackendController.class);

    @Autowired
    private LinkRepository linkRepository;


    @RequestMapping(path = "/hello")
    public @ResponseBody String sayHello() {
        log.info("GET called on /hello resource");
        return "hello from spring boot my version 0.0.1";
    }

    @RequestMapping(path = "/links", method = RequestMethod.GET)
    public @ResponseBody List<LinkItem> getAllLinks() {
        log.info("GET called on /links resource");
        return linkRepository.findAll();
    }

}
