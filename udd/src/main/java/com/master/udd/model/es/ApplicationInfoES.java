package com.master.udd.model.es;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter

@Document(indexName = "application_infos")
public class ApplicationInfoES {

    @Id
    @Field(type = FieldType.Text, index = false, store = true)
    private String id;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Date)
    private Date timestamp;

    public ApplicationInfoES() {
        this.location = new GeoPoint(39.56553881520639, 2.650095237636433);
        this.timestamp = new Date();
    }
}
