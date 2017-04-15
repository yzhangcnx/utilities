/**
 * 
 */
package com.yong.report;

import java.util.Date;

/**
 * @author yongzhang
 *
 */
public class DataSourceBean
{
  private int criteria_id;
  private Date start_date;
  private Date end_date;
  private String state;
  private String prod_line;
  private String label;
  private int subregion_id;
  private Date version;
  
  public int getCriteria_id()
  {
    return criteria_id;
  }
  public void setCriteria_id(int criteria_id)
  {
    this.criteria_id = criteria_id;
  }
  public Date getEnd_date()
  {
    return end_date;
  }
  public void setEnd_date(Date end_date)
  {
    this.end_date = end_date;
  }
  public String getLabel()
  {
    return label;
  }
  public void setLabel(String label)
  {
    this.label = label;
  }
  public String getProd_line()
  {
    return prod_line;
  }
  public void setProd_line(String prod_line)
  {
    this.prod_line = prod_line;
  }
  public Date getStart_date()
  {
    return start_date;
  }
  public void setStart_date(Date start_date)
  {
    this.start_date = start_date;
  }
  public String getState()
  {
    return state;
  }
  public void setState(String state)
  {
    this.state = state;
  }
  public int getSubregion_id()
  {
    return subregion_id;
  }
  public void setSubregion_id(int subregion_id)
  {
    this.subregion_id = subregion_id;
  }
  public Date getVersion()
  {
    return version;
  }
  public void setVersion(Date version)
  {
    this.version = version;
  }
}
