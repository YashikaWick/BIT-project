package com.assetmanagement.controller;

import com.assetmanagement.dao.ModelDao;
import com.assetmanagement.entity.Brand;
import com.assetmanagement.entity.Model;
import com.assetmanagement.entity.Subcategory;
import com.assetmanagement.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModelController {

    @Autowired
    private ModelDao dao;

    @RequestMapping(value = "/models", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Model> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.MODEL,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/models/list", method = RequestMethod.GET, produces = "application/json")
    public List<Model> list(){return dao.list();}

    @RequestMapping(value = "/models/listbybrand",params = "brandId", method = RequestMethod.GET, produces = "application/json")
    public List<Model> listByBrand(@Param("brandId")Integer brandId) {
        return dao.listByBrand(brandId);
    }

    @RequestMapping(value = "/models", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Model model) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.MODEL,AuthProvider.INSERT)) {


            try {
                dao.save(model);
                return "0";
            } catch (Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }
        return "Error-Saving : You have no Permission";

    }
}


