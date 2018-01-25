package com.spon.ems.salary.tools;

import org.apache.log4j.Logger;

import com.spon.utils.util.BA_TOOLS;

import java.sql.Connection;


/**
 * EMS 薪資計算公用元件
 * 
 * @version 1.0
 * @created 18-十月-2010 下午 06:00:47
 */
public class EMS_InsuranceTools {

	static Logger loger = Logger.getLogger(EMS_InsuranceTools.class.getName());
	
	private volatile static EMS_InsuranceTools ems_insurance_tools;
	private static BA_TOOLS tools = BA_TOOLS.getInstance();
	//private static BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
	
	private static String sta_salyymm = "";
	private static String sta_costyymm = "";
	private static String sta_user_id = "";
	private static String sta_user_name = "";
	private static String sta_comp_id = "";
	
	
	public static EMS_InsuranceTools getInstance() {
        /*if (ems_insurance_tools == null)
        	ems_insurance_tools = new EMS_InsuranceTools();*/
        if(ems_insurance_tools == null) {
            synchronized(EMS_InsuranceTools.class) {
            	ems_insurance_tools = new EMS_InsuranceTools();
            }
        }
        return ems_insurance_tools;
    }
	
	public static EMS_InsuranceTools getInstance( String salYYMM, String costYYMM, String user_id, String user_name, String comp_id) {
        /*if (ems_insurance_tools == null){
        	ems_insurance_tools = new EMS_InsuranceTools( salYYMM, costYYMM, user_id, user_name, comp_id);
        }else{
        	sta_salyymm = salYYMM;
        	sta_costyymm = costYYMM;
        	sta_user_id = user_id;
        	sta_user_name = user_name;
        	sta_comp_id = comp_id;
        }*/
        
        if(ems_insurance_tools == null) {
            synchronized(EMS_InsuranceTools.class) {
            	ems_insurance_tools = new EMS_InsuranceTools(salYYMM, costYYMM, user_id, user_name, comp_id);
            }
        }else{
        	sta_salyymm = salYYMM;
        	sta_costyymm = costYYMM;
        	sta_user_id = user_id;
        	sta_user_name = user_name;
        	sta_comp_id = comp_id;
        }
        
        return ems_insurance_tools;
    }
    
	private EMS_InsuranceTools( String salYYMM, String costYYMM, String user_id, String user_name, String comp_id){
		EMS_InsuranceTools.sta_salyymm = salYYMM;
		EMS_InsuranceTools.sta_costyymm = costYYMM;
		EMS_InsuranceTools.sta_user_id = user_id;
		EMS_InsuranceTools.sta_user_name = user_name;
		EMS_InsuranceTools.sta_comp_id = comp_id;
	}
	
