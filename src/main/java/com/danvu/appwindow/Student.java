package com.danvu.appwindow;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private Integer Id;
    private String Name;
    private String Sex;
    private String BirthDay;
    private String Phone;
    private String Address;


}
