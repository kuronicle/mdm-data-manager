package net.kuronicle.mdm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.kuronicle.mdm.domain.ColumnDefinition;
import net.kuronicle.mdm.domain.Record;

import org.springframework.stereotype.Service;

@Service
public class DataManagerService {
    
    private Set<ColumnDefinition> columns;
    
    private Map<String, Record> records;
    
    int recordMax = 2;
    
    public DataManagerService() {
        columns = new LinkedHashSet<ColumnDefinition>();
        ColumnDefinition id = new ColumnDefinition("id", "ID");
        id.setEditable(false);
        columns.add(id);
        ColumnDefinition name = new ColumnDefinition("name", "名前");
        columns.add(name);
        
        records = new HashMap<String, Record>();
        Record record1 = new Record();
        record1.addColumn("id", "0001");
        record1.addColumn("name", "ほげ太郎");
        records.put("0001", record1);
        Record record2 = new Record();
        record2.addColumn("id", "0002");
        record2.addColumn("name", "ふが二郎");
        records.put("0002", record2);
    }

    public Set<ColumnDefinition> getColumnDefinitions(String orsName, String tableName) {
        return columns;
    }

    public List<Record> findAll(String orsName, String tableName) {
        List<Record> recordList = new ArrayList<Record>();
        for(Entry<String, Record> entry : records.entrySet()) {
            recordList.add(entry.getValue());
        }
        return recordList;
    }

    public String getBaseObjectDisplayName(String orsName, String baseObjectName) {
        return "BO名";
    }

    public Record findOne(String orsName, String baseObjectName, String id) {
        return records.get(id);
    }

    public void put(Record baseObjectRecord) {
        if (baseObjectRecord.getValue("id") == null || "".equals(baseObjectRecord.getValue("id"))) {
            recordMax++;
            baseObjectRecord.addColumn("id", ("0000" + recordMax).substring(("0000" + recordMax).length()-4, ("0000" + recordMax).length()));
        }
        records.put(baseObjectRecord.getValue("id"), baseObjectRecord);
    }

    public void delete(String id) {
        records.remove(id);
    }

}
