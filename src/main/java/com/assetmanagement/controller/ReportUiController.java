package com.assetmanagement.controller;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/ui/report")
public class ReportUiController {

    @RequestMapping("/systemaccessanalysis")
    public ModelAndView login(){
        ModelAndView model = new ModelAndView();
        model.setViewName("systemaccessanalysis.html");
        return model;
    }

    @RequestMapping("/supplier")
    public ModelAndView supplier(){
        ModelAndView model = new ModelAndView();
        model.setViewName("supplier.html");
        return model;
    }

    @RequestMapping("/assetdetails")
    public ModelAndView asset(){
        ModelAndView model = new ModelAndView();
        model.setViewName("assetdetails.html");
        return model;
    }

}