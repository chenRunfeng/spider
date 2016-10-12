package common;
import java.util.LinkedHashMap;
import java.util.Map;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;

/**
 * 
 */

/**
 * the class is used to get the result
 * @author Mr.Chen RunFENG
 *
 */
public class ResultItem {
//  public ResultItem(String t,String a,String ty,String ti,String bstract){
//	  Title=t;
//	  Author=a;
//	  Type=ty;
//	  Time=ti;
//	  Abstract=bstract;
//  }
//  public String Title;
//  public String Author;
//  public String Type;
//  public String Time;
//  public String Abstract;
  private Map<String, Object> fields = new LinkedHashMap<String, Object>();

  private boolean skip;

  public <T> T get(String key) {
      Object o = fields.get(key);
      if (o == null) {
          return null;
      }
      return (T) fields.get(key);
  }

  public Map<String, Object> getAll() {
      return fields;
  }

  public <T> ResultItem put(String key, T value) {
      fields.put(key, value);
      return this;
  }


  /**
   * Whether to skip the result.<br>
   * Result which is skipped will not be processed by Pipeline.
   *
   * @return whether to skip the result
   */
  public boolean isSkip() {
      return skip;
  }
}
