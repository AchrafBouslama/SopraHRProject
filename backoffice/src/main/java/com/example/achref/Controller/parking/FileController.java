package com.example.achref.Controller.parking;

import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    private static final String UPLOAD_DIR="./uploads/";
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return new ResponseEntity<>("the file is empty", HttpStatus.BAD_REQUEST);
        }
        try{
            byte[] bytes=file.getBytes();
            Path path= Paths.get(UPLOAD_DIR+file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path,bytes);
            return new ResponseEntity<>("File uploaded successfully:"+file.getOriginalFilename(),HttpStatus.OK);

        }catch (IOException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-image/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename){
        try{
            Path file=Paths.get(UPLOAD_DIR).resolve(filename);
            Resource resource=new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }
            else{
                throw  new RuntimeException("Could not read the file");
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Error:"+e.getMessage());
        }
    }
}
