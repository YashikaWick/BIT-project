package com.assetmanagement.controller;

import com.assetmanagement.dao.BrandDao;
import com.assetmanagement.entity.Brand;
import com.assetmanagement.entity.Category;
import com.assetmanagement.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class BrandController {

    @Autowired
    private BrandDao dao;

    @RequestMapping(value = "/brands", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Brand> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.BRAND,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    /*@RequestMapping(value = "/brands/list", method = RequestMethod.GET, produces = "application/json")
    public List<Brand> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }*/

    @RequestMapping(value = "/brands/listbycategory",params = "categoryId", method = RequestMethod.GET, produces = "application/json")
    public List<Brand> listByCategory(@Param("categoryId")Integer categoryId) {
        return dao.listByCategory(categoryId);
    }

    @RequestMapping(value = "/brands/listbysubcategory",params = "categoryId", method = RequestMethod.GET, produces = "application/json")
    public List<Brand> listBySubCategory(@Param("categoryId")Integer categoryId) {
        return dao.listByCategory(categoryId);
    }

    @RequestMapping(value = "/brands/list", method = RequestMethod.GET, produces = "application/json")
    public  List<Brand> list(){
        return dao.list();
    }

    @RequestMapping(value = "/brands", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Brand brand) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.BRAND,AuthProvider.INSERT)) {

            try {
                dao.save(brand);
                return "0";
            } catch (Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/brands", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Brand brand) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.BRAND,AuthProvider.UPDATE)) {

            try {
                dao.save(brand);
                return "0";
            }
            catch(Exception e) {
                return "Error-Updating : "+e.getMessage();
            }
        }


        return "Error-Updating : You have no Permission";
    }

    @RequestMapping(value = "/brands", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Brand brand ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.BRAND,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(brand.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}
