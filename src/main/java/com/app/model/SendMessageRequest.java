package com.app.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class SendMessageRequest {
	
	@Getter @Setter private String message;

}
