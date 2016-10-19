/**
 * 
 */
package pageprocessor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
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
	public ArrayList<ResultItem> lisresultitem=new ArrayList<ResultItem>();
	private static  Spider spider;
	private static int intpagesum;
	private static int pagenum;//������¼ץȡ��ҳ�����
	private boolean isFistPage=true;
	private static String sumary;
	@Override
	public void process(HtmlPage page) {
		if (isFistPage==true) {
			pagenum=1;
			java.util.List<?> lsthtmlDivElement=page.getByXPath("//div[@class='pagerTitleCell']");
		       HtmlDivision htmlDivElement=(HtmlDivision)lsthtmlDivElement.get(0);
		       String divtextString=htmlDivElement.getTextContent();
		       Pattern pattern=Pattern.compile("[^0-9]");
		       Matcher m = pattern.matcher(divtextString); 
		       intpagesum=Integer.valueOf(m.replaceAll("").trim());
		       isFistPage=false;
		}		 
		java.util.List<?> LhtmlInput=page.getByXPath("//table[@class='GridTableContent']/tbody/tr/td[10]/div/input|//a[@class='fz14']");
		if (LhtmlInput.isEmpty()==false) {
	        for (int i=0;i<LhtmlInput.size();i+=2) {
	        	HtmlAnchor htmlAnchor=(HtmlAnchor)LhtmlInput.get(i);
	        	HtmlInput htmlInput=(HtmlInput)LhtmlInput.get(i+1);
	        	String outstrvalue=htmlInput.getDefaultValue();
	        	String[] lsStrings=outstrvalue.split("[|]");
	        	String outString=lsStrings[0];
	        	String nexturl="http://www.cnki.net/"+htmlAnchor.getHrefAttribute().replace("kns", "KCMS");
	        	String refererString=page.getUrl().toString();
	        	String[] lisStrings=outString.split("-");
				ResultItem resultItem=new ResultItem();				
		        try {
					spider.getNextPage(nexturl, refererString);
				} catch (MalformedURLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
		        catch (NullPointerException e) {
					// TODO: handle exception
		        	return;
				}
		        resultItem.put("����", lisStrings[0]);
		        resultItem.put("����", lisStrings[1]);
		        resultItem.put("����", lisStrings[2]);
		        resultItem.put("ʱ��", lisStrings[3]+"-"+lisStrings[4]+"-"+lisStrings[5]);
		        resultItem.put("ժҪ", sumary);
		        lisresultitem.add(resultItem);
		        //System.out.print(lisStrings[0]+"\r\n"+sumary+"\r\n");
		        }
	        pagenum++;
		//System.out.println(i+"\r\n");
		}
		else {
			Spider.Stop();
		}
	};
    @Override 
    public String getStarturl() {
    	return this.starturl;
    };
    @Override
    public void nextprocess(HtmlPage page){
    	DomElement span=page.getElementById("ChDivSummary");
    	if (span==null) {
			HtmlSpan htmlSpan=page.getFirstByXPath("//div[@class='summary pad10']/p[3]/span");
			if (htmlSpan==null) {
				return;
			}
			htmlSpan.asText();
		}
    	else {
        	sumary=span.asText();
		}
    	
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startmain();
	}
	public static void startmain() {
		// TODO �Զ����ɵķ������		 
		System.out.println("---------------------��ӭʹ��֪����������-----------------------");
		System.out.println("�������£�");
		System.out.println("1.����ؼ��֣����س�����ʼץȡ֪�����ݡ�\r\n2.�ڡ�����ҳ����������������ҳ�룬�����ͬ��ҳ��\r\n3.������ҳ���������������������������s���洢��ǰ��ȡ�����ݣ���r����������ؼ���");
		Scanner scanner=new Scanner(System.in);
        System.out.println("������ؼ��֣�");
        String keyword=scanner.nextLine();
        if (keyword!="") {
//        	try {
//				Runtime.getRuntime().exec("cls");
//			} catch (IOException e) {
//				// TODO �Զ����ɵ� catch ��
//				e.printStackTrace();
//				//throw e;
//			}  
            CNZPageProcessor cnzPageProcessor=new CNZPageProcessor();
    		cnzPageProcessor.new crawlThread(keyword,cnzPageProcessor).start();	
    		try {
    			System.out.println("���Ժ򡣡���������");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
    		while (cnzPageProcessor.lisresultitem.size()<20) {
    			System.out.print("��");	
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
    		cnzPageProcessor.show(1);
    		cnzPageProcessor.disPlay(scanner);
		}
        else {
			System.out.println("��Ǹ��������ؼ��֣���");
		}
	}
	public  void disPlay(Scanner scanner) {
		System.out.println("�����������ҳ�룺");
		String strin=scanner.nextLine();
		if (strin.equals("s")) {
			Spider.Stop();
			spider.savetomysql(lisresultitem);
			System.out.println("����ɹ�����");
		} 
		else if(strin.equals("r")) {
			Spider.Stop();
			lisresultitem.clear();
			startmain();
		}
		else {
			try {  
			     int intpage= Integer.parseInt(strin);  
			     if (intpage<intpagesum) {
					show(intpage);
				} else {
		            System.out.println("����ҳ�뷶Χ,��"+intpagesum+"ҳ");
		            disPlay(scanner);
				}
			} catch (NumberFormatException e) {  
			      System.out.println("������������ҳ�벻��ȷ");
			      disPlay(scanner);
			}
		}
	}
	public static void startcrawl(String keyword,
			CNZPageProcessor cnzPageProcessor, Scheduler scheduler)
			throws UnsupportedEncodingException, IOException,
			MalformedURLException {
		String Dbcatalog = URLEncoder.encode("�й�ѧ��������������ܿ�", "utf-8");
		 String searchurl;
		searchurl = cnzPageProcessor.getSearchURL(Dbcatalog, keyword);
		spider.Start(searchurl);
		 String Stimemillis=String.valueOf(System.currentTimeMillis());
		scheduler.push(spider.getwebrequest(cnzPageProcessor.getpage1url(keyword, Stimemillis), cnzPageProcessor.getStarturl()));
		spider.Run();
		int i=intpagesum/20+1;
		if (pagenum>i) {
			return;
		}
		for (int j = pagenum; j <=i; j++) {
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
		scheduler.clear();
		startcrawl(keyword, cnzPageProcessor, scheduler);
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
	public void show(int i){
		int index=(i-1)*20;
		for (int j =index; j < index+20&&j<lisresultitem.size(); j++) {
			ResultItem resultItem=lisresultitem.get(j);
			Map<String, Object> m=resultItem.getAll();
			for (String key : m.keySet()) {
				String strout=key+"��"+m.get(key);
				System.out.println(strout);
			}
			System.out.println("\r\n");
		}
	}
	public class crawlThread extends Thread{
		private String keyString;
		private CNZPageProcessor cnzPageProcessor;
		public crawlThread(String keyword,CNZPageProcessor cPageProcessor){
			keyString=keyword;
			cnzPageProcessor=cPageProcessor;
		}
		public void run() {
			try {			

				//CNZPageProcessor cnzPageProcessor=new CNZPageProcessor();
		         spider=Spider.create(cnzPageProcessor);
		         Scheduler scheduler=new QueueScheduler();
		         spider.setSceduler(scheduler);
		         startcrawl(keyString, cnzPageProcessor, scheduler);
				
			} catch (UnsupportedEncodingException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (FailingHttpStatusCodeException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} 
	   } 
	}
}
