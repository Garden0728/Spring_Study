package study.membership.controller.membercotroller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SignupRequestDto {
    private String ID;
    private String password;
    private String name;
    private MultipartFile imagePath;

}
