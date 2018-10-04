package bookmarks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
    public static final String INDEX_MAPPING = "/";
    public static final String LINKS_MAPPING = "/links";

    @GetMapping(INDEX_MAPPING)
    public String index(){
        log.info("Request arrived to {}", INDEX_MAPPING);
        return "index";
    }

    @GetMapping(LINKS_MAPPING)
    public String links(){
        log.info("Request arrived to {}", LINKS_MAPPING);
        return "links";
    }
}
