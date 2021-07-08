import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IActivityMaster } from '../activity-master.model';
import { ActivityMasterService } from '../service/activity-master.service';

@Component({
  templateUrl: './activity-master-delete-dialog.component.html',
})
export class ActivityMasterDeleteDialogComponent {
  activityMaster?: IActivityMaster;

  constructor(protected activityMasterService: ActivityMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activityMasterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
