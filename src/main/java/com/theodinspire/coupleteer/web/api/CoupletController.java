package com.theodinspire.coupleteer.web.api;

import com.theodinspire.coupleteer.Couplet;
import com.theodinspire.coupleteer.Coupleteer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by corms on 6/20/17.
 */

@RestController
public class CoupletController {
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/api/couplet", method = RequestMethod.GET)
    public Couplet couplet() {
        return Coupleteer.getInstance().getCouplet();
    }
}
