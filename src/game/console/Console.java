/*
 * Copyright (C) 2020 Sam Iredale "Samfucious" (gyrepin@gmail.com)
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
package game.console;

import game.application.Application;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class Console {
    enum Command {
        echo("echo"),
        stop("stop");
        
        private final String command;
        
        Command(String command) {
            this.command = command;
        }
        
        public String getCommand() {
            return this.command;
        }
        
        public static boolean commandSupported(String command) {
            for (Command c : Command.values()) {
                if (c.getCommand().equals(command)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    private static final HashMap<Command, ICommand> commands = new HashMap();
    
    static {
        commands.put(Command.echo, new Console.EchoCommand());
        commands.put(Command.stop, new Console.StopCommand());
    }
    
    private static boolean running = false;
    
    public static void start() {
        running = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                while(running) {
                    if(sc.hasNext()) {
                        processConsoleInput(sc.nextLine());
                    }
                }
            }
        }).start();
    }
    
    public static void stop() {
        running = false;
        try {
            System.in.close();
        } catch (IOException ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void processConsoleInput(String input) {
        String[] split = input.trim().split("\\s+");
        
        if(split.length > 0) {
            String command = split[0].toLowerCase();
            if (Command.commandSupported(command)) {
                commands.get(Command.valueOf(command)).run(Arrays.copyOfRange(split, 1, split.length));
            }
            else {
                new NotSupportedCommand().run(Arrays.copyOf(split, 1));
            }
        }
    }
    
    public interface ICommand {
        void run(String[] args);
    }
    
    public static class EchoCommand implements ICommand {
        @Override
        public void run(String[] args) {
            System.out.println(Arrays.toString(args));
        }
    }
    
    public static class StopCommand implements ICommand {
        @Override
        public void run(String[] args) {
            Application.getApplication().stop();
            Console.stop();
        }
    }
    
    public static class NotSupportedCommand implements ICommand {
        private static final String MESSAGE = "Command \"%s\" not supported.";
        
        @Override
        public void run(String[] args) {
            System.out.println(String.format(MESSAGE, args[0]));
        }
    }
}