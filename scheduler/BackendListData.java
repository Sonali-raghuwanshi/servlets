package com.pwc.madison.core.services.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.pwc.madison.core.services.ListBackendData;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackendListData implements ListBackendData
{
    @Reference
    private QueryBuilder queryBuilder;
    private Session session;
    Map<String,String>  predicate=new HashMap<>();
    @Override
    public List<String> getPageList()
    {
        List<String> pageList=new ArrayList<String>();
        predicate.put("path", "/content/madison-ums/en");
        predicate.put("type", "cq:PageContent");
        predicate.put("property", "jcr:content/cq:Page");
        Query query=queryBuilder.createQuery(PredicateGroup.create(predicate),session);
        SearchResult searchResult=query.getResult();
       try
       {
           for (Hit hits : searchResult.getHits())
           {
               String path = hits.getPath();
               pageList.add(path);
           }
       }
       catch(RepositoryException e) {
           throw new RuntimeException(e);
       }
       return pageList;
    }
}
