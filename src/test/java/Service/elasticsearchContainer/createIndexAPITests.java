package Service.elasticsearchContainer;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.RestStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class createIndexAPITests {
	
	static RestHighLevelClient client = new RestHighLevelClient(
	        RestClient.builder(new HttpHost("localhost", 9200, "http")));

	
	@BeforeClass
	public static void beforeClass() throws IOException {
		
		CreateIndexRequest createRrequest = new CreateIndexRequest("new_index");
		
		//add index settings
		createRrequest.settings(Settings.builder().put("index.number_of_shards", 3));
		
		//add index alias
		createRrequest.alias(new Alias("my_index")); 
		
		client.indices().create(createRrequest, RequestOptions.DEFAULT);
	   
	}

	
	@Test
	  public void getIndexTest() throws IOException {
		  
		GetIndexRequest request = new GetIndexRequest("new_index");
		boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
		
		assertTrue(exists);
		  
	}
	
	
	@Test 
	public void getSettingsTest() throws IOException {
		
		GetSettingsRequest request = new GetSettingsRequest().indices("new_index");
		GetSettingsResponse getSettingsResponse = client.indices().getSettings(request, RequestOptions.DEFAULT);
		String numberOfShardsString = getSettingsResponse.getSetting("new_index", "index.number_of_shards");
		
		assertEquals("3", numberOfShardsString);
		
	}
	
	
	@Test
	public void getAliasTest() throws IOException {
		
		GetAliasesRequest request = new GetAliasesRequest().indices("new_index");
		GetAliasesResponse response = client.indices().getAlias(request, RequestOptions.DEFAULT);
		Map<String, Set<AliasMetadata>> aliases = response.getAliases();
	
		assertEquals("my_index", aliases.get("new_index").iterator().next().alias());
	
	}
	
	
	@AfterClass
	public static void afterClass() throws IOException {
		
		try {
		    DeleteIndexRequest request = new DeleteIndexRequest("new_index");
		    client.indices().delete(request, RequestOptions.DEFAULT);
		} catch (ElasticsearchException exception) {
		    if (exception.status() == RestStatus.NOT_FOUND) {
		    	System.out.println("Index not Found"); 
		    }
		}
		
		client.close();

	}
	

}
