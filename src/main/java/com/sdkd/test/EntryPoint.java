package com.sdkd.test;
 
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
 
@Path("/entry-point")

public class EntryPoint {
    
    Cluster cluster;
    
    protected Bucket getConnection() {
        Cluster cluster = CouchbaseCluster.create("10.3.3.205");
        Bucket bucket = cluster.openBucket("default");     
        return bucket;
    }
    
    public Result getFromResultSet(JsonObject js){
        Result result = new Result();
        result.setdesc(js.getString("Description"));
        result.setScen(js.getString("Scenario"));
        result.setWorkload(js.getString("Workload"));
        return result;
    }
    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String sayHello() {
//        return "{Hello World PLAIN text:hello}";
//    }
 
//    @GET
//    @Produces(MediaType.TEXT_XML)
//    public String sayXMLHello() {
//        return "<?xml version='1.0'?><hello> Hello World XML, YAY!!!</hello>";
//    }
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public String sayHtmlHello() {
//        return "<html><title>Hello World HTML</title><body><h1>Hello World HTML JRebel Rules!</body></h1></html>";
//    }
// 
//    @POST
//    @Consumes({"text/xml", "text/plain", MediaType.TEXT_HTML})
//    @Produces(MediaType.TEXT_PLAIN)
//    public String sayPostHello() {
//        return "Hello World Post!";
//    }
    
    @GET
    @Produces("application/json")
    public String getList() {
       List events = new ArrayList<>();
       Bucket b = getConnection();
       JsonDocument found = b.get("hello");
  
       System.out.println(found);

       Result r = getFromResultSet(found.content());
       
       events.add(r.getScen());
       events.add(r.getWorkload());
       events.add(r.getdesc());

       return events.toString();
    }
}