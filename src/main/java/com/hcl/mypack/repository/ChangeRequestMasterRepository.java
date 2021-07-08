package com.hcl.mypack.repository;

import com.hcl.mypack.domain.ChangeRequestMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChangeRequestMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChangeRequestMasterRepository extends JpaRepository<ChangeRequestMaster, Long> {}
