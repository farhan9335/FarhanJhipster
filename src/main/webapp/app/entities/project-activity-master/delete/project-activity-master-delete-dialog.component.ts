import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProjectActivityMaster } from '../project-activity-master.model';
import { ProjectActivityMasterService } from '../service/project-activity-master.service';

@Component({
  templateUrl: './project-activity-master-delete-dialog.component.html',
})
export class ProjectActivityMasterDeleteDialogComponent {
  projectActivityMaster?: IProjectActivityMaster;

  constructor(protected projectActivityMasterService: ProjectActivityMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectActivityMasterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
