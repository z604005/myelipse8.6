package com.spon.ems.com.actions;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.com.forms.EHF000100M0F;
import com.spon.ems.com.models.EHF000100;



/**
 * 公司基本資料
 * @author SPONPC2
 *
 */
public class EHF000100M0A extends EditAction {
	
	protected boolean setFormTypeData(Connection conn, ActionForm form, Map paramMap){
		
		EHF000100M0F Form = (EHF000100M0F)form;
		
		try{
			//設定Struts-Layout顯示模式
			if("save".equals(Form.getChangeFormType())){
				paramMap.put(super.EMS_STRUTSLAYOUT_MODE, super.StrutsLayoutInspectMode);
			}else if("edit".equals(Form.getChangeFormType())){
				paramMap.put(super.EMS_STRUTSLAYOUT_MODE, super.StrutsLayoutEditMode);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Map executeQueryEditData(Connection conn, String[] key,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		return executeQueryData(conn, form, paramMap);
	}

	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF000100M0F Form = (EHF000100M0F)form;
		
		try{
			//建立EHF000100元件
			EHF000100 ehf000100 = new EHF000100();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap.put("EHF000100T0_01", Form.getEHF000100T0_01().toString());	//公司代碼
			dataMap.put("EHF000100T0_02", Integer.valueOf(Form.getEHF000100T0_02()));  //統一編號
			dataMap.put("EHF000100T0_03", Form.getEHF000100T0_03().toString());	 //公司名稱(中)
			dataMap.put("EHF000100T0_04", Form.getEHF000100T0_04()==null?"":Form.getEHF000100T0_04());  //公司名稱(英)
			dataMap.put("EHF000100T0_05", Form.getEHF000100T0_05().toString());  //公司簡稱
			dataMap.put("EHF000100T0_06", Form.getEHF000100T0_06().toString());  //成立日期
			dataMap.put("EHF000100T0_07", Form.getEHF000100T0_07().toString());  //負責人
			dataMap.put("EHF000100T0_08", Form.getEHF000100T0_08()==null?"":Form.getEHF000100T0_08());  //聯絡人
			dataMap.put("EHF000100T0_09", Form.getEHF000100T0_09().toString());  //登記地址(中)
			dataMap.put("EHF000100T0_10", Form.getEHF000100T0_10()==null?"":Form.getEHF000100T0_10());  //登記地址(英)
			dataMap.put("EHF000100T0_11", Form.getEHF000100T0_11()==null?"":Form.getEHF000100T0_11());  //營業地址(中)
			dataMap.put("EHF000100T0_12", Form.getEHF000100T0_12()==null?"":Form.getEHF000100T0_12());  //營業地址(英)
			dataMap.put("EHF000100T0_13", Form.getEHF000100T0_13_TAC()+"-"+Form.getEHF000100T0_13_PN());  //電話(代表號)						
			dataMap.put("EHF000100T0_16", Form.getEHF000100T0_16()==null?"":Form.getEHF000100T0_16());  //電子郵件
			dataMap.put("EHF000100T0_17", Form.getEHF000100T0_17()==null?"":Form.getEHF000100T0_17());  //公司簡介
			dataMap.put("EHF000100T0_18", Form.getEHF000100T0_03().toString());  //
			dataMap.put("EHF000100T0_19", Form.getEHF000100T0_01().toString());  //
			dataMap.put("EHF000100T0_20", Form.getEHF000100T0_03().toString()+"("+Form.getEHF000100T0_01().toString()+")");  //
			
			if("".equals(Form.getEHF000100T0_14_TAC())){
				dataMap.put("EHF000100T0_14", Form.getEHF000100T0_14_PN()==null?"":Form.getEHF000100T0_14_PN());  //電話二			
			}else{
				dataMap.put("EHF000100T0_14", Form.getEHF000100T0_14_TAC()+"-"+Form.getEHF000100T0_14_PN());  //電話二
			}
			
			if("".equals(Form.getEHF000100T0_15_TAC())){
				dataMap.put("EHF000100T0_15", Form.getEHF000100T0_15_PN()==null?"":Form.getEHF000100T0_15_PN());  //傳真			
			}else{
				dataMap.put("EHF000100T0_15", Form.getEHF000100T0_15_TAC()+"-"+Form.getEHF000100T0_15_PN());  //傳真
			}
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000100.saveData(dataMap);
			
			//關閉EHF000100元件
			ehf000100.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000100T0_01());
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

	}

	@Override
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		return null;
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
		
		EHF000100M0F Form = (EHF000100M0F)form;
		Form.setChangeFormType("init");
		
		try{
			Form = (EHF000100M0F)executeQueryData(conn, Form, paramMap).get("FORM");
			
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
		
		EHF000100M0F Form = (EHF000100M0F)form;		
		boolean chk_flag = false;		
		Map queryCondMap = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF000100元件
			EHF000100 ehf000100 = new EHF000100();
			
			//建立查詢 Condition Map
			queryCondMap = new HashMap();
			
			//處理條件設定
			queryCondMap.put("COMP_ID", comp_id);
			queryCondMap = ehf000100.queryEditData(queryCondMap);
			
			if(!"".equals(queryCondMap.get("HR_CompanySysNo")) && queryCondMap.get("HR_CompanySysNo")!=null){	//已有資料
				
				if(!"".equals(queryCondMap.get("EHF000100T0_13").toString()) && queryCondMap.get("EHF000100T0_13").toString() != null){
					String phone_num_1[] = (queryCondMap.get("EHF000100T0_13").toString()).split("-");
					Form.setEHF000100T0_13_TAC(phone_num_1[0]);
					Form.setEHF000100T0_13_PN(phone_num_1[1]);
				}else{
					Form.setEHF000100T0_13_TAC("");
					Form.setEHF000100T0_13_PN("");
				}
				
				if(!"".equals(queryCondMap.get("EHF000100T0_14").toString()) && queryCondMap.get("EHF000100T0_14").toString() != null){
					if((queryCondMap.get("EHF000100T0_14").toString()).indexOf("-") != -1){
						String phone_num_2[] = (queryCondMap.get("EHF000100T0_14").toString()).split("-");
						Form.setEHF000100T0_14_TAC(phone_num_2[0]);
						Form.setEHF000100T0_14_PN(phone_num_2[1]);
					}else{
						Form.setEHF000100T0_14_TAC("");
						Form.setEHF000100T0_14_PN(queryCondMap.get("EHF000100T0_14").toString());
					}
				}else{
					Form.setEHF000100T0_14_TAC("");
					Form.setEHF000100T0_14_PN("");
				}
				
				if(!"".equals(queryCondMap.get("EHF000100T0_15").toString()) && queryCondMap.get("EHF000100T0_15").toString() != null){
					if((queryCondMap.get("EHF000100T0_15").toString()).indexOf("-") != -1){
						String fax[] = (queryCondMap.get("EHF000100T0_15").toString()).split("-");
						Form.setEHF000100T0_15_TAC(fax[0]);
						Form.setEHF000100T0_15_PN(fax[1]);
					}else{
						Form.setEHF000100T0_15_TAC("");
						Form.setEHF000100T0_15_PN(queryCondMap.get("EHF000100T0_15").toString());
					}
				}else{
					Form.setEHF000100T0_15_TAC("");
					Form.setEHF000100T0_15_PN("");
				}
				
				Form.setHR_CompanySysNo(queryCondMap.get("HR_CompanySysNo").toString());
				Form.setEHF000100T0_01(queryCondMap.get("EHF000100T0_01").toString());
				Form.setEHF000100T0_02(queryCondMap.get("EHF000100T0_02").toString());
				Form.setEHF000100T0_03(queryCondMap.get("EHF000100T0_03").toString());
				Form.setEHF000100T0_04(queryCondMap.get("EHF000100T0_04").toString());
				Form.setEHF000100T0_05(queryCondMap.get("EHF000100T0_05").toString());
				Form.setEHF000100T0_06(queryCondMap.get("EHF000100T0_06").toString());
				Form.setEHF000100T0_07(queryCondMap.get("EHF000100T0_07").toString());
				Form.setEHF000100T0_08(queryCondMap.get("EHF000100T0_08").toString());
				Form.setEHF000100T0_09(queryCondMap.get("EHF000100T0_09").toString());
				Form.setEHF000100T0_10(queryCondMap.get("EHF000100T0_10").toString());
				Form.setEHF000100T0_11(queryCondMap.get("EHF000100T0_11").toString());
				Form.setEHF000100T0_12(queryCondMap.get("EHF000100T0_12").toString());								
				Form.setEHF000100T0_16(queryCondMap.get("EHF000100T0_16").toString());
				Form.setEHF000100T0_17(queryCondMap.get("EHF000100T0_17").toString());
				Form.setEHF000100T0_18(queryCondMap.get("EHF000100T0_18").toString());
				Form.setEHF000100T0_19(queryCondMap.get("EHF000100T0_19").toString());
				Form.setEHF000100T0_20(queryCondMap.get("EHF000100T0_20").toString());
				Form.setDATE_CREATE(queryCondMap.get("DATE_CREATE").toString());
				Form.setHR_LastUpdateDate(queryCondMap.get("HR_LastUpdateDate").toString());
				Form.setVERSION(Integer.parseInt(queryCondMap.get("VERSION").toString()));
				Form.setUSER_CREATE(queryCondMap.get("USER_CREATE").toString());
				Form.setUSER_UPDATE(queryCondMap.get("USER_UPDATE").toString());
				
				chk_flag = true;
				
			}else{	//尚無資料，執行addDataForm()
				//executeAddData(conn, form, paramMap);
				//executeQueryData(conn, form, paramMap);
				request.setAttribute("ErrMSG", "請先新增公司別!");
			}

			//關閉EHF010107元件
			ehf000100.close();

			//Return資料
			//paramMap.put(super.EMS_STRUTSLAYOUT_MODE, super.StrutsLayoutInspectMode);
			queryCondMap.put("CHK_FLAG", chk_flag);
			queryCondMap.put("FORM", form);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return queryCondMap;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
