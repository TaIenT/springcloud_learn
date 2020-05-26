package com.learn.client.VO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductVO {


    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer catgoryType;


    @JsonProperty("foods")
    List<ProductInfoVO> productInfoVOList;

}
