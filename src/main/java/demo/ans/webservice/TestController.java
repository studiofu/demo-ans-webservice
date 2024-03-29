package demo.ans.webservice;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.message.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * test SOAP endpoint, try to consume and list out the information
 *
 */
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {
	
    @RequestMapping("/consume")
    public HashMap<String,String> consume() throws Exception {
    	
		log.info("consume soap service start.......");
		
		// download the target server certificate as server.crt or server.cer
		// and the use keytool to convert into key store for the Java application to use
		
		// keytool -import -alias testclient -keystore clientKeyStore -file server.crt
		
		String filePath= Thread.currentThread().getContextClassLoader().getResource("keystore/clientKeyStore").getFile();
		log.info("trust store path: " + filePath);
		System.setProperty("javax.net.ssl.trustStore", filePath);
		//System.setProperty("javax.net.ssl.keyStorePassword", "abcd1234");
		
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		
		Client client = dcf.createClient("https://test1.local:8443/api/policyService?wsdl");
		
		SoapMessage m;
		// create header
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		
		//headers.put("XXX-SOA-SERVICE-NAME", Arrays.asList("SampleService"));
		//headers.put("XXX-SOA-APP-NAME", Arrays.asList("SampleServiceAppv1"));
		//headers.put("Authorization", Arrays.asList("Basic ZHVtbXk6YWJjZDEyMzQ="));
		headers.put("x-HKMCA-client-id", Arrays.asList("user"));
		headers.put("x-HKMCA-client-secret", Arrays.asList("abcd1234"));
		
		//client.getRequestContext().put("Authorization", "Basic ZHVtbXk6YWJjZDEyMzQ=");
		
		client.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
		
		//Object[] objects = client.invoke("getAllPolicies");
		Object[] objects = client.invoke("getPolicyByPolicyNumber", "2020000001");
		
		//if(objects[0] instanceof PolicyXMaster ) {
//			PolicyXMaster x = (PolicyXMaster) objects[0];
//			log.info(x.toString());
		//}
		
		log.info(objects[0].getClass().toString());
		log.info(objects[0].toString());
		
		
		HashMap<String, String> result = new HashMap<String,String>();
		
		for (Field field : objects[0].getClass().getDeclaredFields()) {
		    
			field.setAccessible(true);
		    
			Object value = field.get(objects[0]);
		    
		    if (value != null) {
		    	result.put(field.getName(), value.toString());
		    	log.info(field.getName() + "=" + value);
		    }
		}
		
		return result;
    }

}
