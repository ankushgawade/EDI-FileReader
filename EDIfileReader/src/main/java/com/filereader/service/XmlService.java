/*
 * package com.filereader.service;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.filereader.model.xmlData; import
 * com.filereader.repo.XmlreaderRepo;
 * 
 * @Service public class XmlService {
 * 
 * @Autowired private XmlreaderRepo xmlreaderRepo;
 * 
 * @Transactional public List<String> searchCNumberAndSave(String cNumber,
 * String replacementNumber) { List<String> modifiedContent = new ArrayList<>();
 * 
 * for (String data : contentList) { if (data.contains(cNumber)) { String
 * modifiedData = data.replace(cNumber, replacementNumber);
 * modifiedContent.add(modifiedData);
 * 
 * xmlData dataEntity = new xmlData(modifiedData);
 * 
 * try { xmlreaderRepo.save(dataEntity); } catch (Exception e) { // Handle
 * exceptions here (log, throw, etc.) } } }
 * 
 * if (!modifiedContent.isEmpty()) { modifiedContent.add("Searched C Number: " +
 * cNumber); }
 * 
 * return modifiedContent; } }
 */