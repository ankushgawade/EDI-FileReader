package com.filereader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.filereader.model.xmlData;
import com.filereader.repo.XmlreaderRepo;

@Controller
public class FileUploadController {

	@Autowired
    private XmlreaderRepo xmlreaderRepo;
    private List<String> contentList = new ArrayList<>();
    private String filePath;
    private List<String> selectedContent = new ArrayList<>();

 

    @PostMapping("/uploadform")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            if (!file.isEmpty()) {

                // Save the file to the specified folder
                String uploadDir = "D:\\Project\\xml files";
                filePath = uploadDir + File.separator + file.getOriginalFilename();
                File dest = new File(filePath);

                // file.transferTo(dest);

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file.getInputStream());
                doc.getDocumentElement().normalize();

                file.transferTo(dest);

                NodeList nodeList = doc.getElementsByTagName("PATH");
                StringBuilder content = new StringBuilder();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String textContent = element.getTextContent();
                        if (textContent.contains("/Outbound/NONEDI/")) {
                            String value = textContent.replace("/Outbound/NONEDI/", "");
                            contentList.add(value);
                        }

                    }
                }
                
                // Create and save xmlData instances to the database

                for (String value : contentList) {

                    xmlData data = new xmlData(value);

                    xmlreaderRepo.save(data);
                }
            
                
                model.addAttribute("message", "XML file uploaded, parsed, and data saved in the database.");

                model.addAttribute("content", content.toString());

            } else {
                model.addAttribute("message", "Please select a file to upload.");
            }
        } catch (IOException e) {
            model.addAttribute("message", "Error uploading or parsing the XML file.");
            e.printStackTrace();
        } catch (Exception e) {
            model.addAttribute("message", "Error processing the XML file.");
            e.printStackTrace();
        }
        return "xmldata";
    }

 

    @GetMapping("/search")
    public String searchCNumber(
            @RequestParam("cNumber") String cNumber,
            @RequestParam("replacementNumber") String replacementNumber,
            Model model  ) {

        try {
            // Parse the original XML document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document originalDoc = docBuilder.parse(new File(filePath));

            // Get a list of nodes containing the target "cNumber"

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            String expression = "//*[text()[contains(.,'" + cNumber + "')]]";
            NodeList nodeList = (NodeList) xPath.evaluate(expression, originalDoc, XPathConstants.NODESET);
            // Iterate through the nodes and replace the "cNumber" content
            
            List<String> modifiedContents = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                String nodeContent = node.getTextContent();

                String modifiedContent = nodeContent.replaceAll("\\b" + cNumber + "\\b", replacementNumber);

                node.setTextContent(modifiedContent);

                modifiedContents.add(modifiedContent);

                // Update xmlData entity and save it to the database

                xmlData data = new xmlData();  // Create a new instance of your xmlData entity

                data.setcNumber(modifiedContent);  // Set the modified content
               
                System.out.println(modifiedContent);
                
                xmlreaderRepo.save(data);  // Save the modified entity

            }
            model.addAttribute("modifiedContent", modifiedContents);
            
            // Save the modified XML document
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(originalDoc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
            model.addAttribute("message", "XML file updated successfully.");

        } catch (Exception e) {

            model.addAttribute("message", "Error updating the XML file.");
            e.printStackTrace();
        }
        return "xmldata";
    }

    @GetMapping("/Flowset")
    public String flowset(Model model) {
        model.addAttribute("selectedContent", selectedContent);
        return "flowset";
    }

    @PostMapping("/selectContent")
    public String selectContent(@RequestParam("selectedItems") List<String> selectedItems, Model model) {
        selectedContent = selectedItems; // Store the selected content in the list
        model.addAttribute("message", "Selected content updated successfully.");
        model.addAttribute("selectedContent", selectedContent); // Pass the updated selected content to the view
        return "flowset";
    }

 

}