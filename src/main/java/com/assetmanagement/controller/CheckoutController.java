package com.assetmanagement.controller;

import com.assetmanagement.dao.CheckoutDao;
import com.assetmanagement.entity.Asset;
import com.assetmanagement.entity.Assetcheckout;
import com.assetmanagement.entity.Checkout;
import com.assetmanagement.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckoutController {

    @Autowired
    private CheckoutDao dao;

    @RequestMapping(value = "/checkouts", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Checkout> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.CHECKOUT,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/checkouts/choutNo", method = RequestMethod.GET, produces = "application/json")
    public String lastChoutNo(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.CHECKOUT ,AuthProvider.SELECT)) {
            String choutNo = dao.lastChoutNo();
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
            return "{\"no\":"+"\""+choutNo+"\"}";
        }
        return null;
    }
    @RequestMapping(value = "/checkouts", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Checkout checkout) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CHECKOUT,AuthProvider.INSERT)) {
            Checkout checkoutno = dao.findByCoNo(checkout.getCheckoutno());

            if (checkoutno != null)
                return "Error-Validation : Check out no Exists";

            else
                try {
                    for (Assetcheckout choutasset: checkout.getAssetcheckoutList())
                        choutasset.setCheckoutId(checkout);
                    dao.save(checkout);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    /*@RequestMapping(value = "/purchaseorders", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Porder purchasorder) {
        //System.out.print("Added Successfully.....!");

        if(AuthProvider.isAuthorized(username,password,ModuleList.PORDERS,AuthProvider.INSERT)) {
            Porder po = dao.findByPoNo(purchasorder.getPorderno());
            if (po != null)
                return "Error-Validation : Purchase Order Already Exists";
            else
                try {
                for (Porderitem poitm: purchasorder.getPorderitemList())
                    poitm.setPorderId(purchasorder);

                    dao.save(purchasorder);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }*/

}
