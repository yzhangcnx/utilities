/**
 * 
 */
package com.yong.report;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author yongzhang
 *
 */
public class ReportWizard
{
	/*
  private final static String DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
  private final static String URL_STRING = "jdbc:microsoft:sqlserver://ctdb1\\dev;databasename=WPNERN_R2_2_27_01_YongZhang_UAT;SelectMethod=cursor";
  private final static String USER = "yongzhang";
  private final static String PASSWORD = "password1";
  */
	
	/*
  private final static String DRIVER = "com.jnetdirect.jsql.JSQLDriver";
  private final static String URL_STRING = "jdbc:JSQLConnect://CNXATLSQL05\\JVDEV/database=HP_R50_00_DEV_Tran";
  private final static String USER = "universal_login";
  private final static String PASSWORD = "firefly";
  */
	
  public static void main(String[] args) throws JRException, IOException, ClassNotFoundException, SQLException, DocumentException
  {
  	//JRDataSource datasource = new JREmptyDataSource();
  	
    /*
    Class.forName(DRIVER);
    Connection conn = DriverManager.getConnection(URL_STRING, USER, PASSWORD);
    Statement s = conn.createStatement();
    ResultSet rs = s.executeQuery("select 1");
    JRDataSource datasource = new JRResultSetDataSource(rs);
  	*/
  	
  	/*
    List dataSourceList = new ArrayList();
    JRDataSource beenCollectionDatasource = new JRBeanCollectionDataSource(dataSourceList);
    
    while(rs.next())
    {
      DataSourceBean bean = new DataSourceBean();
      bean.setCriteria_id(rs.getInt(1));
      bean.setStart_date(rs.getDate(2));
      bean.setEnd_date(rs.getDate(3));
      bean.setState(rs.getString(4));
      bean.setProd_line(rs.getString(5));
      bean.setLabel(rs.getString(6));
      bean.setSubregion_id(rs.getInt(7));
      bean.setVersion(rs.getDate(8));
      dataSourceList.add(bean);
    }
    */
  	/*
  	String templatePath = "C:\\Connecture\\wpe2_2_28\\development\\source\\application\\com\\rwsol\\express\\ps" +
				"\\renewals\\va\\service\\resource\\pdf\\proposal\\proposaltemplate\\";
  	String [] templateNames = {"CoverLetter_All_one_01_01_2013", "CoverLetter_All_two_01_01_2013"};
  	*/
  	String templatePath = "C:\\Connecture\\healthplus\\process\\src\\main\\java\\com\\rwsol\\exemplar\\process\\ifprenewal\\pdf\\";
  	String [] templateNames = {"CoverPage"};
  	
  	String carrierLogo = "carrierlogo.png";
  	String footerImage = "coverpagefooter.png";
  	
  	Map parameterMap = new HashMap();
  	
  	parameterMap.put("ApplicantFirstName", "Linda");
  	parameterMap.put("MailingAddress1", "123 Main Street");
  	parameterMap.put("MailingAddress2", "");
  	parameterMap.put("ApplicantCityStateZip", "Anytown, ST 00001");
  	
  	parameterMap.put("LetterCreationDate", "OCT 13, 2014");
  	parameterMap.put("ApplicantName", "Linda Smith");
  	parameterMap.put("PolicyNumber", "H20113543");
  	
  	parameterMap.put("IsAgentExist", Boolean.TRUE);
  	parameterMap.put("AgentName", "Bob Broker");
  	parameterMap.put("Agency", "AA Agency");
  	
  	parameterMap.put("CarrierLogo", new FileInputStream("C:\\Connecture\\healthplus\\process\\src\\main\\java\\com\\rwsol\\exemplar\\process\\ifprenewal\\pdf\\coverpagelogo.png"));
  	parameterMap.put("FooterImage", new FileInputStream("C:\\Connecture\\healthplus\\process\\src\\main\\java\\com\\rwsol\\exemplar\\process\\ifprenewal\\pdf\\coverpagefooter.png"));
  	
  	String outputPath = "C:\\Connecture\\temp\\pdf\\";
  	
    for(int i = 0; i < templateNames.length; i++)
    {
    	String templateName = templateNames[i];
      /*
      URL templateURL = ReportWizard.class.getResource(templateName + ".jrxml");
      JasperDesign template = JRXmlLoader.load(templateURL.openStream());
      */
    	
    	String templateFileName = templatePath + templateName + ".jrxml";
    	/*
    	File templateFile = new File(templateFileName);
    	JasperDesign template = JRXmlLoader.load(templateFile);
			*/
   
    	//JasperDesign template = JRXmlLoader.load(templateFileName);
    	
    	//JasperReport report = JasperCompileManager.compileReport(template); 

    	
    	JasperReport report = JasperCompileManager.compileReport(templateFileName);
    	
    	//JasperDesignViewer.viewReportDesign(report);
    
	    //JasperPrint print = JasperFillManager.fillReport(report, parameterMap);
	    //JasperPrint print = JasperFillManager.fillReport(report, parameterMap, conn);
	    JasperPrint print = JasperFillManager.fillReport(report, parameterMap, new JREmptyDataSource());
    
	    //JasperViewer.viewReport(print);
    
	    //byte [] renewalNoticeBytes = JasperExportManager.exportReportToPdf(print);
	    //JasperExportManager.exportReportToPdfFile(print, outputPath + templateName + ".pdf");
	    //JasperExportManager.exportReportToHtmlFile(print, templateDir.getAbsolutePath() + File.separator + "report.html");
	    //JasperExportManager.exportReportToXmlFile(print, templateDir.getAbsolutePath() + File.separator + "report.xml", false);
	    
      InputStream is = ReportWizard.class.getResourceAsStream("public-planpdfs-Signature_PPO_Silver_1Z.pdf");
	    
      List<PdfReader> readers = new ArrayList<PdfReader>();
      readers.add(new PdfReader(new ByteArrayInputStream((JasperExportManager.exportReportToPdf(print)))));
      readers.add(new PdfReader(is));
      
	    String combinedPDF = outputPath + templateName + "_combined.pdf";
	    FileOutputStream fos = new FileOutputStream(combinedPDF);
	    
	    Document document = new Document();
	    PdfWriter writer = PdfWriter.getInstance(document, fos);
	    document.open();
	    
	    PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data PdfImportedPage page;
	    
      int pageOfCurrentReaderPDF = 0;
      int readerCount = 0;

      for (PdfReader pdfReader : readers) 
      {
          readerCount++;
          if(readerCount > 1 && pdfReader.getNumberOfPages() > 1)
      		{
      			document.setPageSize(PageSize.LETTER.rotate());
      			//document.setMargins(1.0f, 20.0f, 20.0f, 20.0f);
      		}
          while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages())
          {
              document.newPage(); 
              
              pageOfCurrentReaderPDF++;
              PdfImportedPage page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
              
              cb.addTemplate(page, 0, 0);
          }
          pageOfCurrentReaderPDF = 0;
      }
      document.close();
      fos.flush();
      fos.close();   
    }
  }
}
