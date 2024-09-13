package sysml;


import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.interactive.SysMLInteractive;
import org.omg.sysml.interactive.SysMLInteractiveResult;
import org.omg.sysml.lang.sysml.Element;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;


public class SysMLReader {

    public static void main(String[] args) {
        // Get singleton instance.
        SysMLInteractive sysml = SysMLInteractive.getInstance();
        sysml.setVerbose(false);
       
        // Load the KerML and SysML library models. The argument gives the
        // absolute path to the root "system.library" directory.
        System.out.println("Loading libraries...");
        String systemLibPath = "/Users/albertins/git/SySML-v2-Pilot-Implementation/sysml.library";
        sysml.loadLibrary(systemLibPath);
       
        // Set the base path used for publishing via the API.
        sysml.setApiBasePath("http://sysml2.intercax.com:9000");
       
        try {
            // Evaluate (parse and validate) the SysML text read from a file
            // at the given absolute path.
        	String filePath = "/Users/albertins/git/sysml/src/sysml/BatterySystem.sysml"; 
            System.out.println("Reading " + filePath);
            Path of = Path.of(filePath);
            String string = Files.readString(of, Charset.forName("UTF-8"));
            SysMLInteractiveResult result = sysml.process(string);

           
            // If there are warnings or errors, this will print them.
            // If the eval is successful, it will print the name and UUID of
            // the top-level element.
            System.out.println(result.toString());
           
            if (!result.hasErrors()) {
                // Get the top-level named element in the parsed model.
                Element root = result.getRootElement();
                Element top = ((Namespace)root).getOwnedMember().get(0);
                System.out.println(top.getDeclaredName());
               
                // Operate on the model in memory
                // ...
               
                // Publish the updated model to the repository.
                //System.out.println(sysml.publish(top.getName()));
            }
            System.out.println("Finished!");
           
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}