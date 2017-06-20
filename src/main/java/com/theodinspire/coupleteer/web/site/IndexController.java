package com.theodinspire.coupleteer.web.site;

import com.theodinspire.coupleteer.Couplet;
import com.theodinspire.coupleteer.Coupleteer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by corms on 6/19/17.
 */

@Controller
public class IndexController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        Couplet couplet = Coupleteer.getInstance().getCouplet();
        String string = couplet.toStringWithDelimiter("<br />");
        
        model.addAttribute("couplet", string);
        
        return "index";
    }
}
