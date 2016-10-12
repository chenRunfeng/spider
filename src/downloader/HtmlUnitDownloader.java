package downloader;

import java.io.IOException;

import org.hamcrest.core.IsInstanceOf;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitDownloader implements Downloader {
	WebClient wClient=null;
	public HtmlUnitDownloader(WebClient wClient) {
		this.wClient=wClient;
	}
   @Override
   public HtmlPage download(WebRequest request){
	   HtmlPage page = null;
	try {
		Object o=wClient.getPage(request);
        if ( o instanceof HtmlPage) {
			HtmlPage new_name = (HtmlPage) o;
			page=new_name;
		}
        else {
			page=null;
		}
			//WebRequest r = (WebRequest) request;
			//page = wClient.getPage(request);
	} catch (FailingHttpStatusCodeException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	   return page;
   }
}
