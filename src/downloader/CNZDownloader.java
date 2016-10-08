package downloader;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CNZDownloader implements Downloader {
	WebClient wClient=null;
	public CNZDownloader(WebClient wClient) {
		this.wClient=wClient;
	}
   @Override
   public HtmlPage download(WebRequest request){
	   HtmlPage page = null;
	try {
		page = wClient.getPage(request);
	} catch (FailingHttpStatusCodeException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	} catch (IOException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	   return page;
   }
}
