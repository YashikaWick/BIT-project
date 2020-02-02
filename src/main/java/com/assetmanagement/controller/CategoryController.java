package com.assetmanagement.controller;

import com.assetmanagement.dao.CategoryDao;
import com.assetmanagement.entity.Category;
import com.assetmanagement.entity.Employee;
import com.assetmanagement.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CategoryController {

    @Autowired
    private CategoryDao dao;

    @RequestMapping(value = "/categories", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Category> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.CATEGORY,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/categories/list", method = RequestMethod.GET, produces = "application/json")
    public List<Category> categories() {
        return dao.list();
    }


    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Category category) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CATEGORY,AuthProvider.INSERT)) {

                try {
                    dao.save(category);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/categories", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Category category) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CATEGORY,AuthProvider.UPDATE)) {

                try {
                    dao.save(category);
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
            }


        return "Error-Updating : You have no Permission";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Category category ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.CATEGORY,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(category.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


}
