package demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class jsonConverter {
    public static void convertToJson(ArrayList<HashMap<String, String>> data, String outputPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        /*
        To simply write the data in json file
        */
        //objectMapper.writeValue(new File(outputPath), data);

        /*
        To format the JSON file so that each object is printed on a separate line
        */
        // Create a custom pretty printer
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance);
        
        // Create an ObjectWriter with the custom pretty printer
        ObjectWriter writer = objectMapper.writer(prettyPrinter);
        
        // Write the formatted JSON to file
        writer.writeValue(new File(outputPath), data);
    }
}