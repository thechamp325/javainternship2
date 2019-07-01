package com.example.demo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api",  produces = "application/json")
public class PI {
	public String log;
	
	@Autowired
	DB db = new DB();
	
	
	//@Autowired
	AdminDB adb = new AdminDB();
	
	@PostMapping("/pi/emp/faculty")
	public Map<String,Integer> facultyinfo(@RequestBody Map<String, Object> payload) throws SQLException {
		Map<String, Integer> faculty = new HashMap<String, Integer>();
		facultyreport f = new facultyreport();
		return f.facultyinfo(payload,adb);
	
	}


	
	@GetMapping("/pi/emp/t")
	public Map<String, String> personalInfo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", "hello");
		return map;
	}
	
	
	@PostMapping("/pi/emp/enter")
	public String NewUser(@RequestBody Map<String, Object> payload) throws Exception{
		System.out.println(payload);
		Personal p = new Personal();
		String s = p.entry(payload);
		//System.out.println("here");
		return s;
	}
	
	
	@PostMapping("/pi/emp/enter/login")
	public boolean loginmethod(@RequestBody Map<String, Object> login) throws Exception{
		log = (String)login.get("ID");
		System.out.println("here1");
		String sql2 ="SELECT * FROM public.\"Personal\"";
		Connection conn = db.connect();
		Statement st = conn.createStatement();
		//System.out.println("......");
		ResultSet rs = st.executeQuery(sql2);
		//rs.next();
		//String name = rs.getString("First_Name");
		//System.out.println(name);
		//String s = null;
		while (rs.next())
		{
		    System.out.print("Column 1 returned ");
		    if(rs.getString("Employee_ID").equals(log)) {
		    return true;
		  
		    }
		    
		}
		rs.close();
		st.close();
		
		return false;
	}
	
	
	@PostMapping("/pi/emp/enter/details")
		public String data(@RequestBody Map<String, Object> payload) throws Exception{
		Education E= new Education();
		String s = E.entry(payload,log);
		Officeinfo oi = new Officeinfo();
		String s3 = oi.entry(payload, log);
		Others o = new Others();
		String s2 = o.entry(payload, log);
		
		return s;
	}
	
	
	
	@GetMapping("pi/emp/report")
	public Map<String,String> Report() throws SQLException{
		Map<String, String> map = new HashMap<String, String>();

		String sql2 ="SELECT * FROM public.\"Personal\"";
		String sql3 = "SELECT * FROM public.officeinfo";
		
			int i =1;
			Connection conn = db.connect();
			Statement st = conn.createStatement();
			Statement st2 = conn.createStatement();

			ResultSet rs = st.executeQuery(sql2);
			ResultSet rs2 = st2.executeQuery(sql3);
			
			while (rs.next())
			{
			    System.out.print("Column 1 returned ");
			    map.put("Id"+String.valueOf(i),String.valueOf(rs.getInt(1)));
			    map.put("Name"+String.valueOf(i),rs.getString(2));
			    map.put("Department"+String.valueOf(i),rs.getString(5));
			    i++;
			    rs2.next();
			}
			rs.close();
			st.close();
			
			return map;
	}
	
	
	
	@PostMapping("/pi/emp/admin")
	public boolean adminlog(@RequestBody Map<String, Object> payload) throws Exception{
		 final String adminid = (String)payload.get("Admin_ID");
		 final String pass = (String)payload.get("Password");
		 String sql2 ="SELECT * FROM public.\"Admin\"";
		 Connection conn = adb.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql2);
			while (rs.next())
			{ 
				if((adminid.equals(rs.getString(1))&&pass.equals(rs.getString(2)))) {
					return true;
				}
			    
			}
			rs.close();
			st.close();
			
		 
	return false;	
	}
	
	
	
	}

