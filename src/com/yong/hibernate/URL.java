package com.yong.hibernate;

import java.util.Date;

public class URL
{
  private int id;
  private int contactId;
  private String url;
  private Date version;
  
  public URL()
  {}
  
  public String toString()
  {
    return "URL_ID:\t\t" + getId() + "\nCONTACT_ID:\t\t" + getContactId() +
      "\nURL_VALUE:\t\t" + getUrl() + "\nVERSION:\t\t" + getVersion();
  }
  public int getContactId()
  {
    return contactId;
  }

  public void setContactId(int contactId)
  {
    this.contactId = contactId;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
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
