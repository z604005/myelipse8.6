package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.ems.util.overtime.EMS_Overtime_Record_Control;
import com.spon.ems.vacation.forms.EHF020801M0F;
import com.spon.ems.vacation.models.EHF020801;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action> 加班單申請作業(1張1人)
 *@author maybe
 *@version 1.0
 *@created 2016/2/1 下午1:55:01
 */
public class EHF020801M1A extends EditAction {

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020801M0F Form = (EHF020801M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			EHF020801 ehf020801 = new EHF020801(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			//設定使用者資訊
			dataMap.putAll(paramMap);
			dataMap.put("EHF020800T0_12", "01");
			
			//執行新增資料
			ehf020801.addData(dataMap);
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			float min_pay_hour = Float.parseFloat(tools.getSysParam(conn, (String)paramMap.get("COMP_ID"), "MIN_PAY_HOUR"));
			
			//處理加班時間資訊
			Map overtime_datetime_map =
			ehf020801.composeOvertimeDate(Form.getEHF020800T1_06_year(), 
										  Form.getEHF020800T1_06_HH()+Form.getEHF020800T1_06_MM(),
										  Form.getEHF020800T1_07_year(), 
										  Form.getEHF020800T1_07_HH()+Form.getEHF020800T1_07_MM(),min_pay_hour);
			
			//Set overtime_datetime data
			detailDataMap.putAll(overtime_datetime_map);
			
			//處理加班休息時間資訊
			Map overtime_break_datetime_map =
			ehf020801.composeOvertimeBreakDate(Form.getEHF020800T1_06_year(),Form.getEHF020800T1_07_year(),
											   Form.getEHF020800T1_06_HH()+Form.getEHF020800T1_06_MM(), 
											   Form.getEHF020800T1_07_HH()+Form.getEHF020800T1_07_MM(), 
											   Form.getEHF020800T1_06_BRK_HH()+ Form.getEHF020800T1_06_BRK_MM(), 
											   Form.getEHF020800T1_07_BRK_HH()+ Form.getEHF020800T1_07_BRK_MM());
			
			//Set overtime break_datetime data
			detailDataMap.putAll(overtime_break_datetime_map);
			//指定加班單細項資料表單編號
			detailDataMap.put("EHF020800T0_01", (String)dataMap.get("KEY_ID"));
			
			//新增加班明細資訊
			ehf020801.addDetailData("EHF020800T1", detailDataMap);			
			
			//關閉EHF020801元件
			ehf020801.close();
			
			//取出KEY_ID
			Form.setEHF020800T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020800T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//新增完成
			chk_flag = true;
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected Map executeQueryEditData(Connection conn, String[] key,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020801M0F Form = (EHF020801M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		Map return_map = new HashMap();
		
		try{
			EHF020801 ehf020801 = new EHF020801();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF020800T0_01", (key[0]));  //加班申請表單編號
			queryCondMap.putAll(paramMap);  //放入使用者資訊
			
			Map dataMap = ehf020801.queryEditData(queryCondMap);
			
			//關閉EHF020801 元件
			ehf020801.close();
			
			if(!dataMap.isEmpty()){
				
				BeanUtils.copyProperties(Form, dataMap);
				
				chk_flag = true;
				
			}
			
			//設定前端STRUTS-LAYOUT顯示模式
			if(Integer.parseInt(Form.getEHF020800T0_09()) >= 3){
				//出帳後則頁面不可編輯
				super.setFormDisplayMode(paramMap, super.StrutsLayoutInspectMode);
			}
			
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
	protected boolean executeSaveData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020801M0F Form = (EHF020801M0F) form;
		
		try{
			EHF020801 ehf020801 = new EHF020801();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf020801.saveData(dataMap);
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			float min_pay_hour = Float.parseFloat(tools.getSysParam(conn, (String)paramMap.get("COMP_ID"), "MIN_PAY_HOUR"));
			
			//處理加班時間資訊
			Map overtime_datetime_map =
			ehf020801.composeOvertimeDate(Form.getEHF020800T1_06_year(), 
										  Form.getEHF020800T1_06_HH()+Form.getEHF020800T1_06_MM(),
										  Form.getEHF020800T1_07_year(), 
										  Form.getEHF020800T1_07_HH()+Form.getEHF020800T1_07_MM(),min_pay_hour);
			
			//Set overtime_datetime data
			detailDataMap.putAll(overtime_datetime_map);
			
			//處理加班休息時間資訊
			Map overtime_break_datetime_map =
			ehf020801.composeOvertimeBreakDate(Form.getEHF020800T1_06_year(),Form.getEHF020800T1_07_year(),
											   Form.getEHF020800T1_06_HH()+Form.getEHF020800T1_06_MM(), 
											   Form.getEHF020800T1_07_HH()+Form.getEHF020800T1_07_MM(), 
											   Form.getEHF020800T1_06_BRK_HH()+ Form.getEHF020800T1_06_BRK_MM(), 
											   Form.getEHF020800T1_07_BRK_HH()+ Form.getEHF020800T1_07_BRK_MM());
			
			//Set overtime break_datetime data
			detailDataMap.putAll(overtime_break_datetime_map);
			
			//儲存加班明細資料
			ehf020801.saveDetailData("EHF020800T1", detailDataMap);
			
			//關閉EHF020801元件
			ehf020801.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020800T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//儲存完成
			chk_flag = true;
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020801M0F Form = (EHF020801M0F) form;
		
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020800T0_01());
			paramMap.put(super.KEY_ID, key_id);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}

	}

	@Override
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020801M0F Form = (EHF020801M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF020800T0_01());
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return key;
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020801M0F Form = (EHF020801M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String employee_id = (String)paramMap.get(super.EMPLOYEE_ID);
		
		try{
			//填單日期
			Form.setEHF020800T0_05(tools.ymdTostring(tools.getSysDate()));
			//加班日期
			Form.setEHF020800T0_06(tools.ymdTostring(tools.getSysDate()));
			//處理狀態
			Form.setEHF020800T0_09("01");  //處理中
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
			
			//填單人員工工號
			Form.setEHF020800T0_03((String)((Map)empMap.get(employee_id)).get("USER_CODE"));
			Form.setEHF020800T0_03_SHOW((String)((Map)empMap.get(employee_id)).get("EMPLOYEE_ID"));
			//填單人姓名
			Form.setEHF020800T0_03_TXT((String)((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME"));
			
			//填單人部門代號
			Form.setEHF020800T0_04((String)((Map)empMap.get(employee_id)).get("DEPT_ID"));
			Form.setEHF020800T0_04_SHOW((String)((Map)empMap.get(employee_id)).get("SHOW_DEPT_ID"));
			//填單人部門名稱
			Form.setEHF020800T0_04_TXT((String)((Map)empMap.get(employee_id)).get("SHOW_DESC"));
			
			//預設加班時數處理方式 為02.換錢
			Form.setEHF020800T1_11("02");
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
			
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		try{			
			DecimalFormat df = new DecimalFormat("00");
			
			//產生 "時" 的下拉選單
			try{
				List listHOUR=new ArrayList();
				for (int i = 0; i < 24; i++) {
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id(df.format(i));
					bform.setItem_value(df.format(i));
					listHOUR.add(bform);
				}
				request.setAttribute("listHOUR",listHOUR);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//產生 "分" 的下拉選單
			try{
				List listMINUTE=new ArrayList();
				for (int i = 0; i < 60; i= i+5) {//20131016修改   BY 賴泓錡
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id(df.format(i));
					bform.setItem_value(df.format(i));
					listMINUTE.add(bform);
				}
				request.setAttribute("listMINUTE",listMINUTE);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//產生處理狀態
			try{
				List listEHF020800T0_09 = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("");
				bform.setItem_value("一請選擇一");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("處理中");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("已完成計算");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("03");
				bform.setItem_value("已確認");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("04");
				bform.setItem_value("已出帳");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("05");
				bform.setItem_value("已結算");
				listEHF020800T0_09.add(bform);
				request.setAttribute("listEHF020800T0_09", listEHF020800T0_09);
			}catch(Exception e) {
				System.out.println(e);
			}
			try{
				List listEHF020800T1_11 = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("換錢");
				listEHF020800T1_11.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("換休");
				listEHF020800T1_11.add(bform);

				request.setAttribute("listEHF020800T1_11", listEHF020800T1_11);
			}catch(Exception e) {
				System.out.println(e);
			}
			//產生加班類別的radio button
			try{
				List listEHF020800T1_13 = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("例假");
				listEHF020800T1_13.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("休假");
				listEHF020800T1_13.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("03");
				bform.setItem_value("國定");
				listEHF020800T1_13.add(bform);

				request.setAttribute("listEHF020800T1_13", listEHF020800T1_13);
			}catch(Exception e) {
				System.out.println(e);
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}

	}
	
	/**
	 * 確認
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		EHF020801M0F Form = (EHF020801M0F) form;

		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		AuthorizedBean authbean = getLoginUser(request);
		
		Calendar cur_system_cal = Calendar.getInstance();
		// 設定系統時間
		cur_system_cal.set(Calendar.HOUR_OF_DAY, 8);
		cur_system_cal.set(Calendar.MINUTE, 0);
		cur_system_cal.set(Calendar.SECOND, 0);
		cur_system_cal.set(Calendar.MILLISECOND, 0);
		
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			//執行確認
			
			//建立EHF020801元件
			EHF020801 ehf020801 = new EHF020801();
			
			//建立細項查詢資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);

			//設定使用者資訊
			dataMap.putAll(paramMap);
			//執行確認
			ehf020801.confirm(dataMap);

			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			//取得加班記錄 use 加班單
			overtime_list = ehf020801.queryOVList(dataMap);
			
			try {
				Map<String, String> Map = null;
				Map<String, String> return_map = null;

				Iterator<Map<String, String>> it = overtime_list.iterator();

				while (it.hasNext()) {

					Map = it.next();
					//轉換Map Data
					Map = ems_ov_ctrl.mappingOVMapData(Map);
					//重產考勤
					ehf020801.Again_Produce_att(conn,Map.get("EHF020801T0_04"),Map.get("EHF020801T0_02"),authbean.getCompId(),authbean.getUserId());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			//關閉EHF020801 元件
			ehf020801.close();
			ems_ov_ctrl.close();
			// 更新資料庫
			conn.commit();
						
			//處理顯示訊息
			request.setAttribute(super.msg_edit_config, "確認成功!!");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				conn.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		return this.queryForm(mapping, Form, request, response);
	}
	
	/**
	 * 還原
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward recovery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		EHF020801M0F Form = (EHF020801M0F) form;
		EMS_DB_CONTROLLER ems_db_c = null;
		
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			//執行還原
			
			//建立EHF020801元件
			EHF020801 ehf020801 = new EHF020801();
			
			//建立細項查詢資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);

			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行還原
			ehf020801.recovery(dataMap);
			
			//關閉EHF020801 元件
			ehf020801.close();
			
			//處理顯示訊息
			request.setAttribute(super.msg_edit_config, "還原成功!!");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		return this.queryForm(mapping, Form, request, response);
	}

}
