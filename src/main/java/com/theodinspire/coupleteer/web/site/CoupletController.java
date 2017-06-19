package com.theodinspire.coupleteer.web.site;

import com.theodinspire.coupleteer.Coupleteer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by corms on 6/19/17.
 */

@Controller
public class CoupletController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        String[] coupletLines = Coupleteer.getInstance().getCouplet();
        String couplet = coupletLines[0] + "<br />" + coupletLines[1];
        
        model.addAttribute("couplet", couplet);
        
        return "index";
    }
}
