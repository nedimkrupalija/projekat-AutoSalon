package ba.etf.unsa.rpr.dao;

import ba.etf.unsa.rpr.domain.Idable;
import ba.etf.unsa.rpr.exception.UserException;

import java.io.FileReader;
import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;


/**
 * Abstract class for implementing all database operation on all tables
 * @param <T>
 */

public abstract class AbstractDao<T extends Idable> implements Dao<T> {
    private Connection conn;
    private String tableName;

    public Connection getConn() {
        return conn;
    }

    /**
     * Constructor for abstract dao
     * Makes connection onto db
     * @param tableName String name of table in database
     */
    public AbstractDao(String tableName){
            this.tableName = tableName;
        try{
            FileReader reader = new FileReader("db.properties");
            Properties property = new Properties();
            property.load(reader);
            this.conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/"+property.getProperty("username"),property.getProperty("username"),property.getProperty("password"));
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for deleting object from db
     * @param id of object to delete
     */
   public void delete(int id){
        String query = "DELETE FROM " + this.tableName + " WHERE id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
   }

    /**
     * method for getting item by its id
     * @param id of item
     * @return item from db
     */
   public T getById(int id){
       String query = "SELECT * FROM " + this.tableName + " WHERE id = ?";
       try{
           PreparedStatement stmt = this.conn.prepareStatement(query);
           stmt.setInt(1,id);
           ResultSet rs = stmt.executeQuery();
           if(rs.next()){
               T item = row2object(rs);
               rs.close();
               return item;
           }
           else {
               throw new Exception("Not found!");
           }
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

    /**
     * Private method that prepares string for update method
     *
     * @param row - map returned from obj2row
     * @return String with values
     */
   private String prepareUpdate(Map<String, Object> row){
       StringBuilder columns = new StringBuilder();

       int counter = 0;
       for(Map.Entry<String, Object> entry : row.entrySet()){
           counter++;
           if(entry.getKey().equals("id")) continue;;
           columns.append(entry.getKey()).append("= ?");
           if(row.size()!=counter){
               columns.append(", ");
           }
       }
       return columns.toString();
   }

    /**
     * Method for updating item in database
     * @param item from which data is taken for update
     * @param id of item to update
     * @return updated item
     */
    public T update(T item, int id){
        Map<String, Object> row = object2row(item);
        String columnsToUpdate = prepareUpdate(row);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE ").append(this.tableName).append(" SET ").append(columnsToUpdate)
                .append(" WHERE id = ?");
        try{
            PreparedStatement stmt = this.conn.prepareStatement(stringBuilder.toString());
            int counter = 1;
            for(Map.Entry<String, Object> entry : row.entrySet()){
                if(entry.getKey().equals("id")) continue;
                stmt.setObject(counter,entry.getValue());
                counter++;
            }
            stmt.setObject(counter,id);
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for inserting item into db
     * @param item to insert
     */
    public void insert(T item){
        Map<String, Object> row = object2row(item);
        Map.Entry<String, String> columns = prepareInsert(row); //
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(this.tableName).append( "(");
        builder.append(columns.getKey()).append(") ").append("VALUES (").append(columns.getValue()).append(")");
        try{
            PreparedStatement stmt = this.conn.prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            for(Map.Entry<String, Object> entry : row.entrySet()){
                if(entry.getKey().equals("id")) continue;
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Help method that prepares names of columns for inserting + ? sign for that column
     * @param row - map returned from obj2row
     * @return - map of name-?
     */
    private Map.Entry<String, String> prepareInsert(Map<String, Object> row){
        StringBuilder columnName = new StringBuilder();
        StringBuilder columnQuestion = new StringBuilder();
        int counter = 1;
        for(Map.Entry<String, Object> entry : row.entrySet()){
            counter++;
            if(entry.getKey().equals("id")) continue;
            columnName.append(entry.getKey());
            columnQuestion.append("?");
            if(row.size()!= counter-1){
                columnName.append(", ");
                columnQuestion.append(", ");
            }
        }
        return new AbstractMap.SimpleEntry<String, String>(columnName.toString(),columnQuestion.toString());
    }

    /**
     * ORM - transforms result set into object
     * @param rs
     * @return
     */
   public abstract T row2object(ResultSet rs) throws SQLException, UserException;

    /**
     * ORM - transforms object params. into fields for query
     *
     * @param object
     * @return
     */
    public abstract Map<String, Object> object2row(T object);

    /**
     * Method for getting all items of type T from db
     * @return ArrayList of objects
     */
    public ArrayList<T> getAll(){
        String query = "SELECT * FROM " + this.tableName;
        ArrayList<T> items = new ArrayList<T>();
        try{
            PreparedStatement stmt = this.conn.prepareStatement(query);
            ResultSet rs =  stmt.executeQuery();
            while(rs.next()){
               T item = row2object(rs);
               items.add(item);
            }
            rs.close();
            return items;
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



}
