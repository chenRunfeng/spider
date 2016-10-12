/**
 * 
 */
package common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import pageprocessor.PageProcessor;
import pipeline.PipeLine;
import scheduler.Scheduler;
import downloader.Downloader;
import downloader.HtmlUnitDownloader;

/**
 * the class is the Aggregate scheduling class
 * @author Mr.Chen RunFENG
 *
 */
public class Spider {
  private ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); 
  protected Downloader downloader;
  protected PageProcessor pageProcessor;
  protected Scheduler scheduler;
  protected PipeLine pipeLine;
  protected String starturl;
  public static boolean isStop=false;
//创建一个webclient
  final private WebClient webClient = new WebClient(BrowserVersion.CHROME);
  //htmlunit 对css和javascript的支持不好，所以请关闭之
  private CookieManager CM = webClient.getCookieManager(); //获得cookie管理
  /**
   * create a spider with pageProcessor.
   *
   * @param pageProcessor pageProcessor
   * @return new spider
   * @see PageProcessor
   */
  public static Spider create(PageProcessor pageProcessor) {
      return new Spider(pageProcessor);
  }
  
  /**
   * create a spider with pageProcessor.
   *
   * @param pageProcessor pageProcessor
   */
  public Spider(PageProcessor pageProcessor) {
      this.pageProcessor = pageProcessor;
      this.starturl=pageProcessor.getStarturl();
      webClient.getOptions().setJavaScriptEnabled(false);
      webClient.getOptions().setCssEnabled(false);
      webClient.getCookieManager().setCookiesEnabled(true);
      this.downloader=new HtmlUnitDownloader(webClient);
  }
  public static void Stop() {
	isStop=true;
}
  public void setSceduler(Scheduler scheduler){
	  this.scheduler=scheduler;
  }
  public void Start(String url) throws FailingHttpStatusCodeException, IOException{
//	 Runnable r= new Runnable() {
//		public void run() {
	  isStop=false;
			webClient.getPage(starturl);
			scheduler.push(getwebrequest(url, starturl));
	       //webClient.getPage(getwebrequest(CM, getSearchURL(Dbcatalog, keyword), url0));
		//}
	//};
  }
  public void Run() {
//	cachedThreadPool.execute(new Runnable() {
//		public void run() {
	  while (!scheduler.isEmpty()&&isStop==false) {
		  download();
	}			
//		}
//	});
}
  public void setResultItem(String nexturl,String refererString) throws MalformedURLException {
	HtmlPage p= downloader.download(getwebrequest(nexturl, refererString)) ;
	pageProcessor.nextprocess(p);
}
  private void download(){	
	WebRequest webRequest=scheduler.poll();
//	HtmlPage page;
//	try {
//		page = webClient.getPage(webRequest);		
//	} catch (FailingHttpStatusCodeException e) {
//		// TODO 自动生成的 catch 块
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO 自动生成的 catch 块
//		e.printStackTrace();
//	}
	HtmlPage page=downloader.download(webRequest);
	if (page!=null) {
		pageProcessor.process(page);
	}
}
  public  WebRequest getwebrequest(String url,String refererString)
			throws MalformedURLException {
		StringBuilder stringBuilder = new StringBuilder();
		for (Cookie c : CM.getCookies()) {
			stringBuilder.append(c.getName()).append("=").append(c.getValue()).append(";");
		}
		stringBuilder.append("RsPerPage").append("=").append("20");
		//System.out.print(stringBuilder.toString());
		//webClient.getOptions().setJavaScriptEnabled(false);
//		WebRequest webRequestList = new WebRequest(new URL("http://epub.cnki.net/kns/brief/brief.aspx?curpage=1&RecordsPerPage=20&QueryID=0&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_result_aspx#J_ORDER"));
		URL Url=new URL(url);
		WebRequest webRequestList = new WebRequest(Url);
		webRequestList.setAdditionalHeader("Host", "epub.cnki.net");
		webRequestList.setAdditionalHeader("Cookie", stringBuilder.toString());
		webRequestList.setAdditionalHeader("Connection", "keep-alive");
		webRequestList.setAdditionalHeader("Accept","textml,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		webRequestList.setAdditionalHeader("Referer",refererString);		
		//webRequestList.setAdditionalHeader("Referer","http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t="+Rtimemillis+"&keyValue=&S=1");
		return webRequestList;
	}
}
