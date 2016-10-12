/**
 * 
 */
package pageprocessor;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 *  *Interface to be implemented to customize a crawler.<br>
 * <br>
 * In PageProcessor, you can customize:
 * <br>
 * start urls and other settings in <br>
 * how the urls to fetch are detected    <br>
 * how the data are extracted and stored   <br>
 * @author Mr.Chen RunFENG
 *
 */
public interface PageProcessor {

	   /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param HtmlPage page
     */
    public void process(HtmlPage  page);
    /**
     * get the start url
     * @param String starturl
     */
    public String getStarturl();
    /**
     * subordinate url page handle
     * @param page
     */
    public void nextprocess(HtmlPage page) ;		
}
