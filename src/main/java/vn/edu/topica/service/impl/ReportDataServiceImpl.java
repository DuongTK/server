package vn.edu.topica.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.model.ReportData;
import vn.edu.topica.repository.ReportDataRepository;
import vn.edu.topica.service.ReportDataService;
import vn.edu.topica.exception.StorageException;
import vn.edu.topica.exception.StorageFileNotFoundException;

@Service
public class ReportDataServiceImpl implements ReportDataService {

 @Value("${app.storage.folder}")
  private String root;

  @Autowired
  private ReportDataRepository reportDataRepository;

  private Path rootLocation ;

  @PostConstruct
  public void init(){
    rootLocation= Paths.get(root);
    try {
      Files.createDirectories(rootLocation);
    }
    catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }

  @Override
  public void saveFile(MultipartFile file) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file " + filename);
      }
      if (filename.contains("..")) {
        // This is a security check
        throw new StorageException(
            "Cannot store file with relative path outside current directory "
                + filename);
      }
      Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new StorageException("Failed to store file " + filename, e);
    }
  }

  @Override
  public Resource loadFileAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filename);

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

  @Override
  public void deleteFile(String fileName){
    Path path = load(fileName);
    try {
      Files.delete(path);
    } catch (NoSuchFileException e) {
      System.err.format("%s: no such" + " file or directory%n", path);
    } catch (DirectoryNotEmptyException e) {
      System.err.format("%s not empty%n", path);
    } catch (IOException e) {
      // File permission problems are caught here.
      System.err.println(e);
    }
  }

  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public ReportData findById(Long id){
    return reportDataRepository.findOne(id);
  }

@Override
  public void save(ReportData reportData){
    reportDataRepository.save(reportData);
  }

  @Override
  public void delete(Long id){
    reportDataRepository.delete(id);
  }

  @Override
  public List<ReportData> findByReportTypeOrderByCreatedDateDesc(String reportType,String queryKey) {
    return reportDataRepository.findByReportTypeOrderByCreatedDateDesc(reportType,queryKey);
  }


}
