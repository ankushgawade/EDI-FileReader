package com.filereader.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filereader.model.ediFiledata;

@Repository
public interface EdireaderRepo extends JpaRepository<ediFiledata, Long>{

	  List<ediFiledata> findAllBySenderId(String senderId);
	//List<ediFiledata> findBySenderId(String senderId);
	

	boolean existsBySenderId(String senderId);


	List<ediFiledata> findAllByFileName(String fileName);


	
	  //List<ediFiledata> findAllByFiletype(String fileType);
	 

	List<ediFiledata> findAllBySenderQualifier(String senderQualifier);


	List<ediFiledata> findAllByReceiverQualifier(String receiverQualifier);


	List<ediFiledata> findAllByReceiverId(String receiverId);


	List<ediFiledata> findAllByFunctionalCode(String functionalCode);


	List<ediFiledata> findAllByTransactionCode(String transactionCode);


	List<ediFiledata> findAllByFileType(String fileType);

	

}
