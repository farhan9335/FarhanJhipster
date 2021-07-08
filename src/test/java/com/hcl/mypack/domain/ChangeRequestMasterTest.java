package com.hcl.mypack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hcl.mypack.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChangeRequestMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChangeRequestMaster.class);
        ChangeRequestMaster changeRequestMaster1 = new ChangeRequestMaster();
        changeRequestMaster1.setId(1L);
        ChangeRequestMaster changeRequestMaster2 = new ChangeRequestMaster();
        changeRequestMaster2.setId(changeRequestMaster1.getId());
        assertThat(changeRequestMaster1).isEqualTo(changeRequestMaster2);
        changeRequestMaster2.setId(2L);
        assertThat(changeRequestMaster1).isNotEqualTo(changeRequestMaster2);
        changeRequestMaster1.setId(null);
        assertThat(changeRequestMaster1).isNotEqualTo(changeRequestMaster2);
    }
}
