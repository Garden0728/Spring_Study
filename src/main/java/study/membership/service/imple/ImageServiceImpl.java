package study.membership.service.imple;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import study.membership.repository.MemberRepository;
import study.membership.service.ImageService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")

    private String bucketName;

    @Override
    public String UpLoad(MultipartFile file) throws IOException {
        return this.uploadImage(file);
    }
     //파일 업로드를 하기위한 PutObjectRequest를 반환
    private PutObjectRequest getPutObjectRequest(String key) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
    }
      // MultipartFile을 업로드 하기위해 RequestBody.fromInputStream에 InputStream과 file의 Size를 넣음
    private RequestBody getFileRequestBody(MultipartFile file) throws IOException {
    return RequestBody.fromInputStream(file.getInputStream(), file.getSize());
    }
  // S3Utilities를 통해 GetUrlRequest를 파라미터로 넣어 파라미터로 넘어온 key의 접근 경로를 URL로 반환받아 경로를 사용할 수 있다. 즉 S3 버킷에 저장된 이미지 주소를 반환?
  private String findUploadKeyUrl (String key) {
    S3Utilities s3Utilities = s3Client.utilities();
    GetUrlRequest request = GetUrlRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();

    URL url = s3Utilities.getUrl(request);

    return url.toString();
  }

    private String uploadImage(MultipartFile file) throws IOException {
        String OriginalFilename = file.getOriginalFilename();
        String extension = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));
        String key = UUID.randomUUID().toString().substring(0, 10) + "_" + OriginalFilename;
        PutObjectRequest objectRequest = getPutObjectRequest(key);
         RequestBody rb = getFileRequestBody(file);
         s3Client.putObject(objectRequest, rb);

         return findUploadKeyUrl(key);

    }


}
