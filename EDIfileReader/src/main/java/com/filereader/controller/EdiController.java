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
import com.filereader.repo.EdireaderRepo;
import com.filereader.service.ediService;


@Controller
public class EdiController {
	
	@Autowired
	private EdireaderRepo edireaderRepo;
	
	@Autowired
	private ediService ediService;
	

	@PostMapping("/ediupload")
	public String handleEdiFileUpload(@RequestParam("file") MultipartFile file, Model model) {
	    try {
	    	 String senderId=null;
	        List<String> contentList = new ArrayList<>();
	        String fileName = file.getOriginalFilename();
	        boolean isEDIFile = false;
	        ediFiledata fileData = new ediFiledata();

	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
	            String line;

	            // Save the file to the specified folder
	            String uploadDir = "D:\\Project\\EDI files";  // Replace with your desired folder path
	            String filePath = uploadDir + File.separator + fileName;
	            File dest = new File(filePath);

	            if (!dest.getParentFile().exists()) {
	                dest.getParentFile().mkdirs();
	            }

	            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dest))) {
	                outputStream.write(file.getBytes());
	            }
	            
	            while ((line = reader.readLine()) != null) {
	                String[] values = line.split("\\*");

	                if (line.startsWith("ISA")) {
	                    if (values.length > 1) {
	                        //String senderQualifier = values[1];
	                        //String senderId = values[6];
	                        //String receiverQualifier = values[7];
	                        //String receiverId = values[8];
	                        //String usageIndicator = values[15];

	                        
	                      //  contentList.add("Usage Indicator:-  " + usageIndicator);
	                        
	                        // Save the fileData object to the repository
	                        
	                        fileData.setFileName(fileName);
	                        fileData.setFileType("EDI File");
	                        String senderQualifier= fileData.setSenderQualifier(values[1].replaceAll("\\s+", " ").trim());
	                       
	                       senderId= fileData.setSenderId(values[6].replaceAll("\\s+", " ").trim());
	                       String receiverQualifier= fileData.setReceiverQualifier(values[7].replaceAll("\\s+", " ").trim());
	                       String receiverId = fileData.setReceiverId(values[8].replaceAll("\\s+", " ").trim());
	                        
	                       contentList.add("File Name:-  " + fileName);
	                        contentList.add("Sender ID Qualifier:-  " + senderQualifier);
	                        contentList.add("Sender ID:-  " + senderId);
	                        contentList.add("Receiver ID Qualifier:-  " + receiverQualifier);
	                        contentList.add("Receiver ID:-  " + receiverId);
	                        
	                        
	                        
	                      //edireaderRepo.save(fileData);
	                        
	                        
	                        
	                    }
	                }

	                if (line.startsWith("GS")) {
	                    if (values.length > 1) {
	                        String functionalCode = values[1].replaceAll("\\s+", " ").trim();
	                        
	                        fileData.setFunctionalCode(functionalCode);
	                      //  edireaderRepo.save(fileData);
	                        contentList.add("Functional ID code:-  " + functionalCode);
	                    }
	                }

	                if (line.startsWith("ST")) {
	                    if (values.length > 1) {
	                        String transactionCode = values[1].replaceAll("\\s+", " ").trim();
	                        fileData.setTransactionCode(transactionCode);
	                        //edireaderRepo.save(fileData);
	                        contentList.add("Transaction Code:-  " + transactionCode);
	                        isEDIFile = true;
	                        break;
	                    }
	                }
	            }
	            if (edireaderRepo.existsBySenderId(senderId)) {
                    System.out.println("Data already exists in the database...................................");

                    
                } else {
                	edireaderRepo.save(fileData);
                	System.out.println("File data saved successfully.....");
                }

	         
	        }

	        if (!isEDIFile) {
	            model.addAttribute("messageEDIerror", "File is not an EDI file, please upload an EDI file.");
	        } else {   	             
	            model.addAttribute("content1", contentList);
	            model.addAttribute("messageEDI", "File uploaded successfully.");
	           // return "redirect:/showData";
	        }
	    } catch (IOException e) {
	        model.addAttribute("messageEDI", "File upload failed or not an EDI file.");
	    }

	    return "Upload";
	}

	/*
	 * @GetMapping("/showallData") public String showTableData(Model model) {
	 * List<ediFiledata> fileData = edireaderRepo.findAll();
	 * model.addAttribute("files", fileData); return "files"; }
	 */
	 
	
	  @GetMapping("/showData") 
	  public String showTableData(@RequestParam(value ="senderId", required = false) String senderId,
			  @RequestParam(value = "fileName", required = false) String fileName,
			  @RequestParam(value = "fileType",required = false)String fileType,
			  @RequestParam(value = "senderQualifier",required = false)String senderQualifier,
			  @RequestParam(value = "receiverQualifier",required = false)String receiverQualifier,
			  @RequestParam(value = "receiverId",required = false)String receiverId,
			  @RequestParam(value = "functionalCode",required = false)String functionalCode,
			  @RequestParam(value = "transactionCode",required = false)String transactionCode,
			  Model model) {
	     List<ediFiledata> fileData;
	     //List<ediFiledata> fileData = edireaderRepo.findAll();
	  
	      if (senderId != null) { 
		  fileData =edireaderRepo.findAllBySenderId(senderId); 
		  } else if (fileName != null) {
		        fileData = edireaderRepo.findAllByFileName(fileName);
		  }

			 else if (fileType != null) { 
				 fileData =edireaderRepo.findAllByFileType(fileType); 
				 }
			 
		  else if (senderQualifier != null) {
		        fileData = edireaderRepo.findAllBySenderQualifier(senderQualifier);
		  }
		  else if (receiverQualifier != null) {
		        fileData = edireaderRepo.findAllByReceiverQualifier(receiverQualifier);
		  }
		  else if (receiverId != null) {
		        fileData = edireaderRepo.findAllByReceiverId(receiverId);
		  }
		  else if (functionalCode != null) {
		        fileData = edireaderRepo.findAllByFunctionalCode(functionalCode);
		  }
		  else if (transactionCode != null) {
		        fileData = edireaderRepo.findAllByTransactionCode(transactionCode);
		  }else { 
			  fileData = edireaderRepo.findAll(); 
			  }
	     
	      model.addAttribute("edifiles", fileData); 
	      return "edifiles"; 
	      }
	 

}
