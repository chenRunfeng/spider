package scheduler;

import com.gargoylesoftware.htmlunit.WebRequest;
/*
 * Scheduler is the part of url management.
 * You can implement interface Scheduler to do:
 * manage urls to fetch
 * remove duplicate urls
 *  @author Mr.Chen RunFENG
 */
public interface Scheduler {

	 /**
     * add a url to fetch
     *
     * @param request request
     * @param task task
     */
    public void push(WebRequest request);

    /**
     * get an url to crawl
     *
     * @param task the task of spider
     * @return the url to crawl
     */
    public WebRequest poll();
    public boolean isEmpty();
    public void clear();
}
