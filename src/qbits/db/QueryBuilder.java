/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.db;

import com.mysql.jdbc.UpdatableResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Topu
 */
public class QueryBuilder {

    private ArrayList<String> tables;
    private ArrayList<String> selectParams;
    private ArrayList<String> whereParams;
    private ArrayList<String> joinParams;
    private ArrayList<String> orderParams;
    private ArrayList<String> groupParams;
    private HashMap<String, String> data;

    public QueryBuilder() {

        tables = new ArrayList<>();
        selectParams = new ArrayList<>();
        whereParams = new ArrayList<>();
        joinParams = new ArrayList<>();
        orderParams = new ArrayList<>();
        groupParams = new ArrayList<>();

        data = new HashMap<>();
    }

    public String insert(String tableName) {

        String query = "INSERT INTO " + tableName + " (";
        String values = "(";
        boolean isFirst = true;

        for (String key : data.keySet()) {
            query += (isFirst ? "" : ", ") + key;
            values += (isFirst ? "" : ", ") + data.get(key);
            isFirst = false;
        }

        query += ") VALUES " + values + ")";

        return query;
    }

    public String update(String tableName) {

        String query = "UPDATE " + tableName + " SET ";
        boolean isFirst = true;

        for (String key : data.keySet()) {
            query += (isFirst ? "" : ", ") + key + " = " + data.get(key);
            isFirst = false;
        }

        query += "\n" + getJoin();
        query += "\n" + getWhere();

        return query.trim();

    }

    public String delete(String tableName) {

        String query = "DELETE FROM " + tableName;

        query += "\n" + getJoin();
        query += "\n" + getWhere();

        return query.trim();
    }

    public void setData(HashMap data) {
        this.data = data;
    }

    public void set(String param, String value) {
        this.data.put(param, value);
    }

    public void setString(String param, String value) {
        this.data.put(param, "\"" + value + "\"");
    }

    public void set(String param, boolean value) {
        this.data.put(param, value ? "1" : "0");
    }

    private String getWhere() {

        String query = "";
        int count = 0;

        if (whereParams.size() > 0) {

            query = "\nWHERE ";

            for (count = 0; count < whereParams.size(); count++) {

                if (whereParams.get(count).startsWith("OR ")) {
                    query += whereParams.get(count);
                } else {

                    query += count == 0 ? whereParams.get(count) : "AND " + whereParams.get(count);

                }

                query += whereParams.size() - 1 == count ? " " : " ";
            }
        }

        return query;
    }

    private String getJoin() {

        String query = "";
        int count = 0;

        if (joinParams.size() > 0) {
            
            query += "\n";

            for (count = 0; count < joinParams.size(); count++) {
                query += joinParams.get(count);
                query += joinParams.size() - 1 == count ? " " : "\n";
            }
        }

        return query;
    }

    public String get() {

        int count = 0;
        String query = "SELECT ";

        for (count = 0; count < selectParams.size(); count++) {

            query += selectParams.get(count);
            query += selectParams.size() - 1 == count ? " " : ", ";
        }

        query += "\nFROM ";

        for (count = 0; count < tables.size(); count++) {

            query += tables.get(count);
            query += tables.size() - 1 == count ? " " : ", ";
        }

        query += getWhere();
        query += getJoin();

        if (orderParams.size() > 0) {

            query += "\nORDER BY ";

            for (count = 0; count < orderParams.size(); count++) {

                query += orderParams.get(count);
                query += orderParams.size() - 1 == count ? " " : ", ";
            }
        }

        if (groupParams.size() > 0) {

            query += "\nGROUP BY ";

            for (count = 0; count < groupParams.size(); count++) {

                query += groupParams.get(count);
                query += groupParams.size() - 1 == count ? " " : ", ";
            }
        }

        return query.trim();
    }

    public void select(String params) {
        selectParams.add(params);
    }

    public void select(ArrayList params) {
        for (int i = 0; i < params.size(); i++) {
            selectParams.add(params.get(i).toString());
        }
    }

    public void from(String tableName) {
        tables.add(tableName);
    }

    private void join(String joinType, String tableName, String condition) {
        joinParams.add(joinType + " " + tableName + " ON " + condition);
    }

    public void innerJoin(String tableName, String condition) {
        join("INNER JOIN", tableName, condition);
    }

    public void leftJoin(String tableName, String condition) {
        join("LEFT JOIN", tableName, condition);
    }

    public void rightJoin(String tableName, String condition) {
        join("RIGHT JOIN", tableName, condition);
    }

    public void where(String condition) {
        whereParams.add(condition);
    }

    public void where(String param, String value) {
        whereParams.add(param + value);
    }

    public void whereString(String param, String value) {
        whereParams.add(param + "\"" + value + "\"");
    }

    public void whereOR(String param, String value) {
        whereParams.add("OR " + param + value);
    }

    public void whereStringOR(String param, String value) {
        whereParams.add("OR " + param + "\"" + value + "\"");
    }

    public void whereOR(String condition) {
        whereParams.add("OR " + condition);
    }

    public void groupBy(String param) {
        groupParams.add(param);
    }

    public void orderBy(String param, String method) {
        orderParams.add(param + " " + method);
    }

    public void orderBy(String param) {
        orderParams.add(param);
    }

    public static void main(String[] args) {

        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder.set("id", null);
        queryBuilder.setString("name", "myname");
        queryBuilder.setString("email", "myemail");
        queryBuilder.set("v1", "1");
        queryBuilder.set("v2", "2");
        queryBuilder.where("id > 5");
        queryBuilder.where("id > 5");
        queryBuilder.where("id > 5 OR cd < 5");
        queryBuilder.whereOR("(id > 5 AND TD < 0)");
        queryBuilder.whereOR("id > 5");
        queryBuilder.whereOR("id > 5");
        queryBuilder.innerJoin("in_table", "int_table.in = outtable.out");
        queryBuilder.leftJoin("in_table", "int_table.in = outtable.out");
        queryBuilder.rightJoin("in_table", "int_table.in = outtable.out");

        System.out.println(queryBuilder.delete("bangladesh"));

//        queryBuilder.set("id", null);
//        queryBuilder.setString("name", "myname");
//        queryBuilder.setString("email", "myemail");
//        queryBuilder.set("v1", "1");
//        queryBuilder.set("v2", "2");
//
//        System.out.println(queryBuilder.insert("mytable"));


//        queryBuilder.select("user_id, keyword");
//        queryBuilder.select("keyword");
//        queryBuilder.from("user_keywords");
//        queryBuilder.from("user_keywords");
//        queryBuilder.from("user_keywords");
//        queryBuilder.from("user_keywords");
//        queryBuilder.where("id > 5");
//        queryBuilder.where("id > 5");
//        queryBuilder.where("id > 5 OR cd < 5");
//        queryBuilder.whereOR("(id > 5 AND TD < 0)");
//        queryBuilder.whereOR("id > 5");
//        queryBuilder.whereOR("id > 5");
//        queryBuilder.innerJoin("in_table", "int_table.in = outtable.out");
//        queryBuilder.leftJoin("in_table", "int_table.in = outtable.out");
//        queryBuilder.rightJoin("in_table", "int_table.in = outtable.out");
//        queryBuilder.groupBy("id");
//        queryBuilder.groupBy("id");
//        queryBuilder.groupBy("id");
//        queryBuilder.orderBy("id", "desc");

//        System.out.println(queryBuilder.get());

    }
}
