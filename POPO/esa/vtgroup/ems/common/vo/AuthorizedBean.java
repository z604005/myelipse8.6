package vtgroup.ems.common.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AuthorizedBean
{
  Map a = new HashMap();
  List ModuleList = new ArrayList();
  List MenuList = new ArrayList();
  boolean Change = false;
  String CompId;
  String UserGroup;
  String UserCode;
  String UserId;
  String EmployeeID;
  String UserName;
  String ManagerFlag;

  public String getUserName()
  {
    return this.UserName;
  }

  public void setUserName(String paramString)
  {
    this.UserName = paramString;
  }

  public String getUserId()
  {
    return this.UserId;
  }

  public void setUserId(String paramString)
  {
    this.UserId = paramString;
  }

  public String getUserCode()
  {
    return this.UserCode;
  }

  public void setUserCode(String paramString)
  {
    this.UserCode = paramString;
  }

  public String getCompId()
  {
    return this.CompId;
  }

  public void setCompId(String paramString)
  {
    this.CompId = paramString;
  }

  public String getUserGroup()
  {
    return this.UserGroup;
  }

  public void setUserGroup(String paramString)
  {
    this.UserGroup = paramString;
  }

  public String getEmployeeID()
  {
    return this.EmployeeID;
  }

  public void setEmployeeID(String paramString)
  {
    this.EmployeeID = paramString;
  }

  public List getModuleList()
  {
    return this.ModuleList;
  }

  public List getMenuList()
  {
    return this.MenuList;
  }

  public AuthorizedBean add(Module paramModule)
  {
    this.a.put(paramModule.getId(), paramModule);
    return this;
  }

  public void addList(Module paramModule)
  {
    this.ModuleList.add(paramModule);
  }

  public void addMenu(Module paramModule)
  {
    this.MenuList.add(paramModule);
  }

  public void setChange(boolean paramBoolean)
  {
    this.Change = paramBoolean;
  }

  public boolean isChange()
  {
    return this.Change;
  }

  public String getManagerFlag()
  {
    return this.ManagerFlag;
  }

  public void setManagerFlag(String paramString)
  {
    this.ManagerFlag = paramString;
  }

  public void reload()
  {
  }

  public boolean isOk(String paramString1, String paramString2)
  {
    boolean bool = false;
    Iterator localIterator = this.a.values().iterator();
    while (localIterator.hasNext())
    {
      Module localModule = (Module)localIterator.next();
      if (localModule.getActionName() != null)
        if (a(paramString1, paramString2, localModule))
        {
          bool = true;
          break;
        }
    }
    return bool;
  }

  private boolean a(String paramString1, String paramString2, Module paramModule)
  {
    boolean bool = false;
    String str = paramModule.getActionName();
    if ((str != null) && (!"null".equalsIgnoreCase(str)) && (paramString1 != null) && (str.length() >= paramString1.length()) && (paramString1.equalsIgnoreCase(str.substring(0, paramString1.length()))))
      bool = isAllow(paramModule.getId(), paramString2);
    return bool;
  }

  public boolean isAllow(String paramString1, String paramString2)
  {
    if (isChange())
    {
      reload();
      this.Change = false;
    }
    boolean bool = false;
    if (paramString1 == null)
      return bool;
    if (this.a.containsKey(paramString1))
    {
      Module localModule = (Module)this.a.get(paramString1);
      List localList = localModule.getFunctionList();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Function localFunction = (Function)localIterator.next();
        if ((paramString2 != null) && (paramString2.equalsIgnoreCase(localFunction.getValue())))
          bool = true;
      }
    }
    return bool;
  }

  public String getModuleName(String paramString)
  {
    Module localModule = (Module)this.a.get(paramString);
    return localModule.getName();
  }
}