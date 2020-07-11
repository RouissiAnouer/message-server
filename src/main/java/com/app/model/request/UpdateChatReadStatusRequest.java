package com.app.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class UpdateChatReadStatusRequest {
	
	@JsonProperty("ids")
	@Getter @Setter private List<String> ids;

}
