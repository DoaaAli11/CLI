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

    public String pwd(){return "";}

    public void cd(String[] args){}

    public void ls(){}

    public void lsReversed(){}

    public void mkdir(String[] args){}

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
    public void chooseCommandAction() {
        String command = parser.getCommandName();
        String[] args = parser.getArgs();

        switch (command) {
            case "echo":
                echo(args);
                break;
            case "pwd":
                System.out.println(pwd());
                break;
            case "cd":
                cd(args);
                break;
            default:
                System.out.println("Command not recognized: " + command);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Terminal terminal = new Terminal();

        System.out.print(">");
        String userInput = scanner.nextLine();

        while (!userInput.equals("exit")) {
            // Parse the user input
            terminal.parser.parse(userInput);

            // Execute the appropriate command action
            terminal.chooseCommandAction();

            // Prompt the user to enter a command for the next iteration
            System.out.print(">");
            userInput = scanner.nextLine();
        }

        // Close the scanner outside the loop
        scanner.close();
    }
}
