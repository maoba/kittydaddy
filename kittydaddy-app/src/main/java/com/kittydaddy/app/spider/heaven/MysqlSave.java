package com.kittydaddy.app.spider.heaven;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class MysqlSave {
    private Connection getConnection(){
        Connection con = null;
        //创建DataSource接口的实现类对象
        BasicDataSource datasource = new BasicDataSource();
        //设置连接设置的四个基本信息,用set方法设置
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://139.199.128.226:3306/kittydaddy");
        datasource.setUsername("root");
        datasource.setPassword("183244LUjianhao!");
        try{
            con = datasource.getConnection();
        }catch(SQLException e){
            System.out.println(e);
            throw new RuntimeException("连接失败");
        }
        return con;
    }

    public void savetoSql(HashMap<String,String> resourse){
        Connection con = getConnection();
        String Classification = null;
        String movename = null;
        String role = null;
        String introduce = null;
        String titilepicture = null;
        String introducepicture = null;
        String address = null;
        float douban = 0;
        if(resourse.get("Classification") != null && resourse.get("Classification").length() < 20){
            Classification = resourse.get("Classification");
        }else{
            Classification = new String("null");
        }
        if(resourse.get("movename") != null && resourse.get("movename").length() < 50){
            movename = resourse.get("movename");
        }else{
            movename = new String("unknown name");
        }
        if(resourse.get("role") != null && resourse.get("role").length() < 20){
            role = resourse.get("role");
        }else{
            role = "unknown";
        }
        if(resourse.get("introduce") != null && resourse.get("introduce").length()<2000){
            introduce = resourse.get("introduce");
        }else{
            introduce = "null";
        }
        if(resourse.get("titilepicture") != null && resourse.get("titilepicture").length()<100){
            titilepicture = resourse.get("titilepicture");
        }else{
            titilepicture = "null";
        }
        if(resourse.get("introducepicture") != null && resourse.get("introducepicture").length() <100){
            introducepicture = resourse.get("introducepicture");
        }else{
            introducepicture = "null";
        }
        if(resourse.get("address") != null && resourse.get("address").length() < 10000){
            address = resourse.get("address");
        }else{
            address = "null";
        }
        if((resourse.get("douban")) != null){
            try{
                Float doubanobj = new Float(resourse.get("douban"));
                douban = doubanobj.floatValue();
            }catch(Exception e){
                System.out.println("数字转换错误");
            }
        }else{

        }
        String sql = "INSERT INTO moves (Classification,movename,role,introduce,titilepicture,introducepicture,address,douban)"+
                "VALUES(?,?,?,?,?,?,?,?)";
        try {
            //预编译语句
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setObject(1,Classification);
            pst.setObject(2, movename);
            pst.setObject(3, role);
            pst.setObject(4, introduce);
            pst.setObject(5, titilepicture);
            pst.setObject(6, introducepicture);
            pst.setObject(7, address);
            System.out.println(address);
            pst.setObject(8, douban);
            int num = pst.executeUpdate();
            System.out.println("修改了"+num+"行数据");
            System.out.println(movename+":"+role+":"+introduce+":"+titilepicture);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
