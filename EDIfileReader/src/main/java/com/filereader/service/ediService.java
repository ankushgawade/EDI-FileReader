package com.filereader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filereader.model.ediFiledata;
import com.filereader.repo.EdireaderRepo;


@Service
public class ediService {

	@Autowired
	private EdireaderRepo edireaderRepo;

	
	public void save(ediFiledata filedata) {
		edireaderRepo.save(filedata);
		System.out.println("filedata added successfully....");
	}
}
