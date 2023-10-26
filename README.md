# CLI
## Command Line Interpreter
This is simple project to simulate the operating system commands using ***Java***
### These are the commands that our programe can execute them
- **echo**
    - Takes 1 argument and prints it.
    - Example: `echo Hello, World!`

- **pwd**
    - Takes no arguments and prints the current working directory.
    - Example: `pwd`

- **cd**
    - Takes 1 argument:
        - No argument: Changes the current path to the home directory.
        - "..": Changes the current directory to the previous directory.
        - Full or relative path: Changes the current path to the specified path.
    - Example: `cd`, `cd ..`, `cd /path/to/directory`

- **ls**
    - Takes no arguments and lists the contents of the current directory sorted alphabetically.
    - Example: `ls`

- **ls -r**
    - Takes no arguments and lists the contents of the current directory in reverse order.
    - Example: `ls -r`

- **mkdir**
    - Takes 1 or more arguments and creates a directory for each argument.
    - Each argument can be a directory name or a path that ends with a directory name.
    - Examples: `mkdir newDir`, `mkdir /path/to/newDir`

- **rmdir**
    - Takes 1 argument:
        - "*" (e.g., `rmdir *`): Removes all empty directories in the current directory.
        - Full or relative path: Removes the given directory if it is empty.
    - Examples: `rmdir *`, `rmdir /path/to/directory`

- **touch**
    - Takes 1 argument, either the full path or the relative path that ends with a file name, and creates this file.
    - Example: `touch newFile.txt`

- **cp**
    - Takes 2 arguments, both are files, and copies the first onto the second.
    - Example: `cp sourceFile.txt destinationFile.txt`

- **cp -r**
    - Takes 2 arguments, both are directories (empty or not), and copies the first directory (with all its content) into the second one.
    - Example: `cp -r sourceDirectory/ destinationDirectory/`

- **rm**
    - Takes 1 argument which is a file name that exists in the current directory and removes this file.
    - Example: `rm unwantedFile.txt`

- **cat**
    - Takes 1 argument and prints the file’s content or takes 2 arguments and concatenates the content of the 2 files and prints it.
    - Example: `cat file.txt`, `cat file1.txt file2.txt`

- **wc**
    - Takes 1 argument and prints word count information.
    - Example: `wc file.txt`
    - Output: `9 79 483 file.txt` (9 lines, 79 words, 483 characters with spaces)

- **>**
    - Redirects the output of the first command to be written to a file. If the file doesn’t exist, it will be created. If the file exists, its original content will be replaced.
    - Example: `echo Hello, World! > output.txt`

- **>>**
    - Like `>`, but appends to the file if it exists.
    - Example: `echo Appending Text >> existingFile.txt`

- **history**
    - Takes no parameters and displays an enumerated list with the commands you’ve used in the past.
    - Example: `history`
    - Output:
        ```
        1. ls
        2. mkdir tutorial
        3. history
        ```

- **exit**
    - Terminates the CLI.
