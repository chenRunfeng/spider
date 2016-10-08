package scheduler;

import org.apache.http.annotation.ThreadSafe;

import com.gargoylesoftware.htmlunit.WebRequest;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Basic Scheduler implementation.<br>
 * Store urls to fetch in LinkedBlockingQueue and remove duplicate urls by HashMap.
 *
 * @author  <br>
 * @since 0.1.0
 */
@ThreadSafe
public class QueueScheduler implements Scheduler{

    private BlockingQueue<WebRequest> queue = new LinkedBlockingQueue<WebRequest>();

    @Override
    public void push(WebRequest request) {
        queue.add(request);
    }

    @Override
    public synchronized WebRequest poll() {
        return queue.poll();
    }
}
