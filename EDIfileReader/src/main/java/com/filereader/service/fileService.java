package com.filereader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filereader.model.flatFileData;
import com.filereader.repo.FilereaderRepo;

@Service
public class fileService {

	@Autowired
	private FilereaderRepo fileReaderRepo;


	public void save(flatFileData filedata) {
		fileReaderRepo.save(filedata);
		System.out.println("filedata added successfully....");
	}
}
