package shorterURL.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shorterURL.service.MainService;

import java.util.Collections;
import java.util.Map;

@Validated
@RestController
@RequestMapping
public class MainController {
    Logger logger = LoggerFactory.getLogger(MainController.class.getSimpleName());
    MainService service;



    @Autowired
    public MainController(MainService service) {
        this.service = service;
    }

    @PostMapping(path = "/makeShortLink")
    public String createShortUrl(@RequestParam("originalURL") String originalURL,
                                 @RequestParam(name = "TTL", required = false) Long ttl,
                                 @RequestParam(name = "ownTitle", required = false) String ownTitle,
                                 @RequestParam(name = "pin", required = false) Long pin) {
        System.out.println("New link found");

        System.out.println(originalURL);
        if (originalURL != null) {
            return  service.makeShortLink(originalURL, ttl, ownTitle, pin);
        } else {
            return null;
        }
    }

    @GetMapping(path = "/{hash}")
    public ResponseEntity redirectShorter(@PathVariable("hash") String hash) {
        System.out.println(hash);
        System.out.println("Finding link");
        String originalLink = service.foundOriginalLinkByHash(hash);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", originalLink);
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }


    @DeleteMapping(path = "/deleteShortLink")
    public ResponseEntity deleteLink(@RequestParam("hash") String hash, @RequestParam("pin") Long pin) {
        if (hash != null && pin != null) {

            return new ResponseEntity(service.deleteShortLink(hash, pin), HttpStatus.OK);
        }
        return null;
    }

    @GetMapping(path = "/getStatistic")
    public Map<String, Object> getStatistic(@RequestParam("hash") String hash) {
        return Collections.singletonMap("statistic", service.getStatistic(hash));

    }
}

