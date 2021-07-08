package com.hcl.mypack.repository;

import com.hcl.mypack.domain.ActivityMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ActivityMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityMasterRepository extends JpaRepository<ActivityMaster, Long> {}
