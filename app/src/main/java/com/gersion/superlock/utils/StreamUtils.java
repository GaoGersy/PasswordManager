  
package com.gersion.superlock.utils;

import java.io.Closeable;
import java.io.IOException;

/** 
 * ClassName:StreamUtils <br/>
 * Function: 关于IO流的关闭 <br/> 
 * Date:     2016年7月10日 下午10:33:36 <br/> 
 * @author   Ger 
 * @version       
 */
public class StreamUtils {

    public static void closeStream(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            closeable =null;
        }
    }
}
  