package com.filereader.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.filereader.model.xmlData;

@Repository
public interface XmlreaderRepo extends JpaRepository<xmlData, Long> {

}
