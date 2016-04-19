package net.kuronicle.mdm.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class ColumnDefinition {

    @NonNull
    String name;
    
    @NonNull
    String dispName;
    
    boolean isEditable = true;
}
