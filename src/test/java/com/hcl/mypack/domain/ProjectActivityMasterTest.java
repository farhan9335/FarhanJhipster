package com.hcl.mypack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hcl.mypack.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectActivityMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectActivityMaster.class);
        ProjectActivityMaster projectActivityMaster1 = new ProjectActivityMaster();
        projectActivityMaster1.setId(1L);
        ProjectActivityMaster projectActivityMaster2 = new ProjectActivityMaster();
        projectActivityMaster2.setId(projectActivityMaster1.getId());
        assertThat(projectActivityMaster1).isEqualTo(projectActivityMaster2);
        projectActivityMaster2.setId(2L);
        assertThat(projectActivityMaster1).isNotEqualTo(projectActivityMaster2);
        projectActivityMaster1.setId(null);
        assertThat(projectActivityMaster1).isNotEqualTo(projectActivityMaster2);
    }
}
