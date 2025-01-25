package com.example.easytable.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse<DTO> {


    List<DTO> list;
    boolean hasNext;


}
