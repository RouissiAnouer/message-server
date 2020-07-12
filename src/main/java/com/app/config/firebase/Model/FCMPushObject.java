package com.app.config.firebase.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
public class FCMPushObject {
	@Getter @Builder.Default private Map<String, String> data = new HashMap();
	@Getter @Setter @Builder.Default public boolean singlePush=true;
	@Getter @Setter @Builder.Default public boolean multiDevice=false;
	@Getter private String senderUsername;
	@Getter private String body;
	@Getter private String title;
	@Getter private String topic;
	@Getter private String topicPrefix;
	@Getter private String token;
	@Getter @Setter private String packageToSet;
	@Getter @Builder.Default Set<String> appPrefixs =  new HashSet<String>();
	@Getter @Builder.Default private List<DeviceTokensObject> deviceTokensMultiCast = new ArrayList<>();

	
	public FCMPushObject filterByPrefix(String prefix,String packageToSet){
		return  FCMPushObject.builder()
				.data(this.data)
				.body(this.body)
				.senderUsername(this.senderUsername)
				.title(this.title)
				.topic(topic)
				.deviceTokensMultiCast(getFilterDeviceTokens(prefix))
				.packageToSet(packageToSet)
				.build();
	}	
	
	
	public List<String> getDeviceTokensString(){
		return this.deviceTokensMultiCast.stream().map(e -> e.getDeviceToken()).collect(Collectors.toList());
	}
	

	private List<DeviceTokensObject> getFilterDeviceTokens(String prefix) {
		return deviceTokensMultiCast.stream()
				.filter(d -> d.getDevicePrefix().equalsIgnoreCase(prefix))
				.collect(Collectors.toList());
		
	}
	
	public FCMPushObject addData(String key,String value) {
		this.data.put(key, value);
		return this;
	}
	
	
	public void addDeviceTokens(String deviceToken,String devicePrefix){
		 this.appPrefixs.add(devicePrefix);
		 if(appPrefixs.size()>1) {
			 this.multiDevice = true;
		 }
		 this.deviceTokensMultiCast.add(new DeviceTokensObject(deviceToken, devicePrefix));
		 if(deviceTokensMultiCast.size()>1) {
			 this.singlePush = false;
		 }
	}


	public static class DeviceTokensObject {
		private String deviceToken;
		private String devicePrefix;
		
		public String getDeviceToken() {
			return deviceToken;
		}
		public void setDeviceToken(String deviceToken) {
			this.deviceToken = deviceToken;
		}
		public String getDevicePrefix() {
			return devicePrefix;
		}
		public void setDevicePrefix(String devicePrefix) {
			this.devicePrefix = devicePrefix;
		}
		
		public  DeviceTokensObject(String deviceToken,String devicePrefix) {
			this.deviceToken=deviceToken;
			this.devicePrefix=devicePrefix;
		}
		
	}
}
