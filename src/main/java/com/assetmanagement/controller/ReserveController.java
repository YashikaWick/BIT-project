package com.assetmanagement.controller;

import com.assetmanagement.dao.ReserveDao;
import com.assetmanagement.entity.Reserve;
import com.assetmanagement.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReserveController {

    @Autowired
    private ReserveDao dao;

    @RequestMapping(value = "/reservations", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Reserve> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.RESERVE,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

}
