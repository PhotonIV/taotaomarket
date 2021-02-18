package com.taotao.search.service;

import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;

public interface SearchService {

    SearchResult search(String quertyString,int page,int rows) throws Exception;

}
