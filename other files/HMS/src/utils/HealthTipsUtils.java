package utils;

import java.util.HashMap;
import java.util.Map;

public class HealthTipsUtils {

    private static final Map<String, String> healthTips = new HashMap<>();

    static{
        healthTips.put("diabetes", "Please focus on your sugar intake, and exercise more regularly");
        healthTips.put("obesity", "Please focus on your calories intake, and exercise more regularly and eat more healthy");
        healthTips.put("cancer", "Generally just main a healthy diet");
    }

    public static String getHealthTip(String diagnosis){
        return healthTips.getOrDefault(diagnosis.toLowerCase(), "No health tips available for this specific condition!");
    }
}
