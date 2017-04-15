package com.yong.hibernate;

import java.util.Set;
import java.util.TreeSet;

public class Contact
{
  private int id;
  private String firstName;
  private String lastName;
  private Set urls;
  
  public Contact() 
  {
    urls = new TreeSet();
  }
  public String getFirstName()
  {
    return firstName;
  }
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }
  public int getId()
  {
    return id;
  }
  private void setId(int id)
  {
    this.id = id;
  }
  public String getLastName()
  {
    return lastName;
  }
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
  public Set getUrls()
  {
    return urls;
  }
  public void setUrls(Set urls)
  {
    this.urls = urls;
  }
}
