/**
 * 
 */
package pageprocessor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scheduler.QueueScheduler;
import scheduler.Scheduler;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.*;

import common.ResultItem;
import common.Spider;

/**
 * Specific implementation class to PageProcessor for CNZ crawl
 * @author Mr.Chen RunFENG
 *
 */
public class CNZPageProcessor implements PageProcessor {
	private String starturl="http://epub.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB";
	private ArrayList<ResultItem> lisresultitem=new ArrayList<ResultItem>();
	private static  Spider spider;
	private static int intpagesum;
	private static int pagenum=0;//用来记录抓取的页面个数
	private boolean isFistPage=true;
	@Override
	public void process(HtmlPage page) {
		if (isFistPage==true) {
			java.util.List<?> lsthtmlDivElement=page.getByXPath("//div[@class='pagerTitleCell']");
		       HtmlDivision htmlDivElement=(HtmlDivision)lsthtmlDivElement.get(0);
		       String divtextString=htmlDivElement.getTextContent();
		       Pattern pattern=Pattern.compile("[^0-9]");
		       Matcher m = pattern.matcher(divtextString); 
		       intpagesum=Integer.valueOf(m.replaceAll("").trim());
		       isFistPage=false;
		}		 
		java.util.List<?> LhtmlInput=page.getByXPath("//table[@class='GridTableContent']/tbody/tr/td[10]/div/input");
		if (LhtmlInput.isEmpty()==false) {
	        for (Object object : LhtmlInput) {
	        	HtmlInput htmlInput=(HtmlInput)object;
	        	String outstrvalue=htmlInput.getDefaultValue();        	
	        	String outString=outstrvalue.substring(0, outstrvalue.indexOf("|"));
	        	String[] lisStrings=outString.split("-");
				System.out.print(lisStrings[0]+"\r\n");
		      
		        }
	        pagenum++;
		//System.out.println(i+"\r\n");
		}
		else {
			Spider.Stop();
		}
//		html.
//		page.getByXPath(arg0)
	};
    @Override 
    public String getStarturl() {
    	return this.starturl;
    };
    @Override
    public void nextprocess(HtmlPage page){
    	
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根		
		try {
			String Dbcatalog = URLEncoder.encode("中国学术文献网络出版总库", "utf-8");
			Scanner scanner=new Scanner(System.in);
			CNZPageProcessor cnzPageProcessor=new CNZPageProcessor();
	         spider=Spider.create(cnzPageProcessor);
	         Scheduler scheduler=new QueueScheduler();
	         spider.setSceduler(scheduler);
	         System.out.println("请输入关键字：");
	         String keyword=scanner.nextLine();
	         String searchurl;
			searchurl = cnzPageProcessor.getSearchURL(Dbcatalog, keyword);
			spider.Start(searchurl);
			 String Stimemillis=String.valueOf(System.currentTimeMillis());
			scheduler.push(spider.getwebrequest(cnzPageProcessor.getpage1url(keyword, Stimemillis), cnzPageProcessor.getStarturl()));
			spider.Run();
			int i=intpagesum/20+1;
			for (int j = 2; j <=i; j++) {
				WebRequest webReques;
		    	   if (j==2) {
		    		   webReques=spider.getwebrequest( cnzPageProcessor.getPageURL(j), cnzPageProcessor.getpage1url(keyword, Stimemillis));
		               	
				}
		    	   else {
		    		   int pj=j-1;
		    		   webReques=spider.getwebrequest(cnzPageProcessor.getPageURL(j), cnzPageProcessor.getPageURL(pj));        	                         
		           
				}
		    	   scheduler.push(webReques);
		    	   }
			spider.Run();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (FailingHttpStatusCodeException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}      
	}
	public  String  getSearchURL(String DbCatalog,String txt_1_value1) throws UnsupportedEncodingException {
    	Date date=new Date();
        String sdate=date.toString();
        //String sData=URLEncoder.encode(sdate.substring(0, sdate.lastIndexOf('C'))+"GMT+0800", "utf-8") ;
        String sData=URLEncoder.encode(sdate.substring(0, sdate.lastIndexOf('C'))+"GMT+0800","utf-8");
        String sDAte=sData.replaceAll("[+]", "%20");
        String txt_1_relation=URLEncoder.encode("#CNKI_AND", "utf-8");
    	String searchurl0="http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=undefined&NaviCode=*&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog="+URLEncoder.encode(DbCatalog, "utf-8") +"&ConfigFile=SCDB.xml&db_opt=CJFQ,CJFN,CDFD,CMFD,CPFD,IPFD,CCND,CCJD,HBRD&base_special1=%25&magazine_special1=%25&txt_1_sel=SU&txt_1_value1="+URLEncoder.encode(txt_1_value1, "utf-8") +"&txt_1_relation="+txt_1_relation+"&txt_1_special1=%3D&his=0&__="+sDAte;
    	return searchurl0;
	}
	public  String getpage1url(String keyvalue,String timeString) throws UnsupportedEncodingException {
		String page1url="http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t="+timeString+"&keyValue="+URLEncoder.encode(keyvalue, "utf-8")+"&S=1";
		return page1url;
	}
	public  String getPageURL(int i) {
		String paStringurl="http://epub.cnki.net/kns/brief/brief.aspx?curpage="+i+"&RecordsPerPage=20&QueryID=0&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_result_aspx";
		return paStringurl;
	}
}
