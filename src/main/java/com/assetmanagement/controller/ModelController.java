package com.assetmanagement.controller;

import com.assetmanagement.dao.ModelDao;
import com.assetmanagement.entity.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModelController {

    @Autowired
    private ModelDao dao;

    @RequestMapping(value = "/models/list", method = RequestMethod.GET, produces = "application/json")
    public List<Model> list(){return dao.list();}

    @RequestMapping(value = "/models/listbybrand",params = "brandId", method = RequestMethod.GET, produces = "application/json")
    public List<Model> listByBrand(@Param("brandId")Integer brandId) {
        return dao.listByBrand(brandId);
    }
}


