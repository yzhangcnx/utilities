package com.yong.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.yong.hibernate.Contact;
import com.yong.hibernate.URL;

public class Main
{ 
  public static void main(String[] args)
  {
    /*
    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    Session session = factory.getCurrentSession();
    session.beginTransaction();
    */
    /*
    Contact c = new Contact();
    c.setFirstName("Hi");
    c.setLastName("Bernate");
    session.save(c);
    */
    /*
    Contact c1 = (Contact)session.load(Contact.class, 1);
    System.out.println(c1.getFirstName() + " " + c1.getLastName());
    
    URL u1 = new URL();
    */
    //u1.setId(-9998);
    //u1.setContactId(1967);
    /*
    u1.setUrl("http://127.0.0.1:18080/");
    u1.setVersion(new Date());
    //session.saveOrUpdate(u1);
    
    URL u2 = new URL();
    //u2.setId(-9998);
    //u2.setContactId(1967);
    u2.setUrl("http://localhost:18080/");
    u2.setVersion(new Date());
    //session.saveOrUpdate(u2);
    
    c1.getUrls().add(u1);
    c1.getUrls().add(u2);
    
    session.saveOrUpdate(u1);
    session.saveOrUpdate(u2);
    session.saveOrUpdate(c1);
    session.getTransaction().commit();
    
    session = factory.getCurrentSession();
    session.beginTransaction();
    Query query = session.createQuery("from URL in class URL");
    List results = query.list();
    for(int i = 0; i < results.size(); i++)
    {
      System.out.println((URL)results.get(i));
    }
    
    session = factory.getCurrentSession();
    session.beginTransaction();
    Contact c2 = (Contact)session.load(Contact.class, 1);
    session.delete(c2);
    session.getTransaction().commit();
    /*
    URL u4 = (URL)session.load(URL.class, -9999);
    System.out.println(u4);
    */
    //java.net.URL  u1 = Thread.currentThread().getContextClassLoader().getResource("ExemplarSetup1.doc");
    java.net.URL  u1 = Main.class.getResource("ExemplarSetup1.doc");
    File f1 = new File(u1.getFile());
    //java.net.URL  u2 = Thread.currentThread().getContextClassLoader().getResource("ExemplarSetup2.doc");
    java.net.URL  u2 = Main.class.getResource("ExemplarSetup3.doc");
    File f2 = new File(u2.getFile());
    
    byte [] a1 = new byte [(int)f1.length()];
    byte [] a2 = new byte [(int)f2.length()];
    
    FileInputStream fi1 = null;
    FileInputStream fi2 = null;
    
    try {
      fi1 = new FileInputStream(f1);
      fi2 = new FileInputStream(f2);
      
      fi1.read(a1);
      fi2.read(a2);
      
      
      Date start, equal, end;
      start = new Date();
      if(Arrays.equals(a1, a2))
      {
        equal = new Date();
        System.out.println("Compare Time " + (equal.getTime() - start.getTime()));
      }
      end = new Date();
      System.out.println("Total Time " + (end.getTime() - start.getTime()));
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        if(fi2 != null) {
          fi2.close();
        }
      }
      catch(Exception e) {}
      try {
        if(fi1 != null) {
          fi1.close();
        }
      }
      catch(Exception e) {}
    }
    /*
    byte [] c = {1, 2, 3, 4, 'a'};
    byte [] d = new byte[] {1, 2, 3, 4, "a".getBytes()[0]};
    if(Arrays.equals(c, d) && null == null)
    {
      System.out.println("Equal");
    }
    else
    {
      System.out.println("Not Equal");
    }
    */
  }
}
