package com.app.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class UploadImageRequest {
	
	@Getter @Setter private String image;
	@Getter @Setter private String username;

}
