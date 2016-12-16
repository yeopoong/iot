package kyun.iot.framework.message;

import java.util.HashMap;

public class Param extends HashMap<Object, Object> {

    private static final long serialVersionUID = 1L;

    public String getString(String key) {
        return (String) get(key);
    }
}
