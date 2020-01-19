package com.assetmanagement.controller;




import com.assetmanagement.entity.Asset;
import com.assetmanagement.entity.Supplier;
import com.assetmanagement.util.ModuleList;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ReportController {



    @RequestMapping(value = "/reports/systemaccessanalysis", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySystemAccessAnalysis> systemaccessanalysis(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
    LocalDateTime startdate = LocalDateTime.now().minusDays(35);
    LocalDateTime enddate = LocalDateTime.now().plusDays(1);
    return ReportProvider.getSystemAccessAnalysisReport(startdate, enddate);
}
else return  null;


    }

    @RequestMapping(value = "/reports/systemaccessanalysis", params = {"startdate", "enddate"}, method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySystemAccessAnalysis> systemaccessanalysis2(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime stdate=LocalDateTime.parse(startdate+" 00:00",formatter );
            LocalDateTime endate=LocalDateTime.parse(enddate +" 00:00",formatter);
            return ReportProvider.getSystemAccessAnalysisReport(stdate,endate);
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/supplierdetails", method = RequestMethod.GET, produces = "application/json")
    public List<Supplier> supplierDetails(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
           return ReportProvider.getSupplierDetailsReport();
        }
        else return  null;
    }


    @RequestMapping(value = "/reports/assetdetails", method = RequestMethod.GET, produces = "application/json")
    public List<Asset> assetDetails(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.ASSET,AuthProvider.SELECT)) {
            return ReportProvider.getAssetDetailsReport();
        }
        else return  null;
    }

}
