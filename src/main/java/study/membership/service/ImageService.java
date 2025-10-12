package study.membership.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public String UpLoad(MultipartFile file) throws IOException;


}
