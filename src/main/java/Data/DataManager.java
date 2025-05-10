package Data;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class DataManager {
    private static ObjectMapper mapper;

    static {
	// I used it to solve grade saving problems with the timestamps
	mapper = new ObjectMapper();
	mapper.registerModule(new JavaTimeModule());
    }

    public static <T> void save(String filename, List<T> data) throws Exception {
	mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), data);
    }

    public static <T> List<T> load(String filename, Class<T[]> clazz) throws Exception {
	T[] array = mapper.readValue(new File(filename), clazz);
	return new ArrayList<>(List.of(array));
    }
}
