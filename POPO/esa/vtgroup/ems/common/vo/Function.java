package vtgroup.ems.common.vo;

public class Function
{
  public String name;
  public boolean status;
  public String id;
  public String type;
  public String value;

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public boolean isStatus()
  {
    return this.status;
  }

  public void setStatus(boolean paramBoolean)
  {
    this.status = paramBoolean;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }
}