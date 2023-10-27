import java.util.Scanner;
class Parser {
    private String commandName;
    private String[] args;

    // This method will divide the input into commandName and args
    // where "input" is the string command entered by the user
    public boolean parse(String input) {
        // Assuming a simple space-based parsing for command and arguments
        String[] parts = input.split(" ");
        if (parts.length > 0) {
            commandName = parts[0];
            args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, args.length);
            return true;
        } else {
            return false;
        }
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }
}

public class Terminal {
    private Parser parser;

    public Terminal() {
        this.parser = new Parser();
    }

    //Implement each command in a method
    public void echo(String[] args){
        for(String i: args){
            System.out.println(i);
        }
    }

    public String pwd() {
    // Returns the current directory path
    return Main.currentDirectory;
}

public void cd(String args) {
    // Change directory based on the provided argument (args)
    if (args == null || args.trim().isEmpty()) {
        // If no argument is provided, change the current directory to the home directory
        Main.currentDirectory = Main.homeDirectory;
    } else if (args.equalsIgnoreCase("..")) {
        // Navigate up one directory level (if possible)
        try {
            File currentDir = new File(Main.currentDirectory);
            String parent = currentDir.getParent();
            if (parent != null) {
                Main.currentDirectory = parent;
            } else {
                System.out.println("You are already at the root directory.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while navigating up the directory.");
        }
    } else {
        // Change the directory based on the provided argument
        File newDir = new File(Main.currentDirectory, args);
        if (newDir.isDirectory()) {
            Main.currentDirectory = newDir.getPath();
        } else {
            System.out.println("Directory not found: " + args);
        }
    }
}

public void ls(String sourcePath) throws Exception {
    // List the contents of the directory at the specified sourcePath
    if (sourcePath.length() == 0) {
        // If no sourcePath is provided, list the contents of the current directory
        File file = new File(Main.currentDirectory);
        if (!file.exists()) {
            System.out.println("This directory does not exist..!");
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                // Display whether the item is a directory or a file
                if (f.isDirectory()) {
                    System.out.print("Directory: ");
                } else {
                    System.out.print("File: ");
                }
                System.out.println(f.getName());
            }
        }
    } else {
        // List the contents of the specified sourcePath directory
        File file = new File(sourcePath);
        if (!file.exists()) {
            System.out.println("This directory does not exist..!");
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    System.out.print("Directory: ");
                } else {
                    System.out.print("File: ");
                }
                System.out.println(f.getName());
            }
        }
    }
}

public void ls_r(String sourcePath) {
    // List the contents of the directory at sourcePath in reverse order
    if (sourcePath.length() == 0) {
        // If no sourcePath is provided, list the contents of the current directory in reverse order
        File file = new File(Main.currentDirectory);
        if (!file.exists()) {
            System.out.println("This directory does not exist..!");
        } else {
            File[] files = file.listFiles();
            for (int i = files.length - 1; i >= 0; i--) {
                if (files[i].isDirectory()) {
                    System.out.print("Directory: ");
                } else {
                    System.out.print("File: ");
                }
                System.out.println(files[i].getName());
            }
        }
    } else {
        // List the contents of the specified sourcePath directory in reverse order
        File file = new File(sourcePath);
        if (!file.exists()) {
            System.out.println("This directory does not exist..!");
        } else {
            File[] files = file.listFiles();
            for (int i = files.length - 1; i >= 0; i--) {
                if (files[i].isDirectory()) {
                    System.out.print("Directory: ");
                } else {
                    System.out.print("File: ");
                }
                System.out.println(files[i].getName());
            }
        }
    }
}

public void mkdir(String directoryName) {
    // Create a new directory with the given directoryName
    if (directoryName.length() == 0)
        System.out.println("mkdir takes a directory name as a parameter..!");
    else {
        File newDirectory = new File(directoryName);
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
            System.out.println(directoryName + " directory has been created successfully");
        } else {
            System.out.println("The directory already exists..!");
        }
    }
}


    public void rmdir(String[] args){}

    public void touch(String[] args){}

    public void cp(String[] args){}

    public void cpReversed(String[] args){}

    public void rm(String[] args){}

    public void cat(String[] args){}

    public void wc(String[] args){}

    public void printIntoFile(String[] args){}

    public void appendIntoFile(String[] args){}

    public void history(){}

    // This method will choose the suitable command method to be called

public class Main {
    public static String currentDirectory = System.getProperty("user.dir");
    public static final String homeDirectory = System.getProperty("user.dir");

    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(terminal.pwd() + ": ");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                break;
            }

            chooseCommand(terminal, input);
        }
    }
    public static void chooseCommand(Terminal terminal, String input) {
        String[] parts = input.split(" ");
        String command = parts[0];

        try {
            if ("cd".equals(command)) {
                if (parts.length > 1) {
                    terminal.cd(parts[1]);
                }
            } else if ("ls".equals(command)) {
                if (parts.length > 1) {
                    terminal.ls(parts[1]);
                } else {
                    terminal.ls("");
                }
            } else if ("ls_r".equals(command)) {
                if (parts.length > 1) {
                    terminal.ls_r(parts[1]);
                } else {
                    terminal.ls_r("");
                }
            } else if ("mkdir".equals(command)) {
                if (parts.length > 1) {
                    terminal.mkdir(parts[1]);
                }
            } else {
                System.out.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
