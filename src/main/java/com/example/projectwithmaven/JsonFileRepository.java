//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//public class JsonFileRepository<T extends Domain.Entitate> extends Repository.GenericRepository<T> {
//    private final String fileName;
//    private final ObjectMapper objectMapper;
//
//    public JsonFileRepository(String fileName, Object entityType) {
//        this.fileName = fileName;
//        this.objectMapper = (ObjectMapper) entityType;
//        citesteDinJson();
//    }
//
//    public List<T> citesteDinJson() {
//        try {
//            return objectMapper.readValue(new File(fileName), new TypeReference<List<T>>() {});
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public void scrieInJson(List<T> entities) {
//        try {
//            objectMapper.writeValue(new File(fileName), entities);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void add(T entitate) throws Repository.ExceptionRepository.RepositoryException {
//        super.add(entitate);
//        scrieInJson(entitati);
//    }
//
//    @Override
//    public void update(T entitate) throws Repository.ExceptionRepository.RepositoryException {
//        super.update(entitate);
//        scrieInJson(entitati);
//    }
//
//    @Override
//    public void delete(int id) throws Repository.ExceptionRepository.RepositoryException {
//        super.delete(id);
//        scrieInJson(entitati);
//    }
//
//}
