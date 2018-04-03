package vn.edu.topica.controller;

import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.model.Query;
import vn.edu.topica.model.ReportData;
import vn.edu.topica.model.ReportStructure;
import vn.edu.topica.service.QueryService;
import vn.edu.topica.service.ReportDataService;
import vn.edu.topica.service.ReportStructureService;

@RestController
@RequestMapping("/api")
@Slf4j
public class MainController {

  @Value("${app.storage.folder}")
  private String root;

  @Autowired
  private QueryService queryService;

  @Autowired
  private ReportDataService reportDataService;

  @Autowired
  private ReportStructureService reportStructureService;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @RequestMapping(value = "/report", method = RequestMethod.GET)
   public ResponseEntity<List<Map<String, Object>>> getData(
      @RequestParam(value = "startTime", required = false) Long startTime,
      @RequestParam(value = "endTime", required = false) Long endTime,
      @RequestParam(value = "key", required = true) String key) {

    String queryFromDB = queryService.findByKey(key);
    log.info("before replate: " +queryFromDB);
    log.info("startTime:" + startTime + " endtime: " +endTime);

    if (startTime != null) {
      queryFromDB = queryFromDB.replace("[STARTTIME]", startTime.toString());
    } else {
      queryFromDB = queryFromDB.replace("[STARTTIME]", Long.toString(Instant.now().getEpochSecond()-86400));
    }
    if (endTime != null) {
      queryFromDB = queryFromDB.replace("[ENDTIME]", endTime.toString());
    } else {
      queryFromDB = queryFromDB.replace("[ENDTIME]", Long.toString(Instant.now().getEpochSecond()));
    }
    log.info("before replate: " +queryFromDB);

    if (queryFromDB.isEmpty()){
      return ResponseEntity.ok(Collections.emptyList());
    }
    List<Map<String, Object>> getData = jdbcTemplate.queryForList(queryFromDB);

    if(getData == null) {
      return ResponseEntity.ok(Collections.emptyList());
    }
    return ResponseEntity.ok(getData);
  }

  @RequestMapping(value = "/reportData", method = RequestMethod.POST)
  public ResponseEntity<ReportData> addFile(
      @RequestParam(value = "queryKey") String queryKey,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "type") String reportType,
      @RequestParam("filedata") MultipartFile file){
    reportDataService.saveFile(file);
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    ReportData reportData = new ReportData();
    reportData.setQueryKey(queryKey);
    reportData.setName(name);
    reportData.setReportType(reportType);
    reportData.setFileName(Paths.get(root).resolve(filename).toString());
    reportDataService.save(reportData);
    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = "/reportDataByFilename", method = RequestMethod.GET)
  public ResponseEntity<Resource> getFile( @RequestParam(value = "id") Long id){
    ReportData reportData = reportDataService.findById(id);
    if(reportData == null){
      return ResponseEntity.notFound().build();
    }
    String fileName = reportData.getFileName();
    Resource resource = reportDataService.loadFileAsResource(fileName);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
  }

  @RequestMapping(value = "/reportData", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteFile( @RequestParam(value = "id") Long id){
    ReportData reportData = reportDataService.findById(id);
    if(reportData == null){
      return ResponseEntity.notFound().build();
    }
    reportData.setStatus((short)1);
    reportDataService.save(reportData);
    return ResponseEntity.ok(HttpStatus.OK);

  }

  @RequestMapping(value = "/getData", method = RequestMethod.GET)
  public ResponseEntity<String> getData(@RequestParam(value = "id") Long id){
    ReportStructure reportStructure = reportStructureService.findById(id);
    if(reportStructure == null){
      return ResponseEntity.notFound().build();
    }
    String data = reportStructure.getData();
    return ResponseEntity.ok(data);
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ResponseEntity<ReportStructure> getData(
      @RequestParam("name") String name,
      @RequestParam("queryKey") String queryKey,
      @RequestParam("report") String report){
    ReportStructure reportStructure = new ReportStructure();
    reportStructure.setName(name);
    reportStructure.setQueryKey(queryKey);
    reportStructure.setData(report);
    reportStructureService.save(reportStructure);
    return ResponseEntity.ok(reportStructure);
  }

  @RequestMapping(value = "/reportStructures", method = RequestMethod.GET)
  public ResponseEntity<List<ReportStructure>> getReportStructureByQueryKey(@RequestParam("queryKey") String queryKey){
    List<ReportStructure> reportStructureList = reportStructureService.findByQueryKeyOrderByCreatedDateDesc(queryKey);
    return ResponseEntity.ok(reportStructureList);
  }

  @RequestMapping(value = "/reportDatas", method = RequestMethod.GET)
  public ResponseEntity<List<ReportData>> getAllReportData( @RequestParam("exportType") String reportType,@RequestParam("queryKey") String queryKey){
    List<ReportData> reportDataList = reportDataService.findByReportTypeOrderByCreatedDateDesc(reportType,queryKey);
    return ResponseEntity.ok(reportDataList);
  }

  @RequestMapping(value = "/reportData", method = RequestMethod.GET)
  public ResponseEntity<ReportData> getReportData( @RequestParam("id") Long id){
    ReportData reportData = reportDataService.findById(id);
    return ResponseEntity.ok(reportData);
  }

  @RequestMapping(value = "/queries", method = RequestMethod.GET)
  public ResponseEntity<List<Query>> getQueries(){
    List<Query> queryList = queryService.findAllQueryNotEmpty();
    return ResponseEntity.ok(queryList);
  }

}