	private EMS_InsuranceTools(){
	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * 計算員工勞工保險費用
	 * @param conn
	 * @param employee_id
	 * @param identity
	 * @param insured_salary
	 * @param insured_day
	 * @return
	 */
	public int countEmpLaborFee( Connection conn, String employee_id, String identity, int insured_salary, int insured_day ){
		
		int labor_fee = 0;
		
		try{
			//普通事故保險費率
			float accident_insure_rate = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "LABOR_ACCIDENT_INSURANCE_RATE"))/100);
			//就業保險費率
			float employment_insure_rate = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "LABOR_EMPLOYMENT_INSURANCE_RATE"))/100);
			//負擔比率
			float burden_rate = 0;
			
			//勞保保險天數若大於 30天則以 30天計算
			if(insured_day > 30){
				insured_day = 30;
			}
			
			//判斷身分別
			if("1".equals(identity)){
				//一般受雇勞工
				//負擔比率 = 20%
				burden_rate = (float)0.2;
				//勞工保險費 = 事故保險費 + 就業保險費
				labor_fee = this.countAccidentInsurance(insured_salary, insured_day, accident_insure_rate, burden_rate) 
							+ this.countEmploymentInsurance(insured_salary, insured_day, employment_insure_rate, burden_rate);
				
			//}else if("2".equals(identity)){
			}else if("3".equals(identity) || "4".equals(identity)){	
				//外籍受雇勞工
				//負擔比率 = 20%
				burden_rate = (float)0.2;
				//勞工保險費 = 事故保險費
				labor_fee = this.countAccidentInsurance(insured_salary, insured_day, accident_insure_rate, burden_rate);
				
			//}else if("3".equals(identity)){
			}else if("6".equals(identity)){
				//雇主
				//負擔比率 = 20%
				burden_rate = (float)0.2;
				//勞工保險費 = 事故保險費
				labor_fee = this.countAccidentInsurance(insured_salary, insured_day, accident_insure_rate, burden_rate);
				
			}else{
				//一般受雇勞工
				//負擔比率 = 20%
				burden_rate = (float)0.2;
				//勞工保險費 = 事故保險費 + 就業保險費
				labor_fee = this.countAccidentInsurance(insured_salary, insured_day, accident_insure_rate, burden_rate) 
							+ this.countEmploymentInsurance(insured_salary, insured_day, employment_insure_rate, burden_rate);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return labor_fee;
	}
	
	/**
	 * 計算投保單位勞工保險費用
	 * @param conn
	 * @param employee_id
	 * @param identity
	 * @param insured_salary
	 * @param insured_day
	 * @return
	 */
	public int countCompLaborFee( Connection conn, String employee_id, String identity, int insured_salary, int insured_day ){
		
		int comp_labor_fee = 0;
		
		try{
			//普通事故保險費率
			float accident_insure_rate = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "LABOR_ACCIDENT_INSURANCE_RATE"))/100);
			//就業保險費率
			float employment_insure_rate = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "LABOR_EMPLOYMENT_INSURANCE_RATE"))/100);
			//職業災害保險費率
			float job_damage_insure_rate = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "LABOR_JOB_DISASTER_RATE"))/100);
			//負擔比率
			float burden_rate = 0;
			
			//勞保保險天數若大於 30天則以 30天計算
			if(insured_day > 30){
				insured_day = 30;
			}
			
			//判斷身分別
			if("1".equals(identity)){
				//一般受雇勞工
				//負擔比率 = 70%
				burden_rate = (float)0.7;
				//單位勞工保險費 = 事故保險費 + 就業保險費
				comp_labor_fee = this.countAccidentInsurance(insured_salary, insured_day, accident_insure_rate, burden_rate) 
								 + this.countEmploymentInsurance(insured_salary, insured_day, employment_insure_rate, burden_rate);
				
				//職業災害保險負擔比率 = 100%
				burden_rate = (float)1.0;
				//單位勞工保險費 = 事故保險費 + 就業保險費 + 職業災害保險費
				comp_labor_fee += this.countJobDamageInsurance(insured_salary, insured_day, job_damage_insure_rate, burden_rate);
				
			}else if("2".equals(identity)){
				//外籍受雇勞工
				//負擔比率 = 70%
				burden_rate = (float)0.7;
				//勞工保險費 = 事故保險費
				comp_labor_fee = this.countAccidentInsurance(insured_salary, insured_day, accident_insure_rate, burden_rate);
				
				//職業災害保險負擔比率 = 100%
				burden_rate = (float)1.0;
				//單位勞工保險費 = 事故保險費 + 職業災害保險費
				comp_labor_fee += this.countJobDamageInsurance(insured_salary, insured_day, job_damage_insure_rate, burden_rate);
				
			}else if("3".equals(identity)){
				//雇主
				//負擔比率 = 70%
				burden_rate = (float)0.7;
				//勞工保險費 = 事故保險費
				comp_labor_fee = this.countAccidentInsurance(insured_salary, insured_day, accident_insure_rate, burden_rate);
				
				//職業災害保險負擔比率 = 100%
				burden_rate = (float)1.0;
				//單位勞工保險費 = 事故保險費 + 職業災害保險費
				comp_labor_fee += this.countJobDamageInsurance(insured_salary, insured_day, job_damage_insure_rate, burden_rate);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comp_labor_fee;
	}
	
	/**
	 * 計算勞工保險普通事故保險費用
	 * @param insured_salary
	 * @param insured_day
	 * @param accident_insure_rate
	 * @param burden_rate
	 * @return
	 */
	protected int countAccidentInsurance( int insured_salary, int insured_day, float accident_insure_rate, float burden_rate ){
		
		int accident_insurance = 0;
		
		try{
			//轉換投保天數的資料型態
			Float float_insured_day = new Float(insured_day);
			
			//勞工普通事故保險費用 = 四捨五入(投保薪資 * (加保天數/30) * 普通事故保險費率 * 負擔比率)
			accident_insurance = tools.fixNumByMode((insured_salary * (float_insured_day.floatValue()/30) * accident_insure_rate * burden_rate), "HALF_UP");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return accident_insurance;
	}
	
	/**
	 * 計算勞工保險就業保險費用
	 * @param insured_salary
	 * @param insured_day
	 * @param employment_insure_rate
	 * @param burden_rate
	 * @return
	 */
	protected int countEmploymentInsurance( int insured_salary, int insured_day, float employment_insure_rate, float burden_rate ){
		
		int employment_insurance = 0;
		
		try{
			//轉換投保天數的資料型態
			Float float_insured_day = new Float(insured_day);
			
			//勞工就業保險費用 = 四捨五入(投保薪資 * (加保天數/30) * 就業保險費率 * 負擔比率)
			employment_insurance = tools.fixNumByMode((insured_salary * (float_insured_day.floatValue()/30) * employment_insure_rate * burden_rate), "HALF_UP");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return employment_insurance;
	}
	
	/**
	 * 計算勞工保險職業災害保險費用
	 * @param insured_salary
	 * @param insured_day
	 * @param job_damage_insure_rate
	 * @param burden_rate
	 * @return
	 */
	protected int countJobDamageInsurance( int insured_salary, int insured_day, float job_damage_insure_rate, float burden_rate ){
		
		int job_damage_insurance = 0;
		
		try{
			//轉換投保天數的資料型態
			Float float_insured_day = new Float(insured_day);
			
			//勞工職業災害保險費用 = 四捨五入(投保薪資 * (加保天數/30) * 職業災害保險費率 * 負擔比率)
			job_damage_insurance = tools.fixNumByMode((insured_salary * (float_insured_day.floatValue()/30) * job_damage_insure_rate * burden_rate), "HALF_UP");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return job_damage_insurance;
	}
	
	/**
	 * 計算員工的全民健康保險費用
	 * @param conn
	 * @param employee_id
	 * @param identity
	 * @param insured_salary
	 * @param num_of_dependents
	 * @param government_subsidize_money
	 * @return
	 */
	public int countEmpHealthFee( Connection conn, String employee_id, String identity, int insured_salary, int num_of_dependents,
								  int government_subsidize_money){
		
		int health_fee = 0;
		
		try{
			//舊的全民健康保險費率
			float health_old_insure_rate = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "HEALTH_OLD_INSURANCE_RATE"))/100);
			//現在的全民健康保險費率
			float health_now_insure_rate = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "HEALTH_NOW_INSURANCE_RATE"))/100);
			//負擔比率
			float burden_rate = 0;
			//費率調整補助比率
			float subsidy_rate = 0;
			
			//全民健康保險計算公式
			//調整前應自付保險費=投保金額*保險費率（4.55%）* 負擔比率（積數四捨五入）
			//調整後應自付保險費=投保金額*保險費率（5.17%）* 負擔比率（積數四捨五入）
			//費率調整差額專案補助金額= (調整後應自付保險費－ 調整前應自付保險費) * 100%（積數後四捨五入）
			int before_health_fee = 0;
			int now_health_fee = 0;
			int health_subsidy_fee = 0;
			
			//判斷費率調整補助比率
			if( insured_salary <= 40100 ){
				subsidy_rate = (float)1.0;  //100%
			}else if( insured_salary >= 42000 && insured_salary <= 50600 ){
				subsidy_rate = (float)0.2;  //20%
			}else if( insured_salary >= 53000 ){
				subsidy_rate = (float)0;  //0%
			}
			
			//眷屬人數超過三人以三人計
			if(num_of_dependents >= 3){
				num_of_dependents = 3;
			}else if(num_of_dependents <= 0){
				num_of_dependents = 0;
			}
			
			//判斷身分別
			if("1".equals(identity)){
				//一般受雇勞工
				//負擔比率 = 30%
				burden_rate = (float)0.3;
				
			//}else if("2".equals(identity)){
			}else if("3".equals(identity) || "4".equals(identity)){	
				//外籍受雇勞工
				//負擔比率 = 30%
				burden_rate = (float)0.3;
				
			//}else if("3".equals(identity)){
			}else if("6".equals(identity)){
				//雇主
				//負擔比率 = 100%
				burden_rate = (float)1.0;
				
			}else{
				//一般受雇勞工
				//負擔比率 = 30%
				burden_rate = (float)0.3;
			}
			
			//計算全民健康保險費用
			//調整前應自付保險費
			before_health_fee = this.countHealthInsurance(insured_salary, health_old_insure_rate, burden_rate);
			//調整後應自付保險費
			now_health_fee = this.countHealthInsurance(insured_salary, health_now_insure_rate, burden_rate);
			//費率調整差額專案補助金額
			health_subsidy_fee = this.countHealthSubsidyFee(before_health_fee, now_health_fee, subsidy_rate);
			
			//全民保險費 =（調整後應自付保險費－費率調整差額專案補助金額）* (本人+眷屬人數)
			health_fee = ( now_health_fee - health_subsidy_fee ) * ( 1 + num_of_dependents );
			
			//處理政府補助金額
			if( government_subsidize_money <= 0){
				government_subsidize_money = 0;
			}
			
			//全民保險費 = 全民保險費 - 政府補助金額(由使用者輸入補助總金額)
			health_fee = health_fee - government_subsidize_money;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return health_fee;
	}
	
	/**
	 * 計算投保單位的全民健康保險費用
	 * @param conn
	 * @param employee_id
	 * @param identity
	 * @param insured_salary
	 * @return
	 */
	public int countCompHealthFee( Connection conn, String employee_id, String identity, int insured_salary ){
		
		int comp_health_fee = 0;
		
		try{
			//現在的全民健康保險費率
			float health_now_insure_rate = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "HEALTH_NOW_INSURANCE_RATE"))/100);
			//全民健康保險的平均眷屬人數
			float health_avg_dependents = (Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "HEALTH_AVG_DEPENDENTS")));
			//負擔比率
			float burden_rate = 0;
			
			//全民健康保險計算公式
			//投保單位保險費 = 投保金額*保險費率（5.17%）* 負擔比率 * ( 本人 + 平均眷屬人數 ), ps:平均眷屬人數目前為 0.7
			
			//判斷身分別
			if("1".equals(identity)){
				//一般受雇勞工
				//負擔比率 = 60%
				burden_rate = (float)0.6;
				
			}else if("2".equals(identity)){
				//外籍受雇勞工
				//負擔比率 = 60%
				burden_rate = (float)0.6;
				
			}else if("3".equals(identity)){
				//雇主
				//負擔比率 = 0%
				burden_rate = (float)0;
				
			}
			
			//計算投保單位全民健康保險費用
			//投保單位保險費 = 投保金額*保險費率（5.17%）* 負擔比率 * ( 本人 + 平均眷屬人數 ), ps:平均眷屬人數目前為 0.7
			comp_health_fee = tools.fixNumByMode(insured_salary * health_now_insure_rate * burden_rate * ( 1 + health_avg_dependents ), "HALF_UP");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return comp_health_fee;
	}
	
	/**
	 * 計算全民健康保險費用基數
	 * @param insured_salary
	 * @param health_insure_rate
	 * @param burden_rate
	 * @return
	 */
	protected int countHealthInsurance( int insured_salary, float health_insure_rate, float burden_rate ){
		
		int health_insurance = 0;
		
		try{
			//全民健康保險費用基數 = 四捨五入(投保金額 * 全民健康保險費率 * 負擔比率)
			health_insurance = tools.fixNumByMode((insured_salary * health_insure_rate * burden_rate), "HALF_UP");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return health_insurance;
	}
	
	/**
	 * 計算全民健康保險的費率調整差額專案補助金額
	 * @param before_health_fee
	 * @param now_health_fee
	 * @param subsidy_rate
	 * @return
	 */
	protected int countHealthSubsidyFee( int before_health_fee, int now_health_fee, float subsidy_rate ){
		
		int health_subsidy_fee = 0;
		
		try{
			//費率調整差額專案補助金額= (調整後應自付保險費－ 調整前應自付保險費) * 100%（積數後四捨五入）
			health_subsidy_fee = tools.fixNumByMode(( (now_health_fee - before_health_fee) * subsidy_rate ), "HALF_UP");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return health_subsidy_fee;
	}
	
}
