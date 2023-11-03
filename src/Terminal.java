import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Parser {

    private String commandName;
    private String[] args;
    private String userInput;

    // This method will divide the input into commandName and args
    // where "input" is the string command entered by the user
    public boolean parse(String input) {
        // Assuming a simple space-based parsing for command and arguments
        userInput = input;
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

    public String getUserInput() {
        return userInput;
    }
}

public class Terminal {

    private Parser parser;

    public Terminal() {
        this.parser = new Parser();
    }
    private ArrayList<String> history = new ArrayList<>();
    private String output = "";
    public String currentDirectory = System.getProperty("user.dir");
    public String homeDirectory = System.getProperty("user.dir");
    private Path filePath = Paths.get(currentDirectory);

    private void addToHistory() {
        history.add(parser.getUserInput());
    }

    private boolean checkIfFileExists(Path file) {
        return Files.exists(file);
    }
    //Implement each command in a method

    public String echo(String[] args) {
        output = "";
        for (String i : args) {
            output += i + '\n';
        }
        return output;
    }

    public String pwd() {
        // Returns the current directory path
        output = currentDirectory;
        return currentDirectory;
    }

     public void cd(String args) {
        output = "";
        try {
            // Change directory based on the provided argument (args)
            if (args == null || args.trim().isEmpty()) {
                // If no argument is provided, change the current directory to the home directory
                currentDirectory = homeDirectory;
            } else if (args.equalsIgnoreCase("..")) {
                // Navigate up one directory level (if possible)
                File currentDir = new File(currentDirectory);
                String parent = currentDir.getParent();
                if (parent != null) {
                    currentDirectory = parent;
                } else {
                    output += "You are already at the root directory.\n";
                }
                // Change the directory based on the provided argument
            } else if (new File(args).isAbsolute()) {
                File absoluteDir = new File(args);
                if (absoluteDir.isDirectory()) {
                   currentDirectory = absoluteDir.getCanonicalPath();
                } else {
                    output += "An error occurred while navigating up the directory.\n";
                }
            } else {
                File newDir = new File(currentDirectory, args);
                if (newDir.exists() && newDir.isDirectory()) {
                    currentDirectory = newDir.getCanonicalPath();
                } else {
                    System.out.println("Directory not found: " + args);
                }
            }
        } catch (SecurityException e) {
            System.out.println("Access denied to the directory: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        output += currentDirectory;
        return output;
    }

    public String ls(String sourcePath) {
        output = "";
        // List the contents of the directory at the specified sourcePath
        if (sourcePath.length() == 0) {
            // If no sourcePath is provided, list the contents of the current directory
            File file = new File(currentDirectory);
            if (!file.exists()) {
                output += "This directory does not exist..!";
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    // Display whether the item is a directory or a file
                    if (f.isDirectory()) {
                        output += "Directory: ";
                    } else {
                        output += "File: ";
                    }
                    output += f.getName()+'\n';
                }
            }
        } else {
            // List the contents of the specified sourcePath directory
            File file = new File(sourcePath);
            if (!file.exists()) {
                output += "This directory does not exist..!";
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isDirectory()) {
                        output += "Directory: ";
                    } else {
                        output += "File: ";
                    }
                    output += f.getName()+'\n';
                }
            }
        }
        return output;
    }

    public String ls_r(String sourcePath) {
        output = "";
        // List the contents of the directory at sourcePath in reverse order
        if (sourcePath.length() == 0) {
            // If no sourcePath is provided, list the contents of the current directory in reverse order
            File file = new File(currentDirectory);
            if (!file.exists()) {
                output += "This directory does not exist..!\n";
            } else {
                File[] files = file.listFiles();
                for (int i = files.length - 1; i >= 0; i--) {
                    if (files[i].isDirectory()) {
                        output += "Directory: ";
                    } else {
                        output += "File: ";
                    }
                    output += files[i].getName()+'\n';
                }
            }
        } else {
            // List the contents of the specified sourcePath directory in reverse order
            File file = new File(sourcePath);
            if (!file.exists()) {
                output += "This directory does not exist..!\n";
            } else {
                File[] files = file.listFiles();
                for (int i = files.length - 1; i >= 0; i--) {
                    if (files[i].isDirectory()) {
                        output += "Directory: ";
                    } else {
                        output += "File: ";
                    }
                    output += files[i].getName()+'\n';
                }
            }
        }
        return output;
    }

    public String mkdir(String[] directoriesName) {
        output = "";
        // Create a new directory with the given directoryName
        for (int i =0; i<directoriesName.length; i++){
            if (directoriesName[i].length() == 0)
                output += "mkdir takes a directory name as a parameter..!\n";
            else {
                File newDirectory = new File(directoriesName[i]);
                if (!newDirectory.exists()) {
                    newDirectory.mkdir();
                    output += directoriesName[i] + " directory has been created successfully\n";
                } else {
                    output += "The directory already exists..!\n";
                }
            }
        }

        return output;
    }

    public String rmdir(String directory) {
        output = "";
        File myDirectory = new File(directory);
        if (directory.length() == 0) {
            output += "Sorry,rmdir takes a directory name please, Enter directory name\n";
        } else {
            if (!myDirectory.exists()) {
                output += "Sorry,This directory is not exist\n";

            } else {
                if (myDirectory.delete()) {
                    output += myDirectory + " Directory was Deleted\n";
                } else {
                    output += "this directory is not empty,you can try rm\n";
                }
            }
        }
        return output;
    }

    public String rmdir_as(String directoryPath) {
        output = "";
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] subdirectories = directory.listFiles();
            if (subdirectories != null) {
                for (File subdirectory : subdirectories) {
                    if (subdirectory.isDirectory()) {
                        rmdir_as(subdirectory.getAbsolutePath());
                        if (subdirectory.listFiles() == null || subdirectory.listFiles().length == 0) {
                            subdirectory.delete();
                            output += "empty files deleted successfully\n";
                        }
                    }
                }
            }
        }
        return output;
    }

    public String rm(String fileName) {
        output = "";
        File file = new File(fileName);

        if (file.exists()) {
            if (!file.delete()) {
                output += "Failed to delete the file.\n";
            } else {
                output += "File deleted successfully.\n";
            }
        } else {
            output += "File doesn't exist or is not a regular file.\n";
        }
        return output;
    }

    public String cp(String src, String dest) {
        output = "";
        // Resolve the source and destination paths based on whether they are relative or absolute
        Path sourcePath = Paths.get(currentDirectory, src);
        Path destinationPath = Paths.get(currentDirectory, dest);

        try {
            // Check if the source file exists before attempting to copy
            if (Files.exists(sourcePath)) {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                output += "File copied successfully\n";
            } else {
                output += "Source file does not exist: " + src + '\n';
            }
        } catch (IOException e) {
            output += "Failed to copy file!!\n";
            output += "Try to enter the right file names\n";
        }
        return output;
    }

    public void cp_r(String sourcePath, String destinationPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);

        if (!Files.exists(destination)) {
            Files.createDirectories(destination);
        }

        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relativePath = source.relativize(dir);
                Path targetPath = destination.resolve(relativePath);

                if (!Files.exists(targetPath)) {
                    Files.createDirectories(targetPath);
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relativePath = source.relativize(file);
                Path targetPath = destination.resolve(relativePath);

                Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);

                return FileVisitResult.CONTINUE;
            }
        });
    }

    public String touch(String filePath) {
        output = "";
        Path path = Paths.get(filePath);

        try {
            Files.createFile(path);
            output += "File created successfully.\n";
        } catch (FileAlreadyExistsException e) {
            output += "File already exists.\n";
        } catch (IOException e) {
            output += "Failed to create the file: " + e.getMessage() + '\n';
        }
        return output;
    }

    public String cat(String[] args){
        output = "";
        String content = "";
        for (String i : args){
            filePath = Paths.get(currentDirectory+'\\'+i);
            if(checkIfFileExists(filePath)){
                try {
                    content += Files.readString(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                output += "Error opening file!\n";
            }
        }
        output += content + '\n';
        return output;
    }

    public String wc(String args){
        output = "";
        Path newPath;
        filePath = Paths.get(args);
        newPath = filePath;
        if(checkIfFileExists(newPath)){
            try {
                int l=0, w=0, ch=0;
                String[] words;
                // Read all lines from the file into a List
                List<String> lines = Files.readAllLines(newPath);
                // Process each line
                for (String line : lines) {
                    l++;
                    words = line.split("\\s");
                    w += words.length;
                    ch += line.length();
                }
                output += Integer.toString(l) + ' ' + Integer.toString(w) + ' ' + Integer.toString(ch) + ' ' + args + '\n';
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            output += "Error opening file!\n";
        }
        return output;
    }

    public void printIntoFile(String command, String[] args, String fileName) {
        String[] newArgs = Arrays.copyOfRange(args, 0, args.length - 2);
        output = switchCommands(command, newArgs);

        try {
            // If the file doesn't exist, create it
            if (!Files.exists(Paths.get(fileName))) {
                Files.createFile(Paths.get(fileName));
            }

            // Open the file in write mode and write the output
            Files.write(Paths.get(currentDirectory+'\\'+fileName), output.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file: " + fileName);
        }
    }

    public void appendIntoFile(String command, String[] args, String fileName) {
        String[] newArgs = Arrays.copyOfRange(args, 0, args.length - 2); // Exclude ">>" and the file name
        String output = switchCommands(command, newArgs);

        try {
            // If the file doesn't exist, create it
            if (!Files.exists(Paths.get(fileName))) {
                Files.createFile(Paths.get(fileName));
            }

            // Open the file in append mode and write the output
            Files.write(Paths.get(fileName), (output + '\n').getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error appending to file: " + fileName);
        }
    }

    public String history() {
        output = "";
        for (int i = 0; i < history.toArray().length - 1; i++) {
            output += history.toArray()[i] + "\n";
        }
        return output;
    }

    private String switchCommands(String command, String[] args) {
        output = "";
        switch (command) {
            case "echo":
                output += echo(args);
                break;
            case "pwd":
                output += pwd();
                break;
            case "cd":
                if (args.length == 1) {
                    output += cd(args[0]);
                }
                break;
            case "ls":
                if (args.length == 2 && args[0].equals("-r")) {
                    output += ls_r(args[1]);
                }
                else if (args.length == 1){
                    if (args[0].equals("-r")){
                        output += ls_r("");

                    }
                    else {
                        output += ls(args[0]);
                    }
                }
                else {
                    output += ls("");
                }
                break;
            case "mkdir":
                output += mkdir(args);
                break;
            case "rmdir":
                if(args[0].equals("*")){
                    String currentDirectoryPath = System.getProperty("user.dir");
                    output += rmdir_as(currentDirectoryPath);
                }
                else{
                    if (args.length == 1) {
                        output += rmdir(args[0]);
                    }
                }
                break;
            case "touch":
                if (args.length == 1) {
                    output += touch(args[0]);
                }
                break;
            case "cp":
                if(args.length == 3){
                    if (args[0].equals("-r")) {
                        try {
                            cp_r(args[1], args[2]);
                            output += "Directory copied successfully.\n";
                        } catch (IOException e) {
                            output += "Failed to copy directory: " + e.getMessage() + '\n';
                        }
                    }
                }
                else{
                    if (args.length == 2) {
                        output += cp(args[0], args[1]);
                    }
                }
                break;
            case "rm":
                if (args.length == 1) {
                    output += rm(args[0]);
                }
                break;
            case "cat":
                output += cat(args);
                break;
            case "wc":
                output += wc(args[0]);
                break;
            case "history":
                output += history();
                break;
            default:
                output += "Command not recognized: " + command + '\n';
        }
        return output;
    }

    public void chooseCommandAction() {
        String command = parser.getCommandName();
        String[] args = parser.getArgs();
        addToHistory();

        boolean hasRedirectOperator = false;

        for (String arg : args) {
            if (arg.equals(">") || arg.equals(">>")) {
                hasRedirectOperator = true;
                break;
            }
        }

        if (hasRedirectOperator) {
            // Handle redirection
            handleRedirection(command, args);
        } else {
            // No redirection operator found, proceed with regular command execution
            System.out.println(switchCommands(command, args));
        }
    }

    private void handleRedirection(String command, String[] args) {
        String fileName = null;
        boolean appendMode = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(">") || args[i].equals(">>")) {
                // Found redirection operator
                if (i < args.length - 1) {
                    fileName = args[i + 1];
                    appendMode = args[i].equals(">>");
                    break;
                } else {
                    System.out.println("Error: Missing file name after redirection operator.");
                    return;
                }
            }
        }

        if (fileName != null) {
            if (appendMode) {
                // Call the appropriate method for append mode
                appendIntoFile(command, args, fileName);
            } else {
                // Call the appropriate method for overwrite mode
                printIntoFile(command, args, fileName);
            }
        } else {
            System.out.println("Error: Missing file name after redirection operator.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Terminal terminal = new Terminal();

        System.out.print(terminal.pwd());
        System.out.print(">");
        String userInput = scanner.nextLine();

        while (!userInput.equals("exit")) {

            // Parse the user input
            terminal.parser.parse(userInput);

            // Execute the appropriate command action
            terminal.chooseCommandAction();

            // Prompt the user to enter a command for the next iteration
            System.out.print(terminal.pwd());
            System.out.print(">");
            userInput = scanner.nextLine();
        }

        // Close the scanner outside the loop
        scanner.close();
    }

}
