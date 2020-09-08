package com.example.resources;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PropertyResolver {

    @Value("${NumberOfDoors}")
    public String doorCount;

}
