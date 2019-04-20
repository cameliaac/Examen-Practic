package sample.Repository;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import sample.Domain.Entity;
import sample.Domain.IValidator;
import sample.Domain.Car;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRepository<T extends Entity> implements IRepository<T> {

    private IValidator<T> validator;
    private String carname;
    private Map<String, T> storage = new HashMap<>();
    private Type type;

    /**
     * Constructs a car repository.
     *
     * @param validator the validator.
     * @param carname   the carname used to store objects.
     * @param type      the type of the objects stored (of T).
     */
    public CarRepository(IValidator<T> validator, String carname, Type type) {
        this.validator = validator;
        this.carname = carname;
        this.type = type;
    }

    private void loadFromCar() {
        storage.clear();
        Gson gson = new Gson();
        try (FileReader in = new FileReader(carname)) {
            try (BufferedReader bufferedReader = new BufferedReader(in)) {

                T[] entities = gson.fromJson(bufferedReader, type);
                for (T entity : entities) {
                    storage.put(entity.getId(), entity);
                }
                //                String contents = gson.fromJson(bufferedReader.readLine(), Collection<type>);
//                String line = bufferedReader.readLine(); // skip [
//                while ((line = bufferedReader.readLine()) != null) {
//                    T entity;
//                    try {
//                        entity = gson.fromJson(line.substring(0, line.length() - 1), type);
//                    } catch (Exception ex) {
//                        entity = gson.fromJson(line, type);
//                    }
//
//                    storage.put(entity.getId(), entity);
//                }
            }
        } catch (Exception ex) {

        }
    }

    private void writeToCar() {
        Gson gson = new Gson();
        try (FileWriter out = new FileWriter(carname)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(out)) {

                bufferedWriter.write(gson.toJson(storage.values()));
//
//                bufferedWriter.write('[');
//                bufferedWriter.newLine();
//                List<T> values = new ArrayList<>(storage.values());
//                for (int i = 0; i < values.size() - 1; ++i) {
//                    T entity = values.get(i);
//                    bufferedWriter.write(gson.toJson(entity));
//                    bufferedWriter.write(',');
//                    bufferedWriter.newLine();
//                }
//
//                T entity = values.get(values.size() - 1);
//                bufferedWriter.write(gson.toJson(entity));
//                bufferedWriter.newLine();
//                bufferedWriter.write(']');
            }
        } catch (Exception ex) {

        }
    }

    /**
     * Gets an entity with a given id.
     *
     * @param id the given id.
     * @return the entity with the given id.
     */
    @Override
    public T findById(String id) {
        loadFromCar();
        return storage.get(id);
    }

    /**
     * Adds or replaces an entity.
     *
     * @param entity the entity to add or replace based on its id.
     */
    @Override
    public void upsert(T entity) {
        loadFromCar();
        validator.validate(entity);
        storage.put(entity.getId(), entity);
        writeToCar();
    }

    /**
     * Removes an entity with the given id.
     *
     * @param id the given id.
     * @throws RepositoryException if there is no entity with the given id.
     */
    @Override
    public void remove(String id) {
        loadFromCar();
        if (!storage.containsKey(id)) {
            throw new RepositoryException("There is no entity with the given id to remove.");
        }

        storage.remove(id);
        writeToCar();
    }

    /**
     * Gets a list of all entities.
     *
     * @return the list with all the entities.
     */
    @Override
    public List<T> getAll() {
        loadFromCar();
        return new ArrayList<>(storage.values());
    }
}