package com.spon.ems.popo.models;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;

public class EHF330700 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	private BA_TOOLS tool;
	
	public EHF330700(){
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
			tool = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public EHF330700( String comp_id ){
		try{
			//建立基本操作元件
			base_tools = new BaseFunction(comp_id);
			tool = BA_TOOLS.getInstance();
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
			" SELECT A.EHF310100T0_01, A.EHF310100T0_03, A.EHF310100T0_04, " +
			" 		 A.EHF310100T0_10 " +						
			" FROM EHF310100T0 A " +
			" WHERE 1=1 " ;		
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_04"))){	//產婦姓名
				sql += " and A.EHF310100T0_04 like ? ";
			}
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03_B")) && 
			   this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03_E"))){	//櫃號(起、迄都有填)
					sql += " and A.EHF310100T0_03 between ? and ? ";
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03_B"))){	//只填櫃號(起)
					sql += " and A.EHF310100T0_03 >= ? ";
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03_E"))){	//只填櫃號(迄)
					sql += " and A.EHF310100T0_03 <= ? ";
			}
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_10_B")) && 
			   this.base_tools.existString((String)queryCondMap.get("EHF310100T0_10_E"))){	//送餐日期(起、迄都有填)
				sql += " and A.EHF310100T0_10 between ? and ? ";
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_10_B"))){	//只填送餐日期(起)
				sql += " and A.EHF310100T0_10 >= ? ";
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_10_E"))){	//只填送餐日期(迄)
				sql += " and A.EHF310100T0_10 <= ? ";
			}
			
			sql +=" " +
			" AND A.EHF310100T0_34 like ? " +  //公司代碼
			" ORDER BY A.EHF310100T0_02, A.EHF310100T0_03 ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;

			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_04"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_04"));
				indexid++;
			}
		//	pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF320200T0_01")));a 
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03_B")) && 
					   this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03_E"))){	//櫃號(起、迄都有填)
						pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF310100T0_03_B")));
						indexid++;
						pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF310100T0_03_E")));
						indexid++;
					}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03_B"))){	//只填櫃號(起)
						pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF310100T0_03_B")));
						indexid++;
					}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03_E"))){	//只填櫃號(迄)
						pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF310100T0_03_E")));
						indexid++;
					}		
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_10_B")) && 
			   this.base_tools.existString((String)queryCondMap.get("EHF310100T0_10_E"))){	//送餐日期(起、迄都有填)
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_10_B"));
				indexid++;
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_10_E"));
				indexid++;
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_10_B"))){	//只填送餐日期(起)
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_10_B"));
				indexid++;
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_10_E"))){	//只填送餐日期(迄)
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_10_E"));
				indexid++;
			}
			
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
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
		return null;
	}


	@Override
	public void addData(Map dataMap) {
		// TODO Auto-generated method stub
		
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
		
	}


	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}