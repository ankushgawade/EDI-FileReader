package com.filereader.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.filereader.model.flatFileData;

@Repository
public interface FilereaderRepo extends JpaRepository<flatFileData, Long>{

	List<flatFileData> findByPartnerCode(String partnerCode);
	boolean existsByPartnerCode(String partnerCode);
	List<flatFileData> findAllByFileName(String fileName);
	List<flatFileData> findAllByfileType(String fileType);
	List<flatFileData> findAllByStatus(String status);
	List<flatFileData> findAllByLoadNumber(String loadNumber);
	List<flatFileData> findAllByShipmentId(String shipmentId);
	/*
	 * @Query("SELECT f FROM FlatFileData f WHERE f.fileName LIKE %:searchQuery% OR f.fileType LIKE %:searchQuery% OR f.partnerCode LIKE %:searchQuery% OR f.status LIKE %:searchQuery% OR f.loadNumber LIKE %:searchQuery% OR f.shipmentId LIKE %:searchQuery%"
	 * ) List<flatFileData> searchAllFields(@Param("searchQuery") String
	 * searchQuery);
	 */
	List<flatFileData> findByPartnerCodeContainingOrFileNameContainingOrFileTypeContainingOrStatusContainingOrLoadNumberContainingOrShipmentIdContaining(
			String searchQuery, String searchQuery2, String searchQuery3, String searchQuery4, String searchQuery5,
			String searchQuery6);
}
