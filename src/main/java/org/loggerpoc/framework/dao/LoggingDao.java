package org.loggerpoc.framework.dao;


import org.loggerpoc.framework.entity.LogReqRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LoggingDao extends JpaRepository<LogReqRes, Long>{

}
