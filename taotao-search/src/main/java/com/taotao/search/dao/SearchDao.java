package com.taotao.search.dao;

import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchDao {
    SearchResult search(SolrQuery query) throws  Exception;
   // SearchItem searchById(Long id) throws Exception;
}