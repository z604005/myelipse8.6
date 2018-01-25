package vtgroup.ems.common.vo;

public class LogInfoBean
{
  public static String LOGIN = "0000";
  public static String LOGOUT = "9999";
  public static String CREATE = "0001";
  public static String UPDATE = "0002";
  public static String DELETE = "0003";
  public static String QUERY = "0004";
  public static String PRINT = "0005";
  public static String EXPORT = "0006";
  public static String IMPORT = "0007";
  public static String UPLOAD = "0008";
  public static String DOWNLOAD = "0009";
  public static String COPY = "0010";
  public static String EMAIL = "0011";
  public static String PROCEDURE = "0012";
  public static String CREATE_DTL = "0013";
  public static String UPDATE_DTL = "0014";
  public static String QUERY_DTL = "0015";
  public static String DELETE_DTL = "0016";
  public static String SET_GROUPMEMBER = "0017";
  public static String SET_GROUPFUNCTION = "0018";
  public static String SET_USERGROUP = "0019";
  public static String SET_USERFUNCTION = "0020";
  public static String SET_GROUPFUNCBTN = "0021";
  public static String SET_USERFUNCBTN = "0022";
  private String a;
  private String b;
  private String c = "";
  private String d = "";
  private String e = "";

  public String getUserID()
  {
    return this.a;
  }

  public void setUserID(String paramString)
  {
    this.a = paramString;
  }

  public String getModuleID()
  {
    return this.b;
  }

  public void setModuleID(String paramString)
  {
    this.b = paramString;
  }

  public String getFunctionID()
  {
    return this.c;
  }

  public void setFunctionID(String paramString)
  {
    this.c = paramString;
  }

  public String getButtonID()
  {
    return this.d == null ? "" : this.d;
  }

  public void setButtonID(String paramString)
  {
    if ("CREATE".equalsIgnoreCase(paramString))
      this.d = CREATE;
    else if ("UPDATE".equalsIgnoreCase(paramString))
      this.d = UPDATE;
    else if ("DELETE".equalsIgnoreCase(paramString))
      this.d = DELETE;
    else if ("QUERY".equalsIgnoreCase(paramString))
      this.d = QUERY;
    else if ("PRINT".equalsIgnoreCase(paramString))
      this.d = PRINT;
    else if ("EXPORT".equalsIgnoreCase(paramString))
      this.d = EXPORT;
    else if ("IMPORT".equalsIgnoreCase(paramString))
      this.d = IMPORT;
    else if ("UPLOAD".equalsIgnoreCase(paramString))
      this.d = UPLOAD;
    else if ("DOWNLOAD".equalsIgnoreCase(paramString))
      this.d = DOWNLOAD;
    else if ("COPY".equalsIgnoreCase(paramString))
      this.d = COPY;
    else if ("EMAIL".equalsIgnoreCase(paramString))
      this.d = EMAIL;
    else if ("PROCEDURE".equalsIgnoreCase(paramString))
      this.d = PROCEDURE;
    else if ("CREATE_DTL".equalsIgnoreCase(paramString))
      this.d = CREATE_DTL;
    else if ("UPDATE_DTL".equalsIgnoreCase(paramString))
      this.d = UPDATE_DTL;
    else if ("QUERY_DTL".equalsIgnoreCase(paramString))
      this.d = QUERY_DTL;
    else if ("DELETE_DTL".equalsIgnoreCase(paramString))
      this.d = DELETE_DTL;
    else if ("LOGIN".equalsIgnoreCase(paramString))
      this.d = LOGIN;
    else if ("LOGOUT".equalsIgnoreCase(paramString))
      this.d = LOGOUT;
    else if ("SET_GROUPMEMBER".equalsIgnoreCase(paramString))
      this.d = SET_GROUPMEMBER;
    else if ("SET_GROUPFUNCTION".equalsIgnoreCase(paramString))
      this.d = SET_GROUPFUNCTION;
    else if ("SET_USERGROUP".equalsIgnoreCase(paramString))
      this.d = SET_USERGROUP;
    else if ("SET_USERFUNCTION".equalsIgnoreCase(paramString))
      this.d = SET_USERFUNCTION;
    else
      this.d = paramString;
  }

  public String getMsg()
  {
    return this.e;
  }

  public void setMsg(String paramString)
  {
    this.e = paramString;
  }
}