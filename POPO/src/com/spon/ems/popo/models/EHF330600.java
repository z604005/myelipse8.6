package com.spon.ems.popo.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;

public class EHF330600 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {

	private BaseFunction base_tools;
	private BA_TOOLS tools;
	
	public EHF330600(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
			tools = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF330600( String comp_id ){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction(comp_id);
			tools = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public List queryData(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List dataList = null;
		
		try{
			//Query
			String sql = "" +
			" SELECT a.EHF310100T0_01,a.EHF310100T0_03 AS EHF310100T0_03, " +
			" 		 a.EHF310100T0_04 as EHF310100T0_04, " +
			"   a.EHF310100T0_10 as EHF310100T0_10, " +
			"   a.EHF310100T0_31 as EHF310100T0_31, " +
			"   a.EHF310100T0_21 as EHF310100T0_21, " +
			"   a.EHF310100T0_22 as EHF310100T0_22, " +
			"   b.EHF300200T0_02 as EHF300200T0_02," +
			"   A.DATE_CREATE as DATE_CREATE    " +  						
			"   FROM EHF310100T0 A " +
			"   LEFT JOIN ehf300200t0 b ON a.EHF310100T0_34 = b.EHF300200T0_06 AND a.EHF310100T0_11 = b.EHF300200T0_01" +
			"   WHERE 1=1 " ;			
									
//			if(this.base_tools.existString((String)queryCondMap.get("EHF330600T0_03_B"))&& 
//					   this.base_tools.existString((String)queryCondMap.get("EHF330600T0_03_E"))){	//櫃號
//				sql += " and A.EHF310100T0_03 between ? and ? ";
//			}else if(this.base_tools.existString((String)queryCondMap.get("EHF330600T0_03_B"))){	//只填櫃號(起)
//				sql += " and A.EHF310100T0_03 >= ?";
//			}else if(this.base_tools.existString((String)queryCondMap.get("EHF330600T0_03_E"))){	//只填櫃號(迄)
//				sql += " and A.EHF310100T0_03 <= ?";
//			}

			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_01_01"))){	//系統編號
				sql += " and A.EHF310100T0_01 like ?";
			}

			if(this.base_tools.existString((String)queryCondMap.get("EHF330600T0_10"))){	//日期
				sql += " and A.EHF310100T0_10 like ? ";
			}
			
			sql += "" +
			" AND A.EHF310100T0_34 like ? " +  //公司代碼
			"  	ORDER BY DATE_CREATE DESC  " +
			"   LIMIT 100  " ;    
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_01_01"))){//系統編號
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_01_01"));
				indexid++;
			}
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF330600T0_10"))){//送餐日期
				pstmt.setString(indexid, (String)queryCondMap.get("EHF330600T0_10"));
				indexid++;
			}
		
			
			
//			if(this.base_tools.existString((String)queryCondMap.get("EHF330600T0_03_B")) && 
//			   this.base_tools.existString((String)queryCondMap.get("EHF330600T0_03_E"))){	//櫃號(起、迄都有填)
//				pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF330600T0_03_B")));
//				indexid++;
//				pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF330600T0_03_E")));
//				indexid++;
//			}else if(this.base_tools.existString((String)queryCondMap.get("EHF330600T0_03_B"))){	//只填櫃號(起)
//				pstmt.setInt(indexid,Integer.parseInt((String)queryCondMap.get("EHF330600T0_03_B")));
//				indexid++;
//			}else if(this.base_tools.existString((String)queryCondMap.get("EHF330600T0_03_E"))){	//只填櫃號(迄)
//				pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF330600T0_03_E")));
//				indexid++;
//			}
			
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			//System.out.println(sql);
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}

	@Override
	public Map queryEditData(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		
		try{			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}

	@Override
	public void addData(Map dataMap) {
		// TODO Auto-generated method stub
		
	
		try{
			
		}catch(Exception e){
			
		}

	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
	}

	@Override
	public Map queryDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.base_tools.close();
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return this.base_tools.isClosed();
	}

	
}