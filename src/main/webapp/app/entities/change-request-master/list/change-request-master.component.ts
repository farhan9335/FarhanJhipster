import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChangeRequestMaster } from '../change-request-master.model';
import { ChangeRequestMasterService } from '../service/change-request-master.service';
import { ChangeRequestMasterDeleteDialogComponent } from '../delete/change-request-master-delete-dialog.component';

@Component({
  selector: 'jhi-change-request-master',
  templateUrl: './change-request-master.component.html',
})
export class ChangeRequestMasterComponent implements OnInit {
  changeRequestMasters?: IChangeRequestMaster[];
  isLoading = false;

  constructor(protected changeRequestMasterService: ChangeRequestMasterService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.changeRequestMasterService.query().subscribe(
      (res: HttpResponse<IChangeRequestMaster[]>) => {
        this.isLoading = false;
        this.changeRequestMasters = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IChangeRequestMaster): number {
    return item.id!;
  }

  delete(changeRequestMaster: IChangeRequestMaster): void {
    const modalRef = this.modalService.open(ChangeRequestMasterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.changeRequestMaster = changeRequestMaster;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
