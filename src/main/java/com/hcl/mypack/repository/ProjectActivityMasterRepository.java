package com.hcl.mypack.repository;

import com.hcl.mypack.domain.ProjectActivityMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectActivityMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectActivityMasterRepository extends JpaRepository<ProjectActivityMaster, Long> {}
