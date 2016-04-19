package net.kuronicle.mdm.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Record {

    private Map<String, String> columns = new HashMap<String, String>();
    
    public void addColumn(String columnName, String value) {
        columns.put(columnName, value);
    }
    
    public String getValue(String columnName) {
        return columns.get(columnName);
    }
}
