package com.filereader.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.filereader.model.ediFiledata;
import com.filereader.model.flatFileData;
import com.filereader.repo.FilereaderRepo;

@Controller
public class UploadController {

	private static final String partnerCode = null;
	@Autowired
	private FilereaderRepo fileReaderRepo;

	@PostMapping("/upload")
	public String handleFlatFileUpload(@RequestParam("file") MultipartFile file, Model model) {
		if (!file.isEmpty()) {

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
				String partnerCode=null;
				List<String> contentList = new ArrayList<>();
				String fileName = file.getOriginalFilename();
				String line;
				boolean isFlatFile = false;
				flatFileData fileData = new flatFileData();

				String uploadDir = "D:\\Project\\Flat files"; // Replace with your desired folder path
				String filePath = uploadDir + File.separator + fileName;
				File dest = new File(filePath);

				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}

				try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dest))) {
					outputStream.write(file.getBytes());
				}

				while ((line = reader.readLine()) != null) {
					String[] values = line.split("\\|");

					if (line.startsWith("000")) {
						if (values.length > 1) {
							String fileType = values[1];
							contentList.add("File Name:-  " + fileName);
							contentList.add("File Type:- " + fileType);
							System.out.println(values[1]);
						}
					}

					if (line.startsWith("100")) {
						isFlatFile = true;

						if (values.length > 1) {
							// partnerCode = values[1];
							//String status = values[2];
							//String loadNumber = values[5];
							//String shipmentId = values[15];

							// flatFileData ffileData = new flatFileData();
							fileData.setFileName(fileName);
							fileData.setFileType("Flat File");
						   
						     partnerCode=fileData.setPartnerCode(values[1]);
						     String status=(String) fileData.setStatus( values[2]);
						     String loadNumber=(String) fileData.setLoadNumber(values[5]);
						     String shipmentId=(String) fileData.setShipmentId(values[15]);

							

							contentList.add("PartnerCode:-  " + partnerCode);
							contentList.add("Status:-  " + status);
							contentList.add("Load Number:-  " + loadNumber);
							contentList.add("Container Number:-  " + shipmentId);

						}
					}
				}

				
				 if (fileReaderRepo.existsByPartnerCode(partnerCode)) { 
					 System.out.println("Data already exists in the database...................................");
				
				  } else { 
					  fileReaderRepo.save(fileData);
				      System.out.println("File data saved successfully....."); 
				      }

				if (!isFlatFile) {
					model.addAttribute("messageFlaterror", "File is not flat file.");
				} else {

					model.addAttribute("content", contentList);
					model.addAttribute("messageFlat", "File uploaded successfully.");
				}
			} catch (IOException e) {
				model.addAttribute("messageFlat", "Error occurred while processing the file.");
			}
		}

		return "Upload";
	}

	/*
	 * @GetMapping("/showFlatData") public String showTableData(Model model) {
	 * List<flatFileData> fileData = fileReaderRepo.findAll();
	 * model.addAttribute("ffiledata", fileData); return "ffiledata"; }
	 */
	
	/*
	 * @GetMapping("/showFlatData") public String showTableData(@RequestParam(value
	 * ="partnerCode", required = false) String partnerCode,
	 * 
	 * @RequestParam(value ="fileName", required = false) String fileName,
	 * 
	 * @RequestParam(value ="fileType", required = false) String fileType,
	 * 
	 * @RequestParam(value ="status", required = false) String status,
	 * 
	 * @RequestParam(value ="loadNumber", required = false) String loadNumber,
	 * 
	 * @RequestParam(value ="shipmentId", required = false) String shipmentId, Model
	 * model) { List<flatFileData> fileData = fileReaderRepo.findAll();
	 * 
	 * if (partnerCode != null) { fileData
	 * =fileReaderRepo.findByPartnerCode(partnerCode); } else if (fileName != null)
	 * { fileData = fileReaderRepo.findAllByFileName(fileName); } else if (fileType
	 * != null) { fileData = fileReaderRepo.findAllByfileType(fileType); } else if
	 * (status != null) { fileData = fileReaderRepo.findAllByStatus(status); } else
	 * if (loadNumber != null && !loadNumber.isEmpty()) { fileData =
	 * fileReaderRepo.findAllByLoadNumber(loadNumber); } else if (shipmentId !=
	 * null) { fileData = fileReaderRepo.findAllByShipmentId(shipmentId); } else {
	 * fileData = fileReaderRepo.findAll(); }
	 * 
	 * model.addAttribute("flatfiledata", fileData); return "flatfiledata"; }
	 */
	
	@GetMapping("/showFlatData")
	public String showTableData(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
	List<flatFileData> fileData;

	if (searchQuery != null && !searchQuery.isEmpty()) {
	fileData = fileReaderRepo.findByPartnerCodeContainingOrFileNameContainingOrFileTypeContainingOrStatusContainingOrLoadNumberContainingOrShipmentIdContaining(searchQuery, searchQuery, searchQuery, searchQuery, searchQuery, searchQuery);
	} else {
	fileData = fileReaderRepo.findAll();
	}

	model.addAttribute("flatfiledata", fileData);
	return "flatfiledata";
	}
	
	
	
}
