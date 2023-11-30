import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args){  //2. throws IOException
        // File Class does not allow to read or write the file, must acquire a stream.

        File newFile = new File("files/first.txt"); //Relative path or absolute path
        try {
            boolean fileCreated = newFile.createNewFile();
            if (fileCreated){
                System.out.println("File created successfully");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (File f : File.listRoots()){
            System.out.println(f);
        }

//        boolean fileDeleted = newFile.delete();
//        if (fileDeleted){
//            System.out.println("File deleted successfully");
//        } else {
//            System.out.println("File does not exists");
//        }

        String filename = "testing.csv";
        Path path = Paths.get(filename);
        testFile2(null);
        System.out.println();
        // Any code after where the exception occurred, isn't going to get executed, unless it's in a finally clause
        // Unhandled exception: IOException
        // The IOException is a special kind of exception, called a Checked Exception
        // It's the parent class of many common exceptions you'll encounter, when working with external resources.


        // Handle a checked exception:
        // 1. Wrap the statement that throws a checked exception, in a try catch block, and then handle the situation in the catch block.
        // 2. Alternately, change the method signature, declaring a throws clause, and specifying the exception type.

        //File file = new File(path);
        // No matter if the file exists, the file object can be created.
        // A file handle is a reference to a file that is used by the OS to keep track of the file
        // It is an abstract representation of  the file, and it does not contain any of actual data from the file.
        // Does not create a new entity on filesystem automatically, it just represents a path. (NO open or close)
        // In contrast, a file resource, is the actual data from the file.
        File file = new File(filename);
        // First checking if the file exists
        if (!file.exists()){
            System.out.println("File does not exist");
            System.out.println("Quitting application, go figure it out");
            return;
        }
        System.out.println("I'm good to go");

        testFile(filename);
        // 方法可能会抛出checked Exception异常，要么在方法的catch里面处理好，要么在上层代码中处理

        // An unchecked exception is an instance of runtimeException.
        // EXAMPLE: int i = 1/0;


    }



    private static void testFile(String filename){
        Path path = Paths.get(filename);


        try {
//            List<String> lines = Files.readAllLines(path);
            FileReader reader = new FileReader(filename);
        } catch (IOException e){
            throw new RuntimeException(e);
            // The code in finally block is always executed, no matter what happens in the try or catch blocks.
        } finally {
            // The original purpose for the finally clause, was to have a single block of code to perform cleanup operations
            // including closing open connections, releasing locks, or freeing up resources.
            // The "try with resouces" syntax, introduced in JDK7, is a better approach than using the finally clause for closing resources.
            System.out.println("Maybe I'd log something either way...");
        }
    }

    private static void anotherTestFile(String filename){
        Path path = Paths.get(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(filename);
        } catch (IOException e){
            throw new RuntimeException(e);
        } finally {
            // Before JDK7, this is how you would've closed a file resource
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Maybe I'd log something either way...");
        }
    }

    private static void testFile2(String filename) {

        // Autocloseable
        // Once the code in try block throw an exception and find the corresponding catch block, other catch block will not be executed!
        try (FileReader reader = new FileReader(filename)) {

        } catch (FileNotFoundException e){
            System.out.println("File ");
            throw new RuntimeException(e);

            // catch multiple targeted exceptions with a single clause:
        } catch (NullPointerException | IllegalArgumentException badData){
            System.out.println("BAD DATA!" + badData.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            System.out.println("Something unrelated and unexpected happened");
        } finally {
            System.out.println("FINALLY: Maybe I'd log something either way...");
        }
    }
}