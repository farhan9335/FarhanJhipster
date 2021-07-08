package com.hcl.mypack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hcl.mypack.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivityMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityMaster.class);
        ActivityMaster activityMaster1 = new ActivityMaster();
        activityMaster1.setId(1L);
        ActivityMaster activityMaster2 = new ActivityMaster();
        activityMaster2.setId(activityMaster1.getId());
        assertThat(activityMaster1).isEqualTo(activityMaster2);
        activityMaster2.setId(2L);
        assertThat(activityMaster1).isNotEqualTo(activityMaster2);
        activityMaster1.setId(null);
        assertThat(activityMaster1).isNotEqualTo(activityMaster2);
    }
}
