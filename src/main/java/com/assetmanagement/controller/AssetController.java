package com.assetmanagement.controller;

import com.assetmanagement.dao.AssetDao;
import com.assetmanagement.entity.Asset;
import com.assetmanagement.entity.Subcategory;
import com.assetmanagement.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class  AssetController {
    @Autowired
    private AssetDao dao;

    @RequestMapping(value = "/assets/listbybranch",params ="branchId",method = RequestMethod.GET, produces = "application/json")
    public List<Asset> listByBranch(@RequestParam("branchId") Integer branchId)
    {
        return dao.listByBranch(branchId);
    }

    @RequestMapping(value = "/assets", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Asset> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.ASSET,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/assets/list", method = RequestMethod.GET, produces = "application/json")
    public List<Asset> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }
    @RequestMapping(value = "/assets", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Asset asset) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.ASSET,AuthProvider.INSERT)) {
            Asset assetcode = dao.findByCode(asset.getAssetcode());
            Asset serialNumber = dao.findBySerialNumber(asset.getSerialNumber());
            if (assetcode != null)
                return "Error-Validation : Code Exists";
            else if (serialNumber != null)
                return "Error-Validation : Serial Number Exists";
            else
                try {
                    dao.save(asset);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/assets", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Asset asset) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.ASSET,AuthProvider.UPDATE)) {
                try {
                    dao.save(asset);
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
            }
        return "Error-Updating : You have no Permission";
    }

    @RequestMapping(value = "/assets", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Asset asset ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.ASSET,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(asset.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

    @RequestMapping(value = "/assets/astcode", method = RequestMethod.GET, produces = "application/json")
    public String lastAssetcode(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.ASSET ,AuthProvider.SELECT)) {
            String astcode = dao.lastAssetcode();
            // System.out.println(porderno);
            /*Integer poNumber = Integer.parseInt(pono);
            String porderNumber="";
            if(poNumber<9)
                porderNumber = "0000"+(poNumber+1);
                
            else if(poNumber<99)
                porderNumber = "000"+(poNumber+1);
            else if(poNumber<999)
                porderNumber = "00"+(poNumber+1);
            else if(poNumber<9999)
                porderNumber = "0"+ (poNumber+1);
*/
            return "{\"no\":"+"\""+astcode+"\"}";
        }
        return null;
    }

}

