package com.assetmanagement.controller;

import com.assetmanagement.dao.SubcategoryDao;
import com.assetmanagement.entity.Category;
import com.assetmanagement.entity.Employee;
import com.assetmanagement.entity.Subcategory;
import com.assetmanagement.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class SubcategoryController {
    @Autowired
    private SubcategoryDao dao;

    /*@RequestMapping(value = "/subcategories/list", method = RequestMethod.GET, produces = "application/json")
    public List<Subcategory> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }*/

    @RequestMapping(value = "/subcategories", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Subcategory> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.SUBCATEGORY,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/subcategories/listbycategory",params ="categoryId",method = RequestMethod.GET, produces = "application/json")
    public List<Subcategory> list(@RequestParam("categoryId") Integer categoryId){
        return dao.listByCategory(categoryId);
    }

    @RequestMapping(value = "/subcategories/list", method = RequestMethod.GET, produces = "application/json")
    public List<Subcategory> subcategories() {
        return dao.list();
    }

    @RequestMapping(value = "/subcategories", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Subcategory subcategory) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.SUBCATEGORY,AuthProvider.INSERT)) {


                try {
                    dao.save(subcategory);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }
}
