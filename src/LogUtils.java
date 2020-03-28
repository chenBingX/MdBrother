import com.intellij.openapi.diagnostic.Logger;

public class LogUtils {

    public static final Logger LOG = Logger.getInstance("MdBrother");

    public static void e(String message){
        LOG.error(message);
    }

    public static void i(String message){
        LOG.info(message);
    }

    public static void w(String message){
        LOG.warn(message);
    }
}
