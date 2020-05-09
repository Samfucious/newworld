/*
 * Copyright (C) 2020 samfucious
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package game;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Configuration {    
    public static final String DEFAULT_PORT = "6560";
    
    public enum Configurations {
        SERVER("server"),
        CONNECT("connect"),
        SKIPDIALOG("skipdialog"),
        PORT("port"),
        HELP("help"),
        NOOP("noop"); // A false configuration to avoid having to do null checks.

        private final String configurationName;

        private static final Map<String, Configurations> lookup = new HashMap();

        static {
            for (Configurations configuration : Configurations.values()) {
                lookup.put(configuration.getConfigurationName(), configuration);
            }
        }

        Configurations(String configurationName) {
            this.configurationName = configurationName;
        }

        public String getConfigurationName() {
            return this.configurationName;
        }

        public static Configurations get(String configuration) {
            if (lookup.containsKey(configuration)) {
                return lookup.get(configuration);
            }
            return Configurations.NOOP;
        }
    }
    
    static final String NOOP_VALUE = "BAADF00D"; // A false configuration value
    
    private static Configuration instance;
    
    private final HashMap<Configurations, String> configurations = new HashMap();
    private static final HashMap<Configurations, String> descriptions = new HashMap();
    
    static {
        descriptions.put(Configurations.PORT, "[--%s {port_number}] As a server, listens on the given port.");        
        descriptions.put(Configurations.CONNECT, "[--%s {server_address}] Launches as a client that connects to a given server address.");
        descriptions.put(Configurations.SERVER, "[--%s] Launches the program as a headless server.");
        descriptions.put(Configurations.SKIPDIALOG, "[--%s] Bypasses the runtype selection dialog.");
        descriptions.put(Configurations.HELP, "[--%s] Displays this help message");
        descriptions.put(Configurations.NOOP, "[--%s] Does nothing of consequence.");
    }
    
    public static void displayHelpMessage() {
        System.out.println();
        System.out.println("Usage:");
        System.out.println();
        
        descriptions.keySet().forEach((configuration) -> {
            System.out.println(String.format(descriptions.get(configuration), configuration));
        });
    }
    
    private Configuration(String[] args) {
        instance = this;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String configurationName = args[i].substring(2);
                String configurationValue = i < args.length-1 ? args[i+1] : NOOP_VALUE;
                configurations.put(Configurations.get(configurationName), configurationValue);
            }
        }
    }
    
    public static String getConfigurationValue(Configurations configuration, String fallback) {
        if (!instance.configurations.containsKey(configuration)) {
            return fallback;
        }
        return instance.configurations.get(configuration);
    }
    
    public static void setConfigurationValue(Configurations configuration, String value) {
        instance.configurations.put(configuration, value);
    }
    
    public static boolean isServer() {
        return !getConfigurationValue(Configurations.SERVER, NOOP_VALUE).equals(NOOP_VALUE);
    }
    
    public static boolean hasConfiguration(Configurations configuration) {
        return instance.configurations.containsKey(configuration);
    }
    
    public static void initConfiguration(String[] args) {
        instance = new Configuration(args);
    }
}
