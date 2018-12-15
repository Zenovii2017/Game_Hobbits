package saves;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
    private JSONParser parser = new JSONParser();

    public JSONObject read(String fileName) {
        try {
            Object obj = parser.parse(new FileReader(fileName));
            return (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public void write(String fileName, String height, String width, String type, String name) {
        JSONObject obj = new JSONObject();
        obj.put("height", height);
        obj.put("width", width);
        obj.put("type", type);
        obj.put("name", name);


        try (FileWriter file = new FileWriter(fileName)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
