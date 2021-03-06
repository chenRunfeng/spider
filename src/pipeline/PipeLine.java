/**
 * 
 */
package pipeline;

import common.ResultItem;

/**
 * Pipeline is the persistent and offline process part of crawler.
 * The interface Pipeline can be implemented to customize ways of persistent.
 * @author Mr.Chen RunFENG
 *
 */
public interface PipeLine {

	/**
     * Process extracted results.
     *
     * @param resultItems resultItems
     */
    public void process(ResultItem resultItem);
    public void close();
}
