
package downloader;

import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
/**
 * download page 
 * @author Mr.Chen RunFENG
 *
 */
public interface Downloader {

    /**
     * Downloads web pages and store in Page object.
     *
     * @param WebRequest request
     * @return page
     */
    public HtmlPage download(WebRequest request);
}
