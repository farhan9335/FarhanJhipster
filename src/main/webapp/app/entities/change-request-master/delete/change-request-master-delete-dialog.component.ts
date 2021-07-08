import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChangeRequestMaster } from '../change-request-master.model';
import { ChangeRequestMasterService } from '../service/change-request-master.service';

@Component({
  templateUrl: './change-request-master-delete-dialog.component.html',
})
export class ChangeRequestMasterDeleteDialogComponent {
  changeRequestMaster?: IChangeRequestMaster;

  constructor(protected changeRequestMasterService: ChangeRequestMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.changeRequestMasterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
