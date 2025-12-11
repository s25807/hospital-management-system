package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import validators.ValidatorService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ObjectStore {
    private final ObjectMapper mapper = new ObjectMapper();

    public ObjectStore() {}

    public <T> T load(Class<T> clazz, String path) {
        T object = null;
        try {
            object = mapper.readValue(new File(path), clazz);
        }
        catch (IOException e) {
            System.err.println("[ERROR E011] Reading data failed:\n" + e.getMessage());
        }

        try {
            ValidatorService.validate(object);
        }
        catch (Exception e) {
            System.err.println("[ERROR E012] Validation failed:\n" + e.getMessage());
        }

        return object;
    }

    public <T> void save(T object, String path) {
        try {
            ValidatorService.validate(object);
        }
        catch (Exception e) {
            System.err.println("[ERROR E022] Validation failed:\n" + e.getMessage());
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), object);
        }
        catch (IOException e) {
            System.err.println("[ERROR E023] Saving failed:\n" + e.getMessage());
        }
    }

    public <T> List<T> loadAll(Class<T> clazz, String path) {
        List<T> list = new ArrayList<>();
        try {
            for (Path p : Files.list(Paths.get(path)).toList()) {
                list.add(mapper.readValue(p.toString(), clazz));
            }
        }
        catch (IOException e) {
            System.err.println("[ERROR E034] Loading all failed:\n" + e.getMessage());
        }

        try {
            for (T object : list) ValidatorService.validate(object);
        }
        catch (Exception e) {
            System.err.println("[ERROR E032] Validation failed:\n" + e.getMessage());
        }

        return list;
    }
}
