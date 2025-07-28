import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WiFiPasswordRetriever {

    public static void main(String[] args) {
        List<String> wifiProfiles = getWiFiProfiles();
        if (wifiProfiles.isEmpty()) {
            System.out.println("No Wi-Fi profiles found.");
            return;
        }

        System.out.println("Retrieving Wi-Fi passwords...");
        for (String profile : wifiProfiles) {
            String password = getWiFiPassword(profile);
            System.out.println("SSID: " + profile + " | Password: " + password);
        }
    }

    private static List<String> getWiFiProfiles() {
        List<String> profiles = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("netsh wlan show profiles");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("All User Profile")) {
                    String profile = line.split(":")[1].trim();
                    profiles.add(profile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profiles;
    }

    private static String getWiFiPassword(String profileName) {
        try {
            Process process = Runtime.getRuntime().exec("netsh wlan show profile name=\"" + profileName + "\" key=clear");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Key Content")) {
                    return line.split(":")[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Not Found";
    }
}
