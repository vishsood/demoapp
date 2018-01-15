package com.example.vsood.masterdetail.api;

import com.example.vsood.masterdetail.model.Product;

import java.util.List;

/**
 * Created by vsood on 1/12/18.
 */

public class ProductResponse {
    public List<Product> products;
    public int totalProducts;
    public int pageNumber;
    public int pageSize;
    public int status;
}
