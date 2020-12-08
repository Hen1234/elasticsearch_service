package Service.elasticsearchContainer;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.rest.RestStatus;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


public class deleteIndexAPITests {

	static RestHighLevelClient client = new RestHighLevelClient(
	        RestClient.builder(new HttpHost("localhost", 9200, "http")));

	
	@Before
	public void beforeClass() throws IOException {
		
		CreateIndexRequest createRrequest = new CreateIndexRequest("new_index1");
		client.indices().create(createRrequest, RequestOptions.DEFAULT);
		
	}
	
	
	@Test
	public void deleteIndexTest() throws IOException {
		
		DeleteIndexRequest deleteRequest = new DeleteIndexRequest("new_index1");
		client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
		
		GetIndexRequest request = new GetIndexRequest("new_index1");
		boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
	
		assertTrue(!exists);
		
	}
	
	
	@Test
	public void deleteNotExistIndexTest() throws IOException {
		
		DeleteIndexRequest deleteRequest = new DeleteIndexRequest("new_index1");
		client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
		
		try {
		    DeleteIndexRequest request = new DeleteIndexRequest("new_index1");
		    client.indices().delete(request, RequestOptions.DEFAULT);
		} catch (ElasticsearchException exception) {
		    assertEquals (exception.status(),RestStatus.NOT_FOUND);  	 
		}
		
	}
	
	
	@Test 
	public void deleteIndexByPatternTest() throws IOException {
				
		CreateIndexRequest createRrequest = new CreateIndexRequest("new_index2");
		client.indices().create(createRrequest, RequestOptions.DEFAULT);
		
		DeleteIndexRequest deleteRequest = new DeleteIndexRequest("new_index*");
		client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
		
		GetIndexRequest getRequest = new GetIndexRequest("new_index*");
		boolean exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
	
		assertTrue(!exists);
			    
	}
	
	
	@AfterClass
	public static void afterClass() throws IOException {
		
		client.close();

	}

}
