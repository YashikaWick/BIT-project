package com.assetmanagement.controller;



import com.assetmanagement.entity.Asset;
import com.assetmanagement.entity.Supplier;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReportProvider {

    public static List<ReportEntitySystemAccessAnalysis> getSystemAccessAnalysisReport(LocalDateTime startdate, LocalDateTime enddate) {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT d.name as name, count(*) as attempt FROM assetmanagement.sessionlog as s, assetmanagement.user as u, assetmanagement.employee as e, assetmanagement.designation as d where s.user_id=u.id and u.employee_id=e.id and e.designation_id=d.id and s.logintime between '"+startdate+"' and '"+enddate+"' group by d.id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntitySystemAccessAnalysis> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntitySystemAccessAnalysis report = new ReportEntitySystemAccessAnalysis(rs.getString("name"), rs.getInt("attempt"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }



    public static List<Supplier> getSupplierDetailsReport() {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            //Query needed for the report- fill the query by using workbench
            String query = "SELECT s.regno, s.company_name, s.tel FROM assetmanagement.supplier as s;";
            ResultSet rs = st.executeQuery(query);

            //Create list for add data objects in ResultSet
            List<Supplier> list = new ArrayList<>();

            while (rs.next()) {
                Supplier report = new Supplier(rs.getInt("regno"), rs.getString("company_name"), rs.getString("tel"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static List<Asset> getAssetDetailsReport() {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT a.assetcode, a.name, a.purchase_price FROM assetmanagement.asset as a;\n";
            ResultSet rs = st.executeQuery(query);

            List<Asset> list = new ArrayList<>();

            while (rs.next()) {
                Asset report = new Asset(rs.getString("assetcode"), rs.getString("name"), rs.getBigDecimal("purchase_price"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }








}



