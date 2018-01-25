package com.spon.ems.com.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import vtgroup.ems.common.vo.AuthorizedBean;
import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.com.forms.EHF000700M0F;
import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.ems.com.models.EHF000700;
import com.spon.ems.vacation.tools.EMS_FixAttendance;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>請假單申請
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午10:32:15
 */
public class EHF000700M0A extends QueryAction {
	
//	private EMS_ACCESS ems_access;
//	
//	public EHF000700M0A(){
//		ems_access = EMS_ACCESS.getInstance();
//	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		//String user_name = (String)paramMap.get(super.USER_NAME);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			EHF000700 ehf000700 = new EHF000700(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF000700T0_01", key[0]);  //表單編號
			dataMap.put("COMP_ID", comp_id);
			
			ehf000700.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			ehf000700.close();
			
			//控制刪除後的顯示畫面
			paramMap.put(super.DEL_FORWARD_CONFIG, super.FORWARD_INIT);
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		paramMap.put(INIT_FORWARD_CONFIG,"queryForm");
		EHF000700M0F Form = new EHF000700M0F();
		List list = new ArrayList();
		Form.setEHF000700C(list);

			return (ActionForm) Form;
	}
	
	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF000700M0F Form1 = (EHF000700M0F) form;
		Map return_map = new HashMap();
		EHF000700M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF000700M0F> querylist = new ArrayList<EHF000700M0F>();
			
			//建立EHF000700元件
			EHF000700 ehf000700 = new EHF000700(comp_id);
			//HR_TOOLS hr_tools1 = new HR_TOOLS();
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form1);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf000700_queryList = ehf000700.queryData(queryCondMap);
			
			Iterator it = ehf000700_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				tempMap = (Map) it.next();
				bean = new EHF000700M0F();
				bean.setEHF000700T0_01(String.valueOf(tempMap.get("EHF000700T0_01"))); 
				bean.setEHF000700T0_02((String)tempMap.get("EHF000700T0_02"));  
				bean.setEHF000700T0_03((String)tempMap.get("EHF000700T0_03"));
				bean.setEHF000700T0_04((String)tempMap.get("EHF000700T0_04"));
				bean.setEHF000700T0_05((String)tempMap.get("EHF000700T0_05"));
				querylist.add(bean);
			}
			
			//設定querylist
			Form1.setEHF000700C(querylist);
			
			//關閉EHF000700元件
			ehf000700.close();
			//hr_tools1.close();
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
			
		}
		return return_map;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
	}

}
