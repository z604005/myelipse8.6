package vtgroup.ems.common.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module extends Function
{
  public static final String DISPLAY = "1";
  public static final String ACTION = "2";
  public String actionName;
  public String parentId;
  private Map a = new HashMap();

  public String getActionName()
  {
    return this.actionName;
  }

  public void setActionName(String paramString)
  {
    this.actionName = paramString;
  }

  public Map getMap()
  {
    return this.a;
  }

  public void setMap(Map paramMap)
  {
    this.a = paramMap;
  }

  public List getFunctionList()
  {
    return new ArrayList(this.a.values());
  }

  public Map add(Function paramFunction)
  {
    this.a.put(paramFunction.getId(), paramFunction);
    return this.a;
  }

  public Map add(String paramString1, boolean paramBoolean, String paramString2)
  {
    Function localFunction = new Function();
    localFunction.setId(paramString1);
    localFunction.setStatus(paramBoolean);
    localFunction.setType(paramString2);
    return add(localFunction);
  }

  public String getParentId()
  {
    return this.parentId;
  }

  public void setParentId(String paramString)
  {
    if (("null".equalsIgnoreCase(paramString)) || (paramString == null))
      this.parentId = null;
    else
      this.parentId = paramString;
  }
}